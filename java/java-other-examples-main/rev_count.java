// to pribnt numbers from 10 to 1 with delay of 1-1 sec

class rev_count{
    public static void main(String args[]) throws InterruptedException{
        // Thread t = new Thread();
        for(int i=10;i>=1;i--){
            System.out.print(i+"\t");
            Thread.sleep(600);
        }
        System.out.println();
    }
}