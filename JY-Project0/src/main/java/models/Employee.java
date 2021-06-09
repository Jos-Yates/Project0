package models;

public class Employee {
	private Integer id;
	private String name;
	private String username;
	private String password;
	private Integer account_balance;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String username, String password, Integer account_balance) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.account_balance = account_balance;
	}
	
	public Employee(Integer id, String name, String username, String password, Integer account_balance) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.account_balance = account_balance;
	}
	
	//Getters and Setters
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Integer account_balance) {
		this.account_balance = account_balance;
	}

	//Note for self: the Override compares two objects, specifically equals and hashcode
	//This just acts as a default for models, even if we don't end up using them
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account_balance == null) ? 0 : account_balance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (account_balance == null) {
			if (other.account_balance != null)
				return false;
		} else if (!account_balance.equals(other.account_balance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", account_balance=" + account_balance + "]";
	}
}