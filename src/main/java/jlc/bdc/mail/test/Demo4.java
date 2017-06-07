package jlc.bdc.mail.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jlc.bdc.mail.authenticator.MyAuthenticator;

public class Demo4 {

	 public static void main(String[] args) throws FileNotFoundException, MessagingException {
		// 属性对象
		Properties properties = new Properties();
		// 开启debug调试 ，打印信息
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "true");
		// 发送服务器端口，可以不设置，默认是25
		properties.setProperty("mail.smtp.port", "25");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host", "smtp.163.com");
		// 环境信息
		Session session = Session.getInstance(properties, new MyAuthenticator( "BunyTung@163.com", "tcy123"));
		
		//读取本地邮件
		Message message = new MimeMessage(session, new FileInputStream(new File("C:/Users/synlove/Desktop/demo.eml")));
		//发送邮件
		Transport.send(message, InternetAddress.parse("tangchaoyang@315i.com") );
	}
}
