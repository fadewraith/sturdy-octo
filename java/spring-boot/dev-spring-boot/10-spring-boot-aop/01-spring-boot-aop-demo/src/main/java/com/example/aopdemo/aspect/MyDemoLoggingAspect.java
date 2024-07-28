package com.example.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

//    @Before("execution(public void addAccount())")
//    @Before("execution(public void add*())") // match any add method
//    @Before("execution(public void updateAccount())")
//    @Before("execution(void add*())") // match method with based on return type
//    @Before("execution(* add*())") // match method with any return type
//    @Before("execution(* add*(com.example.aopdemo.Account))") // match method with any param type
//    @Before("execution(* add*(com.example.aopdemo.Account, ..))") // match any num of params
//    @Before("execution(* add*(..))") // match any num of params
    @Before("execution(* com.example.aopdemo.dao.*.*(..))") // limit to package name, any class, any method, any params
    public void beforeAddAccountAdvice() {

        System.out.println("\n=====>>> Executing @Before advice on method");

    }
}
