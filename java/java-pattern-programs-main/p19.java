/*
11111
2222
333
44
5
*/

class p19 {
    public static void main(String args[]){
        int k=1;
        for(int i=5;i>=1;i--){
            for(int j=1;j<=i;j++){
                System.out.print(k);
            }
            System.out.println();
            k++;
        }
    }
    
}
