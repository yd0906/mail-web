package jlc.bdc.mail.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Demo3 {

	public static void main(String[] args) throws IOException, AddressException, MessagingException {
		//环境
		Session session = Session.getDefaultInstance(new Properties());
		//邮件
		MimeMessage msg = new MimeMessage(session);
		
		//设置主题
		msg.setSubject("邮件发送测试-唐朝阳测试");
		//发件人，注意中文的处理
		msg.setFrom(new InternetAddress("\"" + MimeUtility.encodeText( "tangchaoyang") + "\"<bunytung@163.com>"));
		//设置邮件回复人
		msg.setReplyTo(new Address[]{new InternetAddress("bunytung@163.com")});

		//整封邮件的MINE消息体
		MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
		//设置邮件的MINE消息体
		msg.setContent(msgMultipart);

		//附件1
		MimeBodyPart attch1 = new MimeBodyPart();
		//附件2
		MimeBodyPart attch2 = new MimeBodyPart();
		//正文内容
		MimeBodyPart content = new MimeBodyPart();

		//把内容，附件1，附件2加入到 MINE消息体中
		msgMultipart.addBodyPart(attch1);
		msgMultipart.addBodyPart(attch2);
		msgMultipart.addBodyPart(content);

		//把文件，添加到附件1中
		//数据源
		DataSource ds1 = new FileDataSource(new File("C:/Users/synlove/Desktop/邮件附件.rar"));
		//数据处理器
		DataHandler dh1 = new DataHandler(ds1 );
		//设置第一个附件的数据
		attch1.setDataHandler(dh1);
		//设置第一个附件的文件名
		attch1.setFileName(MimeUtility.encodeText( "邮件附件.rar"));

//		//把文件，添加到附件2中
//		DataSource ds2 = new FileDataSource(new File("C:/Users/H__D/Desktop/2.txt"));
//		DataHandler dh2 = new DataHandler(ds2 );
//		attch2.setDataHandler(dh2);
//		attch2.setFileName(MimeUtility.encodeText( "文件2.jpg"));

		//正文（图片和文字部分）
		MimeMultipart bodyMultipart  = new MimeMultipart("related");
		//设置内容为正文
		content.setContent(bodyMultipart);

		//html代码部分
		MimeBodyPart htmlPart = new MimeBodyPart();
		//html中嵌套的图片部分
		MimeBodyPart imgPart = new MimeBodyPart();

		//把文件，添加到图片中
//		DataSource imgds = new FileDataSource(new File("C:/Users/H__D/Desktop/logo.png"));
//		DataHandler imgdh = new DataHandler(imgds );
//		imgPart.setDataHandler(imgdh);
//		//说明html中的img标签的src，引用的是此图片
//		imgPart.setHeader("Content-Location", "http://sunteam.cc/logo.jsg");

		//html代码
		htmlPart.setContent("<span style='color:red'>中文呵呵</span><img src=\"http://sunteam.cc/logo.jsg\">","text/html;charset=utf-8");
		//正文添加图片和html代码
		bodyMultipart.addBodyPart(htmlPart);
		bodyMultipart.addBodyPart(imgPart);
		//生成文件邮件
		msg.saveChanges();

		//输出
		OutputStream os = new FileOutputStream("C:/Users/synlove/Desktop/demo.eml");
		msg.writeTo(os);
		os.close();
	}
}
