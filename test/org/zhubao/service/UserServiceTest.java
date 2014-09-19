/**
 * 
 */

package org.zhubao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhubao.dao.UserDAO;
import org.zhubao.model.User;
import org.zhubao.model.User.Gender;

/**
 * @author Jason.Zhu
 * @date 2013-11-12
 * @email jasonzhu@augmentum.com.cn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private UserService userServiceImpl;

	@Autowired
	private UserDAO userDAO;

	@Test
	public void saveUser() {
		// userServiceImpl.removeAll();
		List<User> users = new ArrayList<User>();
		for (int i = 1; i < 100; i++) {
			User user = new User();
			user.setUsername("Jason" + i);
			user.setEmail("baogee" + i + "@vip.qq.com");
			user.setGender(User.Gender.MALE);
			user.setPassword("123456");
			user.setBornDate(new Date());
			users.add(user);
			userServiceImpl.saveUser(user);
		}
		// userServiceImpl.batchSaveUsers(users);
	}

	@Test
	public void getUserByPager() {

		Page<User> page = userServiceImpl.findUserByPage(0, 7);
		for (User user : page.getContent()) {
			System.out.println(user.getUsername());
		}

		System.out.println("Number: " + page.getNumber());
		System.out.println("NumberElements : " + page.getNumberOfElements());
		System.out.println("Size: " + page.getSize());
		System.out.println("TotalElements: " + page.getTotalElements());
		System.out.println("TotalPages: " + page.getTotalPages());
		System.out.println();
	}

	@Test
	public void getUserByName() {

		User user = userServiceImpl.findByName("Jason2");
		System.out.println(user);
	}

	@Test
	public void getUserByEmail() {

		User user = userServiceImpl.findByUserEmail("baogee1@vip.qq.com");
		System.out.println(user);
	}

	@Test
	public void getUserByGender() {

		Page<User> page = userServiceImpl.findByGender(Gender.MALE, 0, 10);
		for (User user : page.getContent()) {
			System.out.println(user.getUsername());
		}
	}

	@Test
	public void findByCondition() {
		User user = new User();
		user.setEmail("baogee1@vip.qq.com");
		Page<Object[]> page = userServiceImpl.findByCondition(user);
		for (Object[] o : page.getContent()) {
			for (Object oo : o) {
				System.out.println(oo);
			}
		}
	}

	@Test
	public void findBySort() {
		System.out.println(userDAO.findAll(new Sort(Direction.DESC, "userId")));

	}

	@Test
	public void findAll() {
		userServiceImpl.findAll();

		userServiceImpl.findAll();
	}

	@Test
	public void findById() {
		userServiceImpl.findUserById(1);
		System.out.println("second");
		userServiceImpl.removeAll();
		userServiceImpl.findUserById(1);
	}
}
