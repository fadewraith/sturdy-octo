package lambdaexpressiondemo;

public class SoftwareEngineer {
//    @Override
//    public String getSalary() {
//        return "10";
//    }
//
//    @Override
//    public String getDesignation() {
//        return "software engineer";
//    }

    public static void main(String[] args) {
//        Employee2 employee2 = new SoftwareEngineer();
//        System.out.println(employee2.getSalary());
//        System.out.println(employee2.getDesignation());

//        anonymous inner class
//        Employee2 employee2 = new Employee2() {
//            @Override
//            public String getSalary() {
//                return "anonymous 10 salary";
//            }
//
//            @Override
//            public String getDesignation() {
//                return "anonymous inner designation";
//            }
//        };

        doSomething();

    }

//    variable declared globally cant be accessed inside anonymous inner class

    private static void doSomething() {
        int a = 2;
//        variable used inside lambda expression, should be final
        Employee e3 = () -> "100";
        System.out.println(e3.getName());
    }
}
