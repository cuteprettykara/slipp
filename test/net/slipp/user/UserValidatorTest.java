package net.slipp.user;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserValidatorTest {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void UserIdIsNull() {
		User user = new User(null, "password", "name", "");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
		assertEquals("반드시 값이 있어야 합니다.", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void UseerIdLength() throws Exception {
		User user = new User("us", "password", "name", "");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
		
		user = new User("userIduserId2", "password", "name", "");

		constraintViolations = validator.validate(user);

		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void email() throws Exception {
		User user = new User("userId", "password", "name", "email");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void invalidUser() throws Exception {
		User user = new User("us", "password", "name", "email");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertEquals(2, constraintViolations.size());
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		
		while(iterator.hasNext()) {
			ConstraintViolation<User> each = iterator.next();
			System.out.println(each.getPropertyPath() + " : "  + each.getMessage());			
		}
	}
}
