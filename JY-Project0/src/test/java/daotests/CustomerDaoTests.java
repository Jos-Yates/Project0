package daotests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.Customer;
import repositories.CustomerDAO;



public class CustomerDaoTests {
		
		private CustomerDAO cdao = new CustomerDAO();
		
		@Test
		public void getAllCustomersTest() {
			
			List<Customer> customer = new ArrayList<Customer>();
			Customer expected = new Customer(1, "Test Customer", "DummyAccount101", "readyfortesting", 1000);
			
			Customer c = new Customer("Test Customer", "DummyAccount101", "readyfortesting", 1000);
			
			Customer result = cdao.add(c);

			Assert.assertEquals(expected, result);
			

	}
		
		@Test
		public void getCustomerByidTest () {
			System.out.println(cdao.getById(5));
		}
		@Test
		public void addCustomerTest() {
			Customer c = new Customer ("Add Test");
			
			System.out.println(cdao.add(c));
		}

}