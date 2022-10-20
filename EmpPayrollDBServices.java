package demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EmpPayrollDBServices{
	private PreparedStatement employeePayrollDataStatement;
	private static EmpPayrollDBServices employeePayrollDBService;

	private EmpPayrollDBServices() {

	}

	public static EmpPayrollDBServices getInstance() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new EmpPayrollDBServices();
		return employeePayrollDBService;
	}

	private Connection getConnection() throws SQLException {

		String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll";
		String userName = "root";
		String password = "mysql";
		Connection connection;

		System.out.println("Connecting to the database : " + jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is Succcessfully Established!! " + connection);

		return connection;
	}

	private List<EmpPayrollData> getEmployeePayrollData(ResultSet resultSet) {

		List<EmpPayrollData> employeePayrollList = new ArrayList<>();

		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basicSalary = resultSet.getDouble("salary");
				employeePayrollList.add(new EmpPayrollData(id, name, basicSalary));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;

	}

	public List<EmpPayrollData> getEmployeePayrollData(String name) {

		List<EmpPayrollData> employeePayrollList = null;
		if (this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public List<EmpPayrollData> readData() {

		String sqlStatement = "SELECT emp_id, emp_name, basic_pay, start FROM employee JOIN payroll ON employee.payroll_id = payroll.payroll_id;";
		List<EmpPayrollData> employeePayrollList = new ArrayList<>();

		try (Connection connection = getConnection();) {
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);

			while (resultSet.next()) {
				int id = resultSet.getInt("emp_id");
				String name = resultSet.getString("emp_name");
				double basicSalary = resultSet.getDouble("basic_pay");
				employeePayrollList.add(new EmpPayrollData(id, name, basicSalary));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

private void prepareStatementForEmployeeData() {

	try {
		Connection connection = this.getConnection();
		String sqlStatement = "SELECT * FROM employee,payroll WHERE employee.payroll_id = payroll.payroll_id AND name = ?;";
		employeePayrollDataStatement = connection.prepareStatement(sqlStatement);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}}