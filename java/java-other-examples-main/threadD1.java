// demo of java threading by extending thread class

class MyThread extends Thread{

    public void run(){

        for(int i=1;i<=50;i++){
            System.out.print(i+"\t");
        }

        System.out.println();
    }
}

class threadD1{
    public static void main(String args[]){

        MyThread my_thread = new MyThread();
        my_thread.start();

        for(int i=1;i<=20;i++){
            System.out.print("Main: "+i+"\t");
        }

        System.out.println();
    }
}