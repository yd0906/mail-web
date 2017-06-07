package jlc.bdc.controller;

import java.util.HashMap;
import java.util.Map;

import jlc.bdc.quartz.QuartzManager;
import jlc.bdc.quartz.job.HelloWordJob;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/back")
public class BackMailController {

	@ResponseBody
	@RequestMapping("/check/{mid}")
	public void excute(@PathVariable String mid ){
		System.out.println("邮件已被查看:"+mid);
		return ;
	}
	
	@RequestMapping("/add/{name}")
	public void add(@PathVariable String name){
		String jobName = String.format("sayHello%s", name);
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", name);
		QuartzManager.addJob(jobName, HelloWordJob.class, "*/5 * * * * ?", param);
	}
}
