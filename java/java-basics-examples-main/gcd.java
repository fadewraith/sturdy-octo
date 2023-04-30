class gcd{

  static void gcd(int x, int y){
    int z=0;

    for(int i=1;i<=x && i<=y;i++){

      if(x%i==0 && y%i==0)
        z = i;
    }
    System.out.println(z);

  }

  public static void main(String args[]){
    gcd(153,81);
  }
}
