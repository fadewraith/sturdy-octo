package comparatordemo;

public class StudentComparatorDemo {
    public Integer id;
    public String name;

    public StudentComparatorDemo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
