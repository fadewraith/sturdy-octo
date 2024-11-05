package genericsdemo;

interface Printable {
    void print();
}

class MyNumber extends Number implements Printable {
    private final int value;

    public MyNumber(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.println("genericsdemo.MyNumber: " + value);
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}

// order for extends - first class, then any number of interfaces
// here only extends work, not 'implements'
class Boxx<T extends Number & Printable>{
    private T item;

    public Boxx(T item) {
        this.item = item;
    }

    public void display() {
        item.print();
    }

    public T getItem() {
        return item;
    }
}
enum Operations {
    ADD, SUBTRACT, MULTIPLY, DIVIDE;

    public <T extends Number> double apply(T a, T b) {
        switch (this) {
            case ADD:
                return a.doubleValue() + b.doubleValue();
            case SUBTRACT:
                return a.doubleValue() - b.doubleValue();
            case MULTIPLY:
                return a.doubleValue() * b.doubleValue();
            case DIVIDE:
                return a.doubleValue() / b.doubleValue();
            default:
                throw new AssertionError("Unknown operation: " + this);
        }
    }
}

public class Test{

    public static <T> void printArray(T[] array) {
        int len = 0;
        for(T element : array) {
            System.out.print(element);
            if(len < (array.length - 1)) {
                System.out.print(", ");
            }
            len++;
        }
        System.out.println();
    }
    public static void main(String[] args) {
//        genericsdemo.MyNumber myNumber = new genericsdemo.MyNumber(12);
//        genericsdemo.Boxx<genericsdemo.MyNumber> box = new genericsdemo.Boxx<>(myNumber);
//        box.display();

//        genericsdemo.Day day = genericsdemo.Day.MONDAY;
//        System.out.println(day);

//        Integer[] intArr = { 1, 2, 3, 4 };
//        String[] strArr = { "hello", "world" };
//        printArray(intArr);
//        printArray(strArr);
//        display(12);
//        display("string");
//        display(12.34);

        double result1 = Operations.ADD.apply(10, 20);
        double result2 = Operations.MULTIPLY.apply(5.5, 4);
        System.out.println(result1);  // Output: 30.0
        System.out.println(result2);  // Output: 22.0

    }

    public static <T> void display(T element) {
        System.out.println("Generic display: " + element);
    }

    public static void display(Integer element) {
        System.out.println("Integer display: " + element);
    }
}

enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, RIDAY, SATURDAY
}

