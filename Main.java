import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:sqlite:payroll.db";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        createTable();

        while (true) {

            System.out.println("\n====== PAYROLL MANAGEMENT SYSTEM ======");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Generate Payslip");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    generatePayslip();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void createTable() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            Statement stmt = conn.createStatement();

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS employee(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT," +
                            "salary REAL)"
            );

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addEmployee() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            sc.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Basic Salary: ");
            double salary = sc.nextDouble();

            String sql = "INSERT INTO employee(name,salary) VALUES(?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setDouble(2, salary);

            pstmt.executeUpdate();

            System.out.println("Employee Added Successfully!");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewEmployees() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

            System.out.println("\nID\tName\tSalary");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + "\t"
                                + rs.getString("name") + "\t"
                                + rs.getDouble("salary"));

            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void searchEmployee() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            System.out.print("Enter Employee ID: ");

            int id = sc.nextInt();

            String sql = "SELECT * FROM employee WHERE id=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                System.out.println("\nEmployee Found");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Salary : " + rs.getDouble("salary"));

            } else {

                System.out.println("Employee Not Found");

            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void generatePayslip() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            System.out.print("Enter Employee ID: ");

            int id = sc.nextInt();

            String sql = "SELECT * FROM employee WHERE id=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");

                double basic = rs.getDouble("salary");

                double hra = basic * 0.20;

                double da = basic * 0.10;

                double gross = basic + hra + da;

                System.out.println("\n========== PAYSLIP ==========");
                System.out.println("Employee ID : " + id);
                System.out.println("Employee Name : " + name);
                System.out.println("Basic Salary : " + basic);
                System.out.println("HRA (20%) : " + hra);
                System.out.println("DA (10%) : " + da);
                System.out.println("----------------------------");
                System.out.println("Gross Salary : " + gross);
                System.out.println("============================");

            } else {

                System.out.println("Employee Not Found");

            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}