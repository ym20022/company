public class Employee {
    private int id;
    private String fullName;
    private int age;
    private double salary;
    private int departmentId;

    public Employee(int id, String fullName, int age, double salary, int departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", fullName='" + fullName + "', age=" + age + ", salary=" + salary + ", departmentId=" + departmentId + "}";
    }
}
