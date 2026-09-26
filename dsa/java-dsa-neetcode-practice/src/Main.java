import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Cat myCat = new Cat("Stella", 6);
        Field[] declaredFields = myCat.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            System.out.println(field.getName());
//        }
        for (Field field : declaredFields) {
            if(field.getName().equals("name")) {
                field.setAccessible(true);
                field.set(myCat, "John Doe");
            }
        }
//        System.out.println(myCat.getName());
        Method[] declaredMethods = myCat.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
//            System.out.println(method.getName());
            if(method.getName().equals("heyThisIsPrivate")) {
                method.setAccessible(true);
                method.invoke(myCat);
            }
            if(method.getName().equals("thisIsAPrivateStaticMethod")) {
                method.setAccessible(true);
                method.invoke(null);
            }
        }
    }
}
