package com.example.aopdemo.dao;

import com.example.aopdemo.Account;

public interface AccountDAO {
    void addAccount();

    void addAccount(Account account);

    void addAccount(Account account, boolean flag);

    boolean doWork();
}
