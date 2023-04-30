// inheritance basic example

class Rundog{

    public void bark(){

        System.out.println("\\_______________________________/");
        System.out.println("_____________world_______________");

    }
}

class Bulldog extends Rundog{

    public void grow(){

        System.out.println("is...................................there");
        System.out.println("_____________nothing_______________");

    }

}

class inheriBasic{

    public static void main(String args[]){

        Bulldog dog = new Bulldog();
        dog.bark();
        dog.grow();
    }
}