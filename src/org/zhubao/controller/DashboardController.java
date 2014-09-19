/**
 * 
 */
package org.zhubao.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zhubao.model.User;
import org.zhubao.service.UserService;
import org.zhubao.vo.TestVo;

/**
 * @author Jason.Zhu
 * @date   2013-11-13
 * @email jasonzhu@augmentum.com.cn
 */
@Controller
public class DashboardController {

	@Autowired
	private UserService userServiceImpl;
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public @ResponseBody Page<User> getUsers(int page, int size){
		return userServiceImpl.findUserByPage(page, size);
	}
	
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public String testPageable(Pageable pageable){
		PageRequest p = (PageRequest) pageable;
		System.out.println("Pagesize : "+ p.getPageSize());
		System.out.println("PageNum : "+ p.getPageNumber());
		System.out.println("PageSort : "+ p.getSort());
		return "test";
	}
	
	@RequestMapping(value = "/testPager",method = RequestMethod.GET)
	public String test(){
		System.out.println("123");
		return "test";
	}
	
	@RequestMapping(value = "/json",method = RequestMethod.POST)
	public String testJson(@RequestBody TestVo vo){
		System.out.println(vo);
		return "test";
	}
	
	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public void google(HttpServletResponse response) throws IOException{
		response.sendRedirect("https://accounts.google.com/o/oauth2/auth?scope=email%20profile&state=%2Fprofile&redirect_uri=http://127.0.0.1/oauth2callback&response_type=code&client_id=1022083506562-ls7nns6gc1rkttp0fuehgvip4rjh4v19.apps.googleusercontent.com&access_type=offline&approval_prompt=force");
	}
}
