package Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;
import models.Transaction;
import models.Account;
import repositories.CustomerDAO;
import repositories.TransactionHIstoryDAO;
import repositories.AccountDAO;

public class Driver {
	
	// Control the application (Application / Presentation layer)
	// Switch statements go HERE and Scanner!
	//Collect input and output print statements!
	
	private static CustomerDAO cd = new CustomerDAO();
	private static AccountDAO ad = new AccountDAO();
	private static TransactionHIstoryDAO thd = new TransactionHIstoryDAO();
	public static final Logger logger = LogManager.getLogger(Driver.class);
	public static Customer customer;
	static String menuSelection = "0";
	public static void main(String[] args) {
	
		
		Scanner scanner = new Scanner(System.in);
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
		if (customer.getEmployee()) {
			System.out.println("Success!");
			logger.info(userName + " has logged in!");
			callEmployeeMenu(scanner);
			
		} else {	
			
			if (customer != null) {
				System.out.println("Success!");
				logger.info(userName + " has logged in!");
				callCustomerMenu(scanner);
				
			} else {
				System.out.println("Incorrect Username and/or Password!");
				callLogin(scanner);
			}
		}
		}
	//}
	
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
			logger.info(fullName + " has registered!");

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
		
		List<Account> accounts = ad.getAllByCustomerId(customer.getId());
		System.out.println("Your accounts:");
		for (Account a:accounts) {
			System.out.println(a.getId() + ": $" + a.getBalance());
		}
		
		System.out.println("Welcome! Please select an option:" );
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer Funds");
		System.out.println("4. Apply for New Account");
		System.out.println("5. Log Out");
		
		
		
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
				callAccountRegister(scanner);
				break;
			}
			case "5": {
				System.out.println("Thank you for choosing Bank Co.! \nHave a nice day!");
				//menuSelection = "3";
				return;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callCustomerMenu(scanner);
			}
		}
		
	}
	
private static void callEmployeeMenu(Scanner scanner) {
		
		List<Account> accounts = ad.getAllByCustomerId(customer.getId());
		System.out.println("Your accounts:");
		for (Account a:accounts) {
			System.out.println(a.getId() + ": $" + a.getBalance());
		}
		
		System.out.println("Welcome! Please select an option:" );
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer Funds");
		System.out.println("4. Apply for New Account");
		System.out.println("5. Approve Accounts");
		System.out.println("6. View Customer Accounts");
		System.out.println("7. View Transactions");
		System.out.println("8. Log Out");
		
		
		
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
				callAccountRegister(scanner);
				break;
			}
			case "5": {
				callApproveAccount(scanner);
				break;
			}
			case "6": {
				callViewCustomerAccount(scanner);
				break;
			}
			case "7": {
				callViewTransactions(scanner);
				break;
			}
			case "8": {
				System.out.println("Thank you for choosing Bank Co.! \nHave a nice day!");
				//menuSelection = "3";
				break;
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callCustomerMenu(scanner);
			}
		}
		
	}
	

	private static void callViewTransactions(Scanner scanner) {
		
		System.out.println("Transaction log:");
		List<Transaction> transactions = thd.getAll();
		for (Transaction t : transactions) {
			
			System.out.println(t);
			scanner.nextLine();
			callEmployeeMenu(scanner);
			
		}
	}
	
	private static void callViewCustomerAccount(Scanner scanner) {
		System.out.println("Choose a Customer:");
		List<Customer> customers = cd.getAll();
		for (Customer c : customers) {
			System.out.println(c.getId() + ": " + c.getName());
		}
		int selection = scanner.nextInt();
		scanner.nextLine();
		
		Customer customer = customers.get(selection - 1);
		List<Account> accounts = ad.getAllByCustomerId(selection);
		
		for (Account a : accounts) {
			System.out.println(a.getId() + ": " + a.getBalance());
		}
		scanner.nextLine();
		callEmployeeMenu(scanner);

	}
	
	private static void callAccountRegister(Scanner scanner) {
		System.out.println("Enter account balance:");
		Float startingBalance = scanner.nextFloat();
		scanner.nextLine();
		
		System.out.println("Are you sure you want to start an account with a starting balance of $" + startingBalance + "? \n1. Yes\n2. No");
		
		String menuSelectionIForgot = scanner.nextLine();
		switch (menuSelectionIForgot) {
			case "1": {
				
				if (customer.getEmployee()) { //This is so the Employee can return to the employee menu rather than be booted to the customer menu
					System.out.println("Thank you! An employee will review your application in 1-3 business days and will reach back with your results.");
					Account account = new Account();
					account.setBalance(startingBalance);
					account.setCustomer(customer.getId());
					account.setPending(true);
					ad.add(account);
					logger.info(account + " has applied with a starting balance of $" + startingBalance);
					callEmployeeMenu(scanner);
					break;
	
				} else {
					System.out.println("Thank you! An employee will review your application in 1-3 business days and will reach back with your results.");
					Account account = new Account();
					account.setBalance(startingBalance);
					account.setCustomer(customer.getId());
					account.setPending(true);
					ad.add(account);
					logger.info(account + " has applied with a starting balance of $" + startingBalance);
					callCustomerMenu(scanner);
					break;
				}
			}
			case "2": {
				if (customer.getEmployee()) {
					callEmployeeMenu(scanner);
				} else {
				callCustomerMenu(scanner);
				break;
				}
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callAccountRegister(scanner);
			}
		}
	}
	
	private static void callApproveAccount(Scanner scanner) {
		List<Account> list = ad.getAllPending();
		System.out.println("Pending Accounts:");
		for (Account a : list) {
			System.out.println(a.getId() + ": " + a.getBalance());
			System.out.println("Approve? \n1. Yes\n2. No");
			String menuSelectionApproval = scanner.nextLine();
			switch (menuSelectionApproval) {
				case "1": {
					System.out.println("Account approved!");
					a.setPending(false);
					ad.update(a);
					callEmployeeMenu(scanner);
					break;
				}
				case "2": {
					System.out.println("Account denied! Account will now be deleted.");
					ad.delete(a);
					callEmployeeMenu(scanner);
					break;
				}
				default: {
					System.out.println("Error! Please place a correct input!");
					callApproveAccount(scanner);
				}
			}
		}
		System.out.println("No accounts to approve.");
		callEmployeeMenu(scanner); //This positioned here prevents the console from reverting to the 1st menu if there are no accounts to approve
	}
	
	private static void callDeposit(Scanner scanner) {
		System.out.println("Please specify account:" );
		Integer sourceId = scanner.nextInt();
		scanner.nextLine();
		// Good luck deciphering this mess; it's to help prevent the Employee account from being booted back to the Customer account
		//while also making sure the accounts belong to the appropriate ID
		List<Account> accounts = ad.getAllByCustomerId(customer.getId());
		for (Account a:accounts) {
		if (ad.getById(sourceId).getPending()) {
			if (customer.getEmployee()) {
				System.out.println("Sorry! Account " + sourceId + " is still pending!");
				callEmployeeMenu(scanner);
				break;
			} else {
			System.out.println("Sorry! Account " + sourceId + " is still pending!");
			callCustomerMenu(scanner);
			break;
			}
			
			// v This is what acts as the ID lock. DO NOT MIX IT UP OR USERS WILL ACCESS OTHER ACCOUNTS NOT THEIRS
			
		} if (a.getId() != sourceId) {
			if (customer.getEmployee()) {
				System.out.println("Error! This account is not yours, or is nonexistent.");
				callEmployeeMenu(scanner);
				break;
			} else {
			System.out.println("Error! This account is not yours, or is nonexistent.");
			callCustomerMenu(scanner);
		break;
			}
		} else {
		
		Account source = ad.getById(sourceId);

		
		System.out.println("Please specify deposit total:" );
		
		float account_Balance = scanner.nextFloat();
		
		System.out.println("Are you sure you wish to deposit $" + account_Balance + " from " + sourceId + "? \n1. Yes\n2. No");
		
		scanner.nextLine();
		
		String menuSelection4 = scanner.nextLine();
		switch (menuSelection4) {
			case "1": {
				
				if (customer.getEmployee()) {
					if (account_Balance > 0) {
						System.out.println(account_Balance + " deposited!");
						source.setBalance(source.getBalance() + account_Balance);
						logger.info(account_Balance + " deposited to account " + sourceId);
						ad.update(source);
						Transaction t = new Transaction();
						t.setSource(sourceId);
						t.setType("deposit");
						t.setAmount(account_Balance);
						t.setRecipient(sourceId);
						thd.add(t);
						callCustomerMenu(scanner); 
						break;
					} else {
						
						System.out.println("Warning! Incorrect deposit input!");
						callEmployeeMenu(scanner);
						break;
						
					}
					
				} else {
						if (account_Balance > 0) {
							System.out.println(account_Balance + " deposited!");
							source.setBalance(source.getBalance() + account_Balance);
							logger.info(account_Balance + " deposited to account " + sourceId);
							ad.update(source);
							Transaction t = new Transaction();
							t.setSource(sourceId);
							t.setType("deposit");
							t.setAmount(account_Balance);
							t.setRecipient(sourceId);
							thd.add(t);
							callCustomerMenu(scanner); 
							break;
						} else {
							
							System.out.println("Warning! Incorrect deposit input!");
							callCustomerMenu(scanner);
							
						}
						}
				break;
				}
			
			case "2": {
				if (customer.getEmployee()) {
					callEmployeeMenu(scanner);
					break;
				} else {
				callCustomerMenu(scanner);
				break;
			}
			}
			
			default: {
				System.out.println("Error! Please place a correct input!");
				callDeposit(scanner);
			}
		}
		}
		}
	}
	
	private static void callWithdraw(Scanner scanner) {	
		System.out.println("Please specify account:" );
		Integer sourceId = scanner.nextInt();
		scanner.nextLine();
		
		List<Account> accounts = ad.getAllByCustomerId(customer.getId());
		for (Account a:accounts) {
		if (ad.getById(sourceId).getPending()) {
			if(customer.getEmployee()) {
				System.out.println("Sorry! Account " + sourceId + " is still pending!");
				callEmployeeMenu(scanner);
				break;
			} else {
			System.out.println("Sorry! Account " + sourceId + " is still pending!");
			callCustomerMenu(scanner);
			break;
		} 
		}
		if (a.getId() != sourceId) {
			if (customer.getEmployee()) {
				System.out.println("Error! This account is not yours, or is nonexistent.");
				callEmployeeMenu(scanner);
				break;
			} else {
			
				System.out.println("Error! This account is not yours, or is nonexistent.");
				callCustomerMenu(scanner);
				break;
		}
		}
		
		else {
		
		Account source = ad.getById(sourceId);
		
		System.out.println("Please specify amount:");
		
		float withdrawAmount = scanner.nextFloat();
		
		System.out.println("Are you sure you wish to withdraw $" + withdrawAmount + " from " + sourceId + "? \n1. Yes\n2. No");
		
		scanner.nextLine(); //This (generally) has to be after scanner.nextInt when going to scanner.nextLine to avoid it triggering the error-default
		
		String menuSelection5 = scanner.nextLine();
		switch (menuSelection5) {
			case "1": {
				if (customer.getEmployee()) {
					if (source.getBalance() >= withdrawAmount) {
						System.out.println(withdrawAmount + " withdrawn!");
						source.setBalance(source.getBalance() - withdrawAmount);
						logger.info(withdrawAmount + " withdrawn from account " + sourceId);
						ad.update(source);
						Transaction t = new Transaction();
						t.setSource(sourceId);
						t.setType("withdraw");
						t.setAmount(withdrawAmount);
						t.setRecipient(sourceId);
						thd.add(t);
						callEmployeeMenu(scanner); 
						break;
					} else {
						
						System.out.println("Warning! You are withdrawing too much!");
						callEmployeeMenu(scanner);
						break;
					}
				} else {
				
				if (source.getBalance() >= withdrawAmount) {
					System.out.println(withdrawAmount + " withdrawn!");
					source.setBalance(source.getBalance() - withdrawAmount);
					logger.info(withdrawAmount + " withdrawn from account " + sourceId);
					ad.update(source);
					Transaction t = new Transaction();
					t.setSource(sourceId);
					t.setType("withdraw");
					t.setAmount(withdrawAmount);
					t.setRecipient(sourceId);
					thd.add(t);
					callCustomerMenu(scanner); 
				} else {
					
					System.out.println("Warning! You are withdrawing too much!");
					callCustomerMenu(scanner);
					break;
					
				}
				break;
			}
			}
			case "2": {
				if (customer.getEmployee()) {
					callEmployeeMenu(scanner);
					break;
				} else {
				callCustomerMenu(scanner);
				break;
				}
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callWithdraw(scanner);
			}
		}
		}
		}
	}
	
	
	private static void callTransfer(Scanner scanner) {
		System.out.println("Please specify source:" );
		Integer sourceId = scanner.nextInt();
		scanner.nextLine();
		
		List<Account> accounts = ad.getAllByCustomerId(customer.getId());
		for (Account a:accounts) {
		if (ad.getById(sourceId).getPending()) {
			if (customer.getEmployee()) {
				System.out.println("Sorry! Account " + sourceId + " is still pending!");
				callEmployeeMenu(scanner);
				return;
			} else {
			System.out.println("Sorry! Account " + sourceId + " is still pending!");
			callCustomerMenu(scanner);
			return;
			}
		}
		if (a.getId() != sourceId) {
			if (customer.getEmployee()) {
				System.out.println("Error! This account is not yours, or is nonexistent.");
				callEmployeeMenu(scanner);
				break;
			} else {
			System.out.println("Error! This account is not yours, or is nonexistent.");
			callCustomerMenu(scanner);
		break;
			}
		}
		}
		
		
		System.out.println("Please specify recipient:" );
		Integer recipientId = scanner.nextInt();
		scanner.nextLine();
		
		if (ad.getById(recipientId).getPending()) {
			if (customer.getEmployee()) {
				System.out.println("Sorry! Account " + recipientId + " is still pending!");
				callEmployeeMenu(scanner);
				return;
			} else {
			System.out.println("Sorry! Account " + recipientId + " is still pending!");
			callCustomerMenu(scanner);
			return;
			}
		}
		
		System.out.println("Please specify amount total:" );
		float amount = scanner.nextFloat();
		
		scanner.nextLine();
		
		System.out.println("Are you sure you want to transfer $" + amount + " from " + sourceId + " to " + recipientId + "? \n1. Yes\n2. No");
		
	
		String menuSelection6 = scanner.nextLine();
		switch (menuSelection6) {
			case "1": {
				if (customer.getEmployee()) {
					Account source = ad.getById(sourceId);
					Account recipient = ad.getById(recipientId);
					
					if (source != null && recipient != null) {
						
						if (amount >= source.getBalance() && amount > 0) {
							
							source.setBalance(source.getBalance() - amount);
							recipient.setBalance(recipient.getBalance() + amount);
							logger.info(amount + " transfered from account " + sourceId + " to account " + recipientId);
							System.out.println("$" + amount + " sent!");
							
							ad.update(source);
							ad.update(recipient);
							Transaction t = new Transaction();
							t.setSource(sourceId);
							t.setType("transfer");
							t.setAmount(amount);
							t.setRecipient(recipientId);
							thd.add(t);
							
						} else {
							
							System.out.println("Insufficient funds.");
							callEmployeeMenu(scanner);
							break;
						}
						
					}
					callEmployeeMenu(scanner);
				} else {
				Account source = ad.getById(sourceId);
				Account recipient = ad.getById(recipientId);
				
				if (source != null && recipient != null) {
					
					if (amount >= source.getBalance() && amount > 0) {
						
						source.setBalance(source.getBalance() - amount);
						recipient.setBalance(recipient.getBalance() + amount);
						logger.info(amount + " transfered from account " + sourceId + " to account " + recipientId);
						System.out.println("$" + amount + " sent!");
						
						ad.update(source);
						ad.update(recipient);
						Transaction t = new Transaction();
						t.setSource(sourceId);
						t.setType("transfer");
						t.setAmount(amount);
						t.setRecipient(recipientId);
						thd.add(t);
						
					} else {
						
						System.out.println("Insufficient funds.");
						callCustomerMenu(scanner);
						
					}
					
				}
				

				callCustomerMenu(scanner);
				
				
				
				break;
				}
			}
			case "2": {
				if (customer.getEmployee()) {
					callEmployeeMenu(scanner);
				} else {
				callCustomerMenu(scanner);
				break;
			}
			}
			default: {
				System.out.println("Error! Please place a correct input!");
				callTransfer(scanner);
			}
		}
	}
}