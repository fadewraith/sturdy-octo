import java.util.*;

class Account{

  String name;
  int acc_no;
  String acc_type;
  int balance;

  Account(String name,int acc_no,String acc_type,int balance){

    this.name = name;
    this.acc_no = acc_no;
    this.acc_type = acc_type;
    this.balance = balance;

  }

  void deposit(int a_no,int amt){

    if(acc_no == a_no){

      balance += amt;
      System.out.println("Account is credited");

    }else{

      System.out.println("Account does not exist");

    }
  }

  void withdraw(int a_no,int amt){

    if(acc_no == a_no){

      if(balance>=amt){

        balance -= amt;
        System.out.println("Amount withdrawn");

      }else{

        System.out.println("Insufficient balance");

      }
    }
  }

  void enquiry(int a_no){

    if(acc_no==a_no){

      System.out.println("hello "+name);
      System.out.println("Your balance is: "+balance);

    }else{

      System.out.println("Account does not exist");

    }
  }
}

class AccClass{

  public static void main(String args[]){

    Scanner sc = new Scanner(System.in);
    int a_no,amt,ch=0;

    Account ac = new Account("world",1234,"Savings",2000);
    System.out.println("Acc is created");

    while(ch!=4){

      System.out.println("1-> Deposit");
      System.out.println("2-> Withdraw");
      System.out.println("3-> Enquiry");

    ch = sc.nextInt();

    switch(ch){

      case 1:

        System.out.print("Enter acc no: ");
        a_no = sc.nextInt();
        System.out.print("Enter amount: ");
        amt = sc.nextInt();
        ac.deposit(a_no,amt);
        break;

      case 2:

        System.out.print("Enter acc no: ");
        a_no = sc.nextInt();
        System.out.print("Enter amount: ");
        amt = sc.nextInt();
        ac.withdraw(a_no,amt);
        break;

      case 3:

        System.out.print("Enter acc no: ");
        a_no = sc.nextInt();
        ac.enquiry(a_no);
        break;
      
      case 4:
        break;

      default:
        System.out.println("Invalid choice");
        break;
    }
  }

  }
}