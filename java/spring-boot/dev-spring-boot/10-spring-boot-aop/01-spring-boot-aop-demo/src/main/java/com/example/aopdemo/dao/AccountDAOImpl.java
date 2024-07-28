package com.example.aopdemo.dao;

import com.example.aopdemo.Account;
import org.springframework.stereotype.Repository;

@Repository // for component scanning
public class AccountDAOImpl implements AccountDAO {

    @Override
    public void addAccount() {
        System.out.println(getClass() + ": adding an account");
    }

    @Override
    public void addAccount(Account account) {
        System.out.println(getClass() + ": adding an account with param");
    }

    @Override
    public void addAccount(Account account, boolean flag) {
        System.out.println(getClass() + ": adding an account with param and boolean");

    }

    @Override
    public boolean doWork() {
        System.out.println(getClass() + ": doWork()");
        return false;
    }
}
