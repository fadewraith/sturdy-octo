package com.example.aopdemo.dao;

import org.springframework.stereotype.Repository;

@Repository // for component scanning
public class MembershipDAOImpl implements MembershipDAO {

//    @Override
//    public void addAccount() {
//        System.out.println(getClass() + ": adding a membership account");
//    }

//    @Override
//    public void addDummyMember() {
//        System.out.println(getClass() + ": adding a membership account");
//    }

    @Override
    public boolean addDummyMember() {
        System.out.println(getClass() + ": adding a membership account");
        return true;
    }

    @Override
    public void goToSleep() {

        System.out.println(getClass() + ": going to sleep now");

    }
}
