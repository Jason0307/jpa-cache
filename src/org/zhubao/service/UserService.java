/**
 * 
 */
package org.zhubao.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zhubao.model.User;
import org.zhubao.model.User.Gender;

/**
 * @author Jason.Zhu
 * @date   2013-11-12
 * @email jasonzhu@augmentum.com.cn
 */
public interface UserService {

	/**
	 * save user
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * update user
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * delete a user by its id
	 * @param userId
	 */
	public void deleteUser(long userId);
	
	/**
	 * find a user by its id
	 * @param userId
	 * @return
	 */
	public User findUserById(long userId);
	
	/**
	 * find user by pager
	 * @param pageNumber
	 * @param pagesize
	 * @return
	 */
	public Page<User> findUserByPage(int pageNumber,int pagesize);
	
	/**
	 * find use by its username
	 * @param username
	 * @return
	 */
	public User findByName(String username);
	
	/**
	 * find user by email
	 * @param email
	 * @return
	 */
	public User findByUserEmail(String email);

	/**
	 * find users by gender
	 * @param gender
	 * @param pageable
	 * @return
	 */
	public Page<User> findByGender(Gender gender,int pageNumber,int pageSize);
	
	/**
	 * find object by condition
	 * @param user
	 * @return
	 */
	public Page<Object[]> findByCondition(User user);
	
	/**
	 * find users by page
	 * @param pageable
	 * @return
	 */
	public Page<User> findAll(Pageable pageable);

	/**
	 * remove all the users
	 * 
	 */
	public void removeAll();
	
	/**
	 * save batch users
	 * @param users
	 */
	public void batchSaveUsers(List<User> users);
	
	/**
	 * 
	 * @return
	 */
	public Iterable<User> findAll();
}
