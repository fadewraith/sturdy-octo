package com.example.aopdemo.aspect;

import com.example.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.example.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint proceedingJoinPoint
    ) throws Throwable {

//        print out method we are advising on
        String method = proceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @Around on method: " + method);

//        get begin timestamp
        long begin = System.currentTimeMillis();

//        now lets execute the method
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
//            log the exception
            System.out.println(e.getMessage());

//            give user a cusotm msg
//            result = "Major accident! but no worries, your private AOP is on the way";


//            rethrow the exception
            throw e;
        }

//        get end timestamp
        long end = System.currentTimeMillis();

//        complete duration and display it
        long duration = end - begin;
        System.out.println("\n=====> duration: " + duration / 1000.0 + " seconds");

        return result;
    }

    @After("execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
//        print out which method we are advising on

        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After (finally) on method: " + method);

    }

    @AfterThrowing(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc"
    )
    public void afterThrowingFindAccountsAdvice(
            JoinPoint joinPoint, Throwable theExc
            ) {

//        print out which method we are advising on
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterThrowing on method: " + method);

//        log the exception
        System.out.println("\n=====>>> exception is: " + theExc);


    }

//    add a new advice for @AfterReturning on the findAccounts method

    @AfterReturning(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) { // result name is same as returning
//        print out which method we are advising on
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

//        print out the results of the method call
        System.out.println("\n=====>>> result is: " + result);

//        lets post process the data ... lets modify it

//        convert the account names to uppercase
        convertAccountNamesToUpperCase(result);

        System.out.println("\n=====>>> result is: " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {
//        loop through accounts
        for(Account account: result) {
            String upperName = account.getName().toUpperCase();
            account.setName(upperName);
        }

//        get uppercase of name


//        update name on account
    }

    @Before("com.example.aopdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()") // limit to package name, any class, any method, any params
    public void beforeAddAccountAdvice(JoinPoint joinPoint) {
        System.out.println("\n=====>>> Executing @Before advice on method");

//        display the method signature
//        joinpoint has metadata about method call
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("method: " + methodSignature);

//        display method arguments

//        get args
        Object[] args = joinPoint.getArgs();

//        loop through args
        for(Object tempArg: args) {
            System.out.println(tempArg);

            if(tempArg instanceof Account) {
//                downcast and print account specific stuff
                Account account = (Account) tempArg;
                System.out.println("account name: " + account.getName());
                System.out.println("account level: " + account.getLevel());
            }
        }
    }




}
