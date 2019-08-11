package com.itheima.ssm.controller;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method;  //访问的方法


    //前置通知  主要是获取开始时间，执行的是哪一个类，执行的是哪一个方法
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date(); //当前时间就是我们开始访问的时间
        clazz=jp.getClass(); //具体要访问的类
        String methodName = jp.getSignature().getName();//获取访问的方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数

        //获取具体执行的方法的method对象
        if (args==null||args.length==0){
            method = clazz.getMethod(methodName); //只能获取无参数的方法
        }else{
            Class[] classesArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classesArgs[i]=args[i].getClass();
            }
            clazz.getMethod(methodName,classesArgs);
        }
    }

    //后置通知
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp){
        long time = new Date().getTime()-visitTime.getTime(); //获取访问的市场

        String url = "";
        //获取url
        if (clazz!=null&&method!=null&&clazz==LogAop.class){
            //1.获取类上面的@RequestMapping("/role")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value();

                //2.获取方法上的 @RequestMapping("/findRoleByIdAndAllPermission.do")
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0]+methodValue[0];
                }
            }
        }

        //获取访问的ip地址
        //通过request对象访问，在web.xml文件中配置一个listener RequestContextListener 可以直接注入

    }
}
