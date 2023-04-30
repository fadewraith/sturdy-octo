/*
Develop an interface named Drawable. In Drawable interface create an abstract method named
draw(). Now implement Drawable interface in Rectangle class and give definition of draw()
method. And also implement Drawable interface in Circle class and give definition of draw()
method. Now test the class Rectangle and Circle.
*/

interface drawable{
    void draw();
}

class Rectangle implements drawable{

    public void draw(){
        System.out.println("This is rectangle class");
    }
}

class Circle extends Rectangle{
    public void draw(){
        System.out.println("This is circle class.");
    }
}

class interDraw{
    public static void main(String args[]){
        Rectangle rect = new Rectangle();
        rect.draw();

        Circle cir = new Circle();
        cir.draw();
    }
}