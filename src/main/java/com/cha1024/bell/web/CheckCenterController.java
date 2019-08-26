package com.cha1024.bell.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cha1024.bell.dto.JsonResult;
import com.cha1024.bell.websocket.WebSocketServer;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {

	// 页面请求
	@GetMapping("/socket/{cid}")
	public String socket(@PathVariable("cid") String cid, Model model) {
		model.addAttribute("cid", cid);
		return "index/index";
	}

	// 推送数据接口
	@ResponseBody
	@RequestMapping("/socket/push/{cid}")
	public JsonResult pushToWeb(@PathVariable String cid, String message) {
		try {
			WebSocketServer.sendInfo(message, cid);
		} catch (IOException e) {
			e.printStackTrace();
			return JsonResult.error(cid + "#" + e.getMessage());
		}
		return JsonResult.success(cid);
	}
}
