package com.cha1024.bell.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cha1024.bell.model.UserInfo;
import com.cha1024.bell.service.GitUserService;

@Controller
@RequestMapping("/auth/gitlab")
public class AuthGitlabController {

	@Autowired
	private GitUserService gitUserService;
	
	/**
	 * 承载gitlab的重定向
	 * @param code
	 * @param state
	 * @param model
	 * @return
	 */
	@GetMapping("redirect")
	public String redirect(@RequestParam("code")String code, @RequestParam("state")String state, HttpServletRequest request, Model model) {
		UserInfo userInfo = gitUserService.getUserJsonByCodeAndState(code, state);
		if(userInfo == null) {
			System.out.println("授权失败，转向默认首页");
			return "redirect:/";
		}else {
			request.getSession().setAttribute("userId", userInfo.getId());
			model.addAttribute("userInfo", userInfo);
			return "index/profile";
		}
	}
}
