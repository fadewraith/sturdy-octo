/*
Develop a program in java to create a class Bank. In Bank class create a method interest() which
return the bank interest as 0. Now create two classes Sbi and Pnb, these classes inherits the
Bank class. In Sbi and Pnb classes override the interest() method and return the value of interest
as per bank norms. Now test the classes
*/

class Bank{
    double interest;

    void Interest(){
        
        System.out.println("This is the interest which is void of Bank class: "+interest);

    }
}

class Sbi extends Bank{
    void Interest(){
        interest = 5;
        System.out.println("The interest rates of SBI is: "+interest+"%");
    }
}

class Pnb extends Bank{
    void Interest(){
        interest = 4.5;
        System.out.println("The interest rates of PNB is: "+interest+"%");
    }
}

class bankOverRide{

    public static void main(String args[]){
        Bank abank = new Bank();
        abank.Interest();

        Sbi sbank = new Sbi();
        sbank.Interest();

        Pnb pbank = new Pnb();
        pbank.Interest();
    }
}