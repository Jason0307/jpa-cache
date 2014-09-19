/**
 * 
 */
package org.zhubao.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.zhubao.model.User;
import org.zhubao.model.User.Gender;

/**
 * @author Jason.Zhu
 * @date   2013-11-12
 * @email jasonzhu@augmentum.com.cn
 */
public interface UserDAO extends PagingAndSortingRepository<User, Long>{

	/**
	 * find user by pager
	 * @param pageable
	 * @return
	 */
	public Page<User> findAll(Pageable pageable);
	
	/**
	 * find user by its name
	 * @param name
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * find user by email
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);
	
	/**
	 * find users by gender
	 * @param gender
	 * @return
	 */
	public Page<User> findByGender(Gender gender,Pageable pageable);
	
	/**
	 * find  object by condition
	 * @param u
	 * @return
	 */
	public Page<Object[]> findByCondition(User user);
	
	/**
	 * find user by query sql
	 * @param username
	 * @return
	 */
	@Query("SELECT u FROM User u where u.username=:username")
	public User findByQuerySql(@Param("username") String username);
	
	/**
	 * batch save users
	 * @param users
	 */
	public void batchSave(List<User> users);
	
}
