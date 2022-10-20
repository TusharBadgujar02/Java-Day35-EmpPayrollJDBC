package demo;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class EmpPayrollServices {

	private List<EmpPayrollData> employeePayrollList;

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	public EmpPayrollServices() {
		EmpPayrollDBServices.getInstance();
	}

	public EmpPayrollServices(List<EmpPayrollData> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}

	public void printData(IOService fileIo) {
		if (fileIo.equals(IOService.FILE_IO))
			new EmpPayrollFileIO().printData();
	}

	public long countEntries(IOService fileIo) {
		if (fileIo.equals(IOService.FILE_IO))
			return new EmpPayrollFileIO().countEntries();
		return 0;
	}

	private void readEmployeePayrollData(Scanner consoleInputReader) {

		System.out.println("Enter the Employee Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter the Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter the Employee Salary : ");
		double salary = consoleInputReader.nextDouble();

		employeePayrollList.add(new EmpPayrollData(id, name, salary));
	}

	public static void main(String[] args) {

		System.out.println("*****Welcome To Employee Payroll Program*****");
		ArrayList<EmpPayrollData> employeePayrollList = new ArrayList<EmpPayrollData>();
		EmpPayrollServices employeePayrollService = new EmpPayrollServices(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);

		employeePayrollService.readEmployeePayrollData(consoleInputReader);
	}
}