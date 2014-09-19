/**
 * 
 */
package org.zhubao.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhubao.dao.UserDAO;
import org.zhubao.model.User;
import org.zhubao.model.User.Gender;
import org.zhubao.service.UserService;
import org.zhubao.util.SimplePageableImpl;

/**
 * @author Jason.Zhu
 * @date 2013-11-12
 * @email jasonzhu@augmentum.com.cn
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#saveUser(org.zhubao.model.User)
	 */
	@PostConstruct
	public void init() {
		System.out.println("POST Constructor!");

	}

	@Override
	public void saveUser(User user) {
		assertUserNameExist(user.getUsername());
		assertUserEmailExist(user.getEmail());
		userDAO.save(user);

	}

	/**
	 * @param email
	 */
	private void assertUserEmailExist(String email) {
		User user = userDAO.findByEmail(email);
		if (null != user) {
			throw new RuntimeException();
		}

	}

	/**
	 * @param username
	 */
	private void assertUserNameExist(String username) {
		User user = userDAO.findByUsername(username);
		if (null != user) {
			throw new RuntimeException();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#updateUser(org.zhubao.model.User)
	 */
	@Override
	public void updateUser(User user) {
		assertUserNameExist(user.getUsername());
		assertUserEmailExist(user.getEmail());
		userDAO.save(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#deleteUser(long)
	 */
	@Override
	public void deleteUser(long userId) {
		userDAO.delete(userId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#findUserById(long)
	 */
	@Override
	@Cacheable(value = "serviceCache", key="#userId") 
	public User findUserById(long userId) {
		User user = userDAO.findOne(userId);
		if (null == user) {
			throw new RuntimeException("User not exist!");
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#findUserByPage(int, int)
	 */
	@Override
	public Page<User> findUserByPage(int pageNumber, int pagesize) {
		Page<User> page = userDAO.findAll(SimplePageableImpl.getInstance(
				pageNumber, pagesize));
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#findByName(java.lang.String)
	 */
	@Override
	public User findByName(String username) {
		// User user = userDAO.findByUsername(username);
		User user = userDAO.findByQuerySql(username);
		if (null == user) {
			throw new RuntimeException("User not exist!");
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#findByUserEmail(java.lang.String)
	 */
	@Override
	public User findByUserEmail(String email) {
		User user = userDAO.findByEmail(email);
		if (null == user) {
			throw new RuntimeException("User not exist!");
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zhubao.service.UserService#findByGender(org.zhubao.model.User.Gender,
	 * int, int)
	 */
	@Override
	public Page<User> findByGender(Gender gender, int pageNumber, int pageSize) {
		Page<User> page = userDAO.findByGender(gender,
				SimplePageableImpl.getInstance(pageNumber, pageSize));
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zhubao.service.UserService#findByCondition(org.zhubao.model.User)
	 */
	@Override
	public Page<Object[]> findByCondition(User user) {
		Page<Object[]> page = userDAO.findByCondition(user);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zhubao.service.UserService#findAll(org.springframework.data.domain
	 * .Pageable)
	 */
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userDAO.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#removeAll()
	 */
	@Override
	@CacheEvict(value="serviceCache",allEntries=true)  
	public void removeAll() {
		userDAO.deleteAll();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#batchSaveUsers(java.util.List)
	 */
	@Override
	public void batchSaveUsers(List<User> users) {
		userDAO.batchSave(users);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhubao.service.UserService#findAll()
	 */
	@Override
	@Cacheable(value = "serviceCache")
	public Iterable<User> findAll() {
		System.out.println("非缓存查询----------findById");
		return userDAO.findAll();
	}

}
