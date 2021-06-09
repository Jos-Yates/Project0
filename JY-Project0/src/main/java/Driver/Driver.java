package Driver;

import java.util.List;
import java.util.Scanner;

import models.Customer;
import repositories.CustomerDAO;
import repositories.EmployeeDAO;
import repositories.TransactionHIstoryDAO;

public class Driver {
	
	// Control the application (Application / Presentation layer)
	// Switch statements go HERE and Scanner!
	//Collect input and output print statements!
	
	private static EmployeeDAO ed = new EmployeeDAO();
	private static CustomerDAO cd = new CustomerDAO();
	private static TransactionHIstoryDAO tshd = new TransactionHIstoryDAO();
	public static Customer customer;
	
	public static void main(String[] args) {
	
		
		Scanner scanner = new Scanner(System.in);
		String menuSelection = "0";
		while (menuSelection != "3") {
			System.out.println("Welcome to Bank Co.! \nPlease select an option:");
			System.out.println("1. Log-in");
			System.out.println("2. Register");
			System.out.println("3. Quit");
			
			menuSelection = scanner.nextLine();
			switch (menuSelection) {
				case "1": {
					callLogin(scanner);
					break;
				}
				case "2": {
					customer = callRegister(scanner);
					customer = cd.add(customer); //This adds the customer to the DB
					break;
				}
				case "3": {
					System.out.println("Have a nice day!");
					break;
				}
				default: {
					System.out.println("Error! Please place a correct input!");
				}
			}
			
		} // End of While
		scanner.close();
	}
	
	private static void callLogin(Scanner scanner) {
		System.out.println("Username:");
		String userName = scanner.nextLine();
		
		System.out.println("\n");
		
		System.out.println("Password:");
		String password = scanner.nextLine();
		
		customer = cd.getByUsernameAndPassword(userName, password); //This checks all of the available usernames and passwords thru CustomerDAO
		
		if (customer != null) {
			System.out.println("Success!");
			callCustomerMenu(scanner);
			
		} else {
			System.out.println("Incorrect Username and/or Password!");
			callLogin(scanner);
		}
		
		
	}
	
	private static Customer callRegister(Scanner scanner) {
		System.out.println("Please provide the following information:");
		System.out.println("Full name");
		String fullName = scanner.nextLine();
		
		System.out.println("\n");
		
		System.out.println("Username");
		String userName = scanner.nextLine();
		
		System.out.println("\n");
		
		System.out.println("Password");
		String password = scanner.nextLine();
		
		String menuSelection2 = "0";
			System.out.println("Is this correct? \n Full name: " + fullName + " , Username: " + userName + " , Password: " + password);
			System.out.println("1. Yes");
			System.out.println("2. No");
		
		
		
		
		
		menuSelection2 = scanner.nextLine();
		switch (menuSelection2) {
		case "1": {
			System.out.println("Thank you! You will be notified shortly when your account is approved!");
			Customer customer = new Customer();
			customer.setName(fullName);
			customer.setUsername(userName);
			customer.setPassword(password);
			customer.setAccount_balance(500);
			return customer;
		}
		case "2": {
			return callRegister(scanner);
		}
		default: {
			System.out.println("Error! Please place a correct input!");
			return null;
		}
		
		
		}
	}
	
	private static void callCustomerMenu(Scanner scanner) {
		
		System.out.println("Total funds: $" + customer.getAccount_balance());
		
		System.out.println("Welcome! Please select an option:" ); // Find a way to implement the full name here!
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer Funds");
		System.out.println("4. Log Out");
		
		
		
		String menuSelection3 = scanner.nextLine();
		switch (menuSelection3) {
			case "1": {
				callDeposit(scanner);
				break;
			}
			case "2": {
				callWithdraw(scanner);
				break;
			}
			case "3": {
				callTransfer(scanner);
				break;
			}
			case "4": {
				System.out.println("Thank you for choosing Bank Co.! \nHave a nice day!");
				break;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callCustomerMenu(scanner);
			}
		}
		
	}
	
	
	private static void callDeposit(Scanner scanner) {
		System.out.println("Please specify deposit total:" );
		
		int account_Balance = scanner.nextInt();
		
		System.out.println("Is this the correct total: " + account_Balance + "?\n1. Yes\n2. No");
		
		scanner.nextLine();
		
		String menuSelection4 = scanner.nextLine();
		switch (menuSelection4) {
			case "1": {
				System.out.println(account_Balance + " sent!");
				customer.setAccount_balance(customer.getAccount_balance() + account_Balance);
				cd.update(customer);
				callCustomerMenu(scanner);
				break;
			}
			case "2": {
				callCustomerMenu(scanner);
				break;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callDeposit(scanner);
			}
		}
	}
	
	private static void callWithdraw(Scanner scanner) {
		System.out.println("Please specify withdraw total:" );
		
		int withdrawAmount = scanner.nextInt();
		
		System.out.println("Is this the correct total: " + withdrawAmount + "?\n1. Yes\n2. No");
		
		scanner.nextLine(); //This (generally) has to be after scanner.nextInt when going to scanner.nextLine to avoid it triggering the error-default
		
		String menuSelection5 = scanner.nextLine();
		switch (menuSelection5) {
			case "1": {
				
				if (customer.getAccount_balance() >= withdrawAmount) {
				System.out.println(withdrawAmount + " withdrawn!");
				customer.setAccount_balance(customer.getAccount_balance() - withdrawAmount);
				cd.update(customer);
				callCustomerMenu(scanner); } else {
					
					System.out.println("Warning! You are withdrawing too much!");
					callCustomerMenu(scanner);
					
				}
				break;
			}
			case "2": {
				callCustomerMenu(scanner);
				break;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callWithdraw(scanner);
			}
		}
	}
	
	
	private static void callTransfer(Scanner scanner) {
		System.out.println("Please specify recipient:" );
		String userName = scanner.nextLine();
		
		// Insert code to send funds to account name. Add an error message?
		
		System.out.println("Please specify amount total:" );
		int account_Balance = scanner.nextInt();
		
		scanner.nextLine();
		
		System.out.println("Is this the correct recipient, " + userName + " and total: " + account_Balance + "?\n1. Yes\n2. No");
		
	
		String menuSelection6 = scanner.nextLine();
		switch (menuSelection6) {
			case "1": {
				System.out.println(account_Balance + " sent to " + userName + "!");
				customer.setAccount_balance(customer.getAccount_balance() - account_Balance);
				cd.update(customer);
				Customer recipient = cd.getByUsername(userName);
				recipient.setAccount_balance(recipient.getAccount_balance() + account_Balance);
				cd.update(recipient);
				callCustomerMenu(scanner);
				break;
			}
			case "2": {
				callCustomerMenu(scanner);
				break;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callTransfer(scanner);
			}
		}
	}
	
}