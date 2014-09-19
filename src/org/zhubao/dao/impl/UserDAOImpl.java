/**
 * 
 */
package org.zhubao.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.zhubao.model.User;

/**
 * @author Jason.Zhu
 * @date 2013-11-13
 * @email jasonzhu@augmentum.com.cn
 */
public class UserDAOImpl {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public Page<Object[]> findByCondition(final User user) {
		
		Specification<User> spec = new Specification<User>() {  
		    public Predicate toPredicate(Root<User> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {  
		        Predicate p1 = cb.like(root.get("username").as(String.class), "%"+user.getUsername()+"%");  
		        Predicate p2 = cb.equal(root.get("userId").as(Long.class), user.getUserId());  
		        query.where(cb.and(p1,p2));  
		        query.orderBy(cb.desc(root.get("userId").as(Long.class)));  
		        return query.getRestriction();  
		    }  
		};
		String hql = "select u.userId,u.username from User u where u.email=:email";
		Query q = em.createQuery(hql);
		q.setParameter("email", user.getEmail());
		q.setFirstResult(0);
		q.setMaxResults(1);
		Page<Object[]> page = new PageImpl<Object[]>(q.getResultList(),
				new PageRequest(0, 1), 3);
		return page;
	}
	
	public void batchSave(List<User> users){
		for(int i= 0 ; i < users.size(); i++){
			em.persist(users.get(i));
			if(0 == i % 50){
				em.flush();
				em.clear();
			}
		}
	}
}
