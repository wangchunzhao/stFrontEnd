package com.qhc.steigenberger.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.qhc.steigenberger.domain.Mail;

@SpringBootTest
class MailServiceTest {
	
	@Autowired
	private MailService mailService;

	@Test
	void testSendMail() {
		Mail mail = new Mail();
		mail.setSubject("测试邮件主题");
		mail.setTo("zuwei.su@qq.com");
		mail.setCc("zuwei.su@dxc.com");
		mail.setBcc("zuwei.su@dxc.com");
		mail.setBody("测试邮件内容，顺利打开房间数量的客服就爱上了对方");
		boolean result = mailService.send(mail);
		Assert.isTrue(result, "邮件发送失败！");
	}

}
