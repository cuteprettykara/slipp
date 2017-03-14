package net.slipp.user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAOTest {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOTest.class);
	
	private UserDAO userDao;
	
	@Before
	public void setup() {
		userDao = new UserDAO();
	}
	
	@Test
	public void crud() throws Exception {
		User user = UserTest.TEST_USER;
		userDao.removeUser(user.getUserId());
		userDao.addUser(user);
		User dbUser = userDao.findByUserId(user.getUserId());
		assertEquals(user, dbUser);
		
		User updateUser = new User(user.getUserId(), "uPassword", "update name", "update@slipp.net");
		userDao.updateUser(updateUser);
		dbUser = userDao.findByUserId(updateUser.getUserId());
		assertEquals(updateUser, dbUser);
	}
	
	@Test
	public void 존재하지_않는_사용자_조회() throws Exception {
		User user = UserTest.TEST_USER;
		userDao.removeUser(user.getUserId());
		User dbUser = userDao.findByUserId(user.getUserId());
		assertNull(dbUser);
	}

	@Test
	public void findUsers() throws Exception {
		List<User> users = userDao.findUsers();
		assertTrue(users.size()>0);
		logger.debug("Users : {}", users);
	}
}
