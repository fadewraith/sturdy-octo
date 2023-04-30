// thread class by implementing ruunable thread interface

class Thread1 implements Runnable{
    public void run(){
        for(int i=1;i<=50;i++){
            System.out.print(i+"\t");
        }
        System.out.println();
    }
}

class threadD2{
    public static void main(String args[]){
        Thread1 th = new Thread1();
        Thread t = new Thread(th);
        t.start();
        for(int i=1;i<=20;i++){
            System.out.print("main: "+i+"\t");
        }
        System.out.println();
    }
}