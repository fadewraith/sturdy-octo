class power{

  static int pow(int x, int y){

    var prod = 1;

    for(int i=1;i<=y;i++){
      prod *= x;
    }

    return prod;
  }

  public static void main(String args[]){

    int x = pow(7,3);
    
    System.out.println(x);

  }
}
