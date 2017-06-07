package jlc.bdc.mail.spring;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SingleMailSend {
	
	static ApplicationContext actx = new ClassPathXmlApplicationContext("classpath:spring/SpringMailAapplicationContext.xml");
	
	static MailSender sender = (MailSender) actx.getBean("mailSender");
	
	static SimpleMailMessage mailMessage = (SimpleMailMessage) actx.getBean("mailMessage");
	
	public static void main(String args[]) throws MessagingException {		
		mailMessage.setSubject("你好");
		mailMessage.setText("这个是一个通过Spring框架来发送邮件的小程序");
		mailMessage.setTo("tangchaoyang@315i.com");
		//mailMessage.setTo(new String[]{"tangchaoyang@315i.com", "zhangyandong@315i.com"});
		//mailMessage.setCc("zhangyandong@315i.com");
		sender.send(mailMessage);
	}
}
