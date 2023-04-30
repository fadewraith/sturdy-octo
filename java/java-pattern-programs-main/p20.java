/*
     *****
    *   *
   *   *
  *   *
 *****
*/

class p20 {
    public static void main(String args[]){
        int k=1,l=5;
        for(int i=1;i<=5;i++){
            for(int j=5;j>=i;j--){
                System.out.print(" ");
            }

                for(int j=1;j<=5;j++){
                    if(i==1 || i==5 || j==1 || j==5)
                        System.out.print("*");
                    else
                        System.out.print(" ");
                }

            System.out.println();
        }
    }
    
}
