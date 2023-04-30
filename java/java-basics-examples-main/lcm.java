// to find the lcm of two numbers

import java.util.*;

class lcm {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n1,n2,max;
        System.out.print("Enter two numbers: ");
        n1 = sc.nextInt();
        n2 = sc.nextInt();
        max = (n1>n2) ? n1 : n2;
        while(true){
            if((max%n1==0) && (max%n2==0)){
                System.out.print("The LCM of "+n1+" & "+n2+" is "+max);
                break;
            }
            max++;
        }
        System.out.println();
    }
    
}
