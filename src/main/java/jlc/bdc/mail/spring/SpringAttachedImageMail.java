package jlc.bdc.mail.spring;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SpringAttachedImageMail {

	public static void main(String[] args) throws MessagingException {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-mvc.xml");
		JavaMailSenderImpl sender = (JavaMailSenderImpl) ctx.getBean("mailSender");
		MimeMessage mailMessage = sender.createMimeMessage();
		// 设置需要回执
		//mailMessage.setHeader("Disposition-Notification-To","1");
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
		messageHelper.setFrom("tangchaoyang01@315i.com");
		messageHelper.setTo("tangchaoyang@315i.com");
		messageHelper.setCc(new String[]{"dongminghui@315i.com", "zhangyandong@315i.com", "zhangyifei@315i.com"});
		messageHelper.setSubject("唐朝阳测试邮件中嵌套图片!！");
		
		// true 表示启动HTML格式的邮件
		// 发送图片
		messageHelper.setText("<html><head></head><body>测试邮件/n<img src='cid:image' /><img src='http://192.168.13.86:8080/back/201706011001' style=\"display:none;\"/></body></html>", true);
		FileSystemResource img = new FileSystemResource(new File("src/main/webapp/res/temp/snake.jpg"));
		messageHelper.addInline("image", img);//跟cid一致
		// 发送附件
		FileSystemResource file = new FileSystemResource(new File("src/main/webapp/res/temp/2017年研发中心培训计划.xlsx"));
		messageHelper.addAttachment("2017年研发中心培训计划.xlsx", file);
		FileSystemResource file2 = new FileSystemResource(new File("src/main/webapp/res/temp/期货交易规则及交易时间.xlsx"));
		messageHelper.addAttachment("期货交易规则及交易时间.xlsx", file2);
		sender.send(mailMessage);
		System.out.println("邮件发送成功...");
	}
}
