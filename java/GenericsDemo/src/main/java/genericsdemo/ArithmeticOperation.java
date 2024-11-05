package genericsdemo;

interface Calculator<T> {
    T calculate(T a, T b);
}

enum ArithmeticOperation implements Calculator<Integer> {
    ADD {
        @Override
        public Integer calculate(Integer a, Integer b) {
            return a + b;
        }
    },
    SUBTRACT {
        @Override
        public Integer calculate(Integer a, Integer b) {
            return a - b;
        }
    };

    // Additional operations can be added in the same way.
}