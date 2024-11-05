package optionaldemo;

import javax.swing.text.html.Option;
import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
//        ex 1
//        String name = getName(1);
//        if(name != null)
//        System.out.println(name.toUpperCase());

//        ex 2
        Optional<String> name = getName(2);
        if(name.isPresent()) {
            System.out.println(name);
            System.out.println(name.get());
        }
//        name.ifPresent(x -> System.out.println(x));
        name.ifPresent(System.out::println);

//        ex 3
//        String nameToUse = name.isPresent() ? name.get() : "default";
        String nameToUse = name.orElse("default");
//        System.out.println(nameToUse);

//        ex 4
        String s = name.orElseGet(() -> "NA");
//        System.out.println(s);

//        ex 5
//        String s1 = name.orElseThrow(() -> new NoSuchElementException());
        String s1 = name.orElseThrow(NoSuchElementException::new);
    }

    private static Optional<String> getName(int id) {
        String name = null;
//        return Optional.of(name);
//         will check for null
        return Optional.ofNullable(name);
//        return Optional.empty();
    }
}
