package com.cha1024.bell.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cha1024.bell.BellGitlabApplication;
import com.cha1024.bell.dto.JsonResult;
import com.cha1024.bell.model.UserInfo;
import com.cha1024.bell.service.GitUserService;
import com.cha1024.bell.websocket.WebSocketServer;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Controller
@RequestMapping
public class IndexController {

	@Autowired
	private GitUserService gitUserService;
	@GetMapping
	public String index(HttpServletRequest request, Model model) {
		String cid = UUID.fastUUID().toString();
		request.setAttribute("cid", cid);
		model.addAttribute("cid", cid);
		BellGitlabApplication.getFifoCache().put(cid, cid);
		return "index/index";
	}
	
	@PostMapping("dui")
	@ResponseBody
	public String dui(@RequestBody String body) {
		try {
			Iterator<CacheObj<Object, Object>> iterator = BellGitlabApplication.getFifoCache().cacheObjIterator();
			while (iterator.hasNext()) {
				CacheObj<Object, Object> obj = iterator.next();
				String cid = (String) obj.getKey();
				WebSocketServer.sendInfo(body, cid);
			}
			//推送给企业微信
			pushToQyWx(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	String[] actions = {"帅气地", "风骚地", "麻利地", "瑟瑟发抖地", "偷偷地", "开心地", "粗暴地", "美美地", "精疲力尽地", "狂暴地"};
	private String randonAction() {
		Double sc = RandomUtil.randomDouble(0, 9);
		return actions[sc.intValue()];
	}
	/**
	 * 发送消息给企业微信
	 * @param body
	 */
	private void pushToQyWx(String body) {
		System.out.println("推送消息给企业微信机器人");
		String qywxUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=ec352707-1fd4-48a6-91ae-eb474a934508";
		JSON jsonObject = JSONUtil.parse(body);
		String username = (String) jsonObject.getByPath("user_name");
		String description = (String) jsonObject.getByPath("description");
		String avatar = (String) jsonObject.getByPath("user_avatar");
		String homepage = (String) jsonObject.getByPath("homepage");
		String jsonBody = "";
		JSONObject obj = new JSONObject();
		JSONObject news = new JSONObject();
		JSONArray articles = new JSONArray();
		JSONObject article = new JSONObject();
		article.put("title", username + "提交了代码");
		article.put("description", StrUtil.emptyToDefault(description, username + "一顿操作猛如虎，"+randonAction()+"提交了他的代码!"));
		article.put("url", StrUtil.emptyToDefault(homepage, "http://bell.test.banmazgai.com"));
		article.put("picurl", StrUtil.emptyToDefault(avatar, "http://demo.sc.chinaz.com/Files/pic/icons/3751/rockn_roll.png"));
		articles.add(article);
		news.put("articles", articles);
		obj.put("msgtype", "news");
		obj.put("news", news);
		jsonBody = obj.toString();
		HttpUtil.post(qywxUrl, jsonBody);
	}
	/**
	 * 个人profile页面
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("profile")
	public String profile(HttpServletRequest request, Model model) {
		String userId = (String) request.getSession().getAttribute("userId");
		if(userId == null || userId.length() < 10) {
			String state = "bell-github";
			String backUrl = "http://bell.test.banmazgai.com/auth/gitlab/redirect";
			String clientId = "de6ce917e46c62aad562f6592a02e4785927d6730264ab160d8b80cf82856ab2";
			String linkUrl = "http://git.dev.banmazgai.com/oauth/authorize?client_id="+clientId+"&redirect_uri="+backUrl+"&response_type=code&state="+state+"&scope=read_user";
			return "redirect:" + linkUrl;
		}else {
			UserInfo userInfo = gitUserService.getUserInfoById(userId);
			model.addAttribute("userInfo", userInfo);
		}
		return "index/profile";
	}
	/**
	 * ajax获得个人信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@GetMapping("ajaxProfile")
	public JsonResult ajaxProfile(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userId");
		if(userId == null || userId.length() < 10) {
			return JsonResult.failure("未登录");
		}else {
			UserInfo userInfo = gitUserService.getUserInfoById(userId);
			return JsonResult.success(userInfo);
		}
	}
	/**
	 * 检查登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@GetMapping("ajaxCheckLogin")
	public JsonResult ajaxCheckLogin(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userId");
		if(userId == null || userId.length() < 10) {
			return JsonResult.failure("未登录");
		} else {
			return JsonResult.success(userId);
		}
	}
}
