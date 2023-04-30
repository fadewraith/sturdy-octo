// calculator using switch

import java.util.*;

class switchCalc {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        char ch;
        float a,b;

        System.out.println("Enter the choice: +,-,*,/,%");
        ch = sc.next().charAt(0);

        System.out.print("Enter the values of x and y: ");
        a = sc.nextFloat();
        b = sc.nextFloat();

        switch(ch){

            case '+':
                System.out.println("The sum is: "+(a+b));
                break;

            case '-':
                System.out.println("The difference is: "+(a-b));
                break;
            
            case '*':
                System.out.println("The product is: "+(a*b));
                break;

            case '/':
                System.out.println("The quotient is: "+(a/b));
                break;
            
            case '%':
                System.out.println("The remainder is: "+(a%b));
                break;

            default:
                System.out.println("Enter the valid choice.");
                break;

            
            
        }
        

    }
    
}
