import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            CompanyManager.connect();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {

                System.out.println("\n1. Add Department");
                System.out.println("2. Add Employee");
                System.out.println("3. Remove Employee");
                System.out.println("4. Update Department");
                System.out.println("5. Update Employee");
                System.out.println("6. Show Departments");
                System.out.println("7. Show Employees in Department");
                System.out.println("8. Show Total Salary in Department");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:

                        System.out.print("Enter department name: ");
                        String deptName = scanner.nextLine();
                        CompanyManager.addDepartment(deptName);
                        break;
                    case 2:

                        System.out.print("Enter employee full name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        System.out.print("Enter salary: ");
                        double salary = scanner.nextDouble();
                        System.out.print("Enter department ID: ");
                        int deptId = scanner.nextInt();
                        CompanyManager.addEmployee(name, age, salary, deptId);
                        break;
                    case 3:

                        System.out.print("Enter employee ID to remove: ");
                        int empId = scanner.nextInt();
                        CompanyManager.removeEmployee(empId);
                        break;
                    case 4:

                        System.out.print("Enter department ID to update: ");
                        int deptIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter new department name: ");
                        String newDeptName = scanner.nextLine();
                        CompanyManager.updateDepartment(deptIdToUpdate, newDeptName);
                        break;
                    case 5:

                        System.out.print("Enter employee ID to update: ");
                        int empIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter new employee full name: ");
                        String newFullName = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        System.out.print("Enter new salary: ");
                        double newSalary = scanner.nextDouble();
                        CompanyManager.updateEmployee(empIdToUpdate, newFullName, newAge, newSalary);
                        break;
                    case 6:

                        CompanyManager.showDepartments();
                        break;
                    case 7:

                        System.out.print("Enter department ID: ");
                        int departmentId = scanner.nextInt();
                        CompanyManager.showEmployeesInDepartment(departmentId);
                        break;
                    case 8:

                        System.out.print("Enter department ID: ");
                        int deptSalaryId = scanner.nextInt();
                        CompanyManager.showTotalSalaryInDepartment(deptSalaryId);
                        break;
                    case 0:

                        running = false;
                        break;
                    default:

                        System.out.println("Invalid option, please try again.");
                }
            }


            CompanyManager.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

