/*
*           
**
----------
1
22
----------
1
12
----------
A
BB
----------
A
BC
DEF
----------
A
AB
ABC
----------
1 
2 3 
4 5 6 
----------
*/

class p1{
    public static void main(String args[]){
        int k=1;
        for(int i=1;i<=5;i++){
            
            for(int j=1;j<=i;j++){
                System.out.print(k+" ");
                k++;
            }
        System.out.println();
        }
    }
}