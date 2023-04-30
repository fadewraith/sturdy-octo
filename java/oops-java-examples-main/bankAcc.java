/*
Design a class to represent a bank account. Include the following members:-
Name of depositor
Account number
Type of account
Balance amount in account
    Methods:-
To assign initial values
To deposit an amount
To withdraw an amount after checking balance
To display name and balance
*/ 

import java.util.*;

class bankAccount{

    String name;
    long acc_num;
    String type;
    float balance;

    public void setDetails(String n,long acc,String t,float bal){

        name = n;
        acc_num = acc;
        type = t;
        balance = bal;

    }

    public void addAmount(float amt){

        balance += amt;

    }

    public void withDrawAmount(float wdamt){

        System.out.println("The current balance: "+balance);
        balance -= wdamt;
        
    }

    public void setDisplay(){

        System.out.println("Name of acc holder is: "+name);
        System.out.println("Closing balance is: "+balance);

    }

    
}

class bankAcc{
    
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String name = sc.nextLine();

        System.out.print("Enter the acc num: ");
        long acc = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter the type of acc: ");
        String type = sc.nextLine();

        System.out.print("Enter the balance: ");
        float bal = sc.nextFloat();
        sc.nextLine();

        bankAccount ba = new bankAccount();
        ba.setDetails(name,acc,type,bal);

        System.out.print("Enter the amount to be deposited: ");
        float amt = sc.nextFloat();
        ba.addAmount(amt);

        System.out.print("Enter the amount to be withdraw: ");
        float wdamt = sc.nextFloat();
        ba.withDrawAmount(wdamt);

        ba.setDisplay();

    }
}