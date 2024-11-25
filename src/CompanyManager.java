import java.sql.*;
import java.util.*;

public class CompanyManager {
    private static final String URL = "jdbc:mysql://localhost:3306/company_db";
    private static final String USER = "root"; // MySQL username
    private static final String PASSWORD = "Ym01279951749#"; // MySQL password
    private static Connection connection;

    // Establish a connection to the MySQL database
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add a department
    public static void addDepartment(String name) {
        try {
            String query = "INSERT INTO departments (name) VALUES (?)";
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Department added with ID: " + rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add an employee to a department
    public static void addEmployee(String fullName, int age, double salary, int departmentId) {
        try {
            String query = "INSERT INTO employees (full_name, age, salary, department_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, fullName);
                ps.setInt(2, age);
                ps.setDouble(3, salary);
                ps.setInt(4, departmentId);
                ps.executeUpdate();
                updateDepartmentEmployeeCount(departmentId, 1);  // Increment the employee count for the department
                System.out.println("Employee added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove an employee
    public static void removeEmployee(int employeeId) {
        try {
            String query = "SELECT department_id FROM employees WHERE id = ?";
            int departmentId = 0;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, employeeId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        departmentId = rs.getInt(1);
                    }
                }
            }
            query = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, employeeId);
                ps.executeUpdate();
                updateDepartmentEmployeeCount(departmentId, -1);  // Decrement the employee count for the department
                System.out.println("Employee removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a department's name
    public static void updateDepartment(int departmentId, String newName) {
        try {
            String query = "UPDATE departments SET name = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, newName);
                ps.setInt(2, departmentId);
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Department updated successfully.");
                } else {
                    System.out.println("Department not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an employee's details
    public static void updateEmployee(int employeeId, String newFullName, int newAge, double newSalary) {
        try {
            String query = "UPDATE employees SET full_name = ?, age = ?, salary = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, newFullName);
                ps.setInt(2, newAge);
                ps.setDouble(3, newSalary);
                ps.setInt(4, employeeId);
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Employee updated successfully.");
                } else {
                    System.out.println("Employee not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to update employee count for a department
    private static void updateDepartmentEmployeeCount(int departmentId, int change) {
        try {
            String query = "UPDATE departments SET employee_count = employee_count + ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, change);
                ps.setInt(2, departmentId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show all departments
    public static void showDepartments() {
        try {
            String query = "SELECT * FROM departments";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    System.out.println("Department ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Employees: " + rs.getInt("employee_count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show all employees in a department
    public static void showEmployeesInDepartment(int departmentId) {
        try {
            String query = "SELECT * FROM employees WHERE department_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, departmentId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("Employee ID: " + rs.getInt("id") + ", Name: " + rs.getString("full_name") + ", Age: " + rs.getInt("age") + ", Salary: " + rs.getDouble("salary"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show the total salary in a department
    public static void showTotalSalaryInDepartment(int departmentId) {
        try {
            String query = "SELECT SUM(salary) AS total_salary FROM employees WHERE department_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, departmentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Total salary in department: " + rs.getDouble("total_salary"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the connection to the database
    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
