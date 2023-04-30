// Write a java program to find the frequency of each element in array.


import java.util.*;

class arrFreq{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        int arr[] = new int[10];
        int visited = -1;
        int fr[] = new int[arr.length];

        System.out.print("Enter the elements in array: ");
        for(int i=0;i<arr.length;i++){
            arr[i] = sc.nextInt();
        }

        for(int i=0;i<arr.length;i++){
            int count = 1;
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]==arr[j]){
                    count++;
                    fr[j] = visited;
                }
            }
            if(fr[i]!=visited)
                fr[i] = count;
        }
        System.out.println("---------------------");  
        System.out.println(" Element | Frequency");  
        System.out.println("---------------------");  
        for(int i = 0; i < fr.length; i++){  
            if(fr[i] != visited)  
                System.out.println("    " + arr[i] + "    |    " + fr[i]);  
        }  
        System.out.println("---------------------");  
      

    }
}