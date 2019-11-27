package com.qhc.steigenberger.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.qhc.steigenberger.domain.Mail;

@Component
public class MailService {
	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	@Value("${mail.debug}")
	private String debug;

	@Value("${mail.host}")
	private String host;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.smtp.from}")
	private String from;
	@Value("${mail.smtp.auth}")
	private String auth;
	@Value("${mail.smtp.timeout}")
	private String timout;
	@Value("${mail.smtp.port}")
	private String port;

	@Resource
	private UserService userService;
	@Resource
	private MailService mailService;

//	public String createBody(String templateName, Map<String, Object> variables) {
//		return render(templateName, "HTML5", variables);
//	}

//	public Map<String, Object> createDefaultMap(boolean status, BaseEntity baseEntity, ProcessInfo processInfo) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("entity", baseEntity);
//		if (baseEntity != null) {
//			long id = baseEntity.getCreateUserId().longValue();
//			if (id > 0L) {
//				map.put("createUser", this.userService.findUserById(Long.valueOf(id)));
//			}
//			id = baseEntity.getApplicantUserId().longValue();
//			if (id > 0L) {
//				map.put("applicantUser", this.userService.findUserById(Long.valueOf(id)));
//			}
//		}
//		map.put("R", new Render());
//		map.put("processInfo", processInfo);
//		map.put("status", Boolean.valueOf(status));
//		map.put("approval", baseEntity.getApproval());
//		if (processInfo != null)
//			map.put("approvalList", this.activitiService.getApprovalList(processInfo.getId()));
//		return map;
//	}

//	public void sendMail(boolean status, String action, String formType, String requestCodeDesc, String customerName,
//			BaseEntity baseEntity, ProcessInfo processInfo, User currentUser) {
//		try {
//			List<IdentityLink> identityLinks = new ArrayList<>();
//			try {
//				List<TaskInfo> taskInfos = this.activitiService.getCurrentTaskByProcessInstanceId(processInfo.getId(),
//						currentUser);
//				for (TaskInfo info : taskInfos) {
//					identityLinks.addAll(this.activitiService.findIdentityLinks(info.getId(), currentUser));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			List<User> users = this.userService.findUserByIdentityLinks(identityLinks);
//			Map<String, Object> variables = this.mailService.createDefaultMap(status, baseEntity, processInfo);
//
//			variables.put("requestCodeDesc", requestCodeDesc);
//			variables.put("customerName", customerName);
//			variables.put("emailType", action);
//			setMailType(formType, variables);
//			variables.put("createUser", this.userService.findUserById(baseEntity.getApplicantUserId()));
//			String body = this.mailService.createBody("qhc-" + action, variables);
//			String subject = this.mailService.createBody("qhc-subject", variables);
//			Mail mail = new Mail();
//
//			StringBuffer emails = new StringBuffer();
//			for (User user : users) {
////				if (user != null && user.getStatus() != 1) {
//					emails.append(user.getUserMail()).append(",");
////				}
//			}
//
//			if (emails.length() == 0) {
//				log.warn("Do not send any mail, no valid email address.");
//				return;
//			}
//			mail.setTo(emails.substring(0, emails.length() - 1));
//			mail.setSubject(subject);
//			mail.setBody(body);
//			mail.setFrom(this.fromAddress);
//			mail.setId(UUID.randomUUID().toString());
//			this.mailService.send(mail);
//			log.info((new StringBuilder()).append(variables.get("formaType")).append(" send success,mail body: ")
//					.append(body).toString());
//		} catch (Exception e) {
//			log.info(e.toString());
//			log.error("mail send failed,formType:" + formType + ";action=" + action + ";user=" + currentUser
//					+ ";baseEntity ID = " + baseEntity.getId());
//		}
//	}

//	public void sendMail(String action, String formType, String requestCodeDesc, String customerName,
//			Object baseEntity, List<User> users, User createUser) {
//		try {
//			Map<String, Object> variables = new HashMap<>();
//			variables.put("entity", baseEntity);
//			variables.put("requestCodeDesc", requestCodeDesc);
//			variables.put("customerName", customerName);
//
//			variables.put("emailType", action);
//			setMailType(formType, variables);
//			variables.put("createUser", createUser);
//			String body = this.mailService.createBody("qhc-" + action, variables);
//			String subject = this.mailService.createBody("qhc-subject", variables);
//			Mail mail = new Mail();
//
//			StringBuffer emails = new StringBuffer();
//			for (User user : users) {
////				if (user != null && user.getStatus() != 1) {
//					emails.append(user.getUserMail()).append(",");
////				}
//			}
//
//			if (emails.length() == 0) {
//				log.warn("Do not send any mail, no valid email address.");
//				return;
//			}
//			mail.setTo(emails.substring(0, emails.length() - 1));
//			mail.setSubject(subject);
//			mail.setBody(body);
//			mail.setFrom(this.fromAddress);
//			mail.setBcc(mail.getFrom());
//			mail.setId(UUID.randomUUID().toString());
//			send(mail);
//			log.info((new StringBuilder()).append(variables.get("formaType")).append(" mail body: ").append(body)
//					.toString());
//		} catch (Exception e) {
//			log.error("mail send failed,formType:" + formType + ";action=" + action + ";user=" + createUser
//					+ ";baseEntity=" + baseEntity);
//		}
//	}

//	public Map<String, Object> setMailType(String formType, Map<String, Object> variables) {
//		if ("ASO".equals(formType)) {
//			variables.put("formType", "经销商标准折扣下定单");
//		} else if ("ANSB".equals(formType)) {
//			variables.put("formType", "经销商特价申请订单");
//		} else if ("ANSO".equals(formType)) {
//			variables.put("formType", "经销商非标准折扣下定单");
//		} else if ("NYB".equals(formType)) {
//			variables.put("formType", "直签客户投标报价");
//		} else if ("YO".equals(formType)) {
//			variables.put("formType", "直签客户下定单");
//		} else if ("NYO".equals(formType)) {
//			variables.put("formType", "直签客户下定单");
//		} else if ("OOS".equals(formType)) {
//			variables.put("formType", "缺件发货申请");
//		} else if ("EMS".equals(formType)) {
//			variables.put("formType", "紧急发货申请");
//		} else if ("NMS".equals(formType)) {
//			variables.put("formType", "正常发货申请");
//		} else if ("DMC".equals(formType)) {
//			variables.put("formType", "定单更改");
//		}
//		return variables;
//	}

	// Mail Server
	public boolean send(Mail mail) {
		boolean result = send(mail.getTo(), mail.getCc(), mail.getBcc(), mail.getFrom(),
				mail.getSubject(), mail.getBody(), mail.getHead(), mail.getAttachments());
		return result;
	}

	public boolean send(String to, String cc, String bcc, String from, String subject, String body, String head, Map<String, File> attachments) {
		if (head == null) {
			head = "text/html;charset=UTF-8";
		}
		boolean sendFlag = false;
		Properties properties = new Properties();
		properties.put("mail.debug", debug);
		properties.put("mail.host", host);
		properties.put("mail.username", username);
		properties.put("mail.password", password);
		properties.put("mail.smtp.from", from == null ? this.from : from);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.timeout", timout);
		properties.put("mail.smtp.port", port);

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		if (this.debug.equals("true")) {
			session.setDebug(true);
		}
		Address[] toAddr = convert2InternetAddress(to);
		Address[] ccAddr = convert2InternetAddress(cc);
		Address[] bccAddr = convert2InternetAddress(bcc);
		MimeMessage msg = new MimeMessage(session);

		try {
			if (from == null || from.trim().length() == 0) {
				from = this.from;
			}
			msg.setFrom((Address) new InternetAddress(from));
			if (toAddr != null) {
				msg.setRecipients(Message.RecipientType.TO, toAddr);
			}
			if (bccAddr != null) {
				msg.setRecipients(Message.RecipientType.BCC, bccAddr);
			}
			if (ccAddr != null) {
				msg.setRecipients(Message.RecipientType.CC, ccAddr);
			}
			msg.setSubject(cleanHTMLTag(subject), "UTF-8");
			msg.setSentDate(new Date());
			Multipart multipart = createMultipart(body, head, attachments);
			if (multipart == null) {
				msg.setContent(body, head);
			} else {
				msg.setContent(multipart);
			}
			send(to, session, msg);
			sendFlag = true;
		} catch (Throwable e) {
			logger.error("send email failed", (Throwable) e);
		}
		return sendFlag;
	}

	private void send(String to, Session session, MimeMessage msg) throws MessagingException {
		Transport.send((Message) msg);
	}

	private Multipart createMultipart(String body, String head, Map<String, File> attachments)
			throws MessagingException {
		if (attachments != null && attachments.size() > 0) {
			MimeMultipart mimeMultipart = new MimeMultipart();
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(body, head);
			mimeMultipart.addBodyPart((BodyPart) textBodyPart);
			for (Map.Entry<String, File> entry : attachments.entrySet()) {
				String name = entry.getKey();
				File f = entry.getValue();
				if (f.exists()) {
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					String file = f.getAbsolutePath();
					DataSource source = new FileDataSource(file);
					messageBodyPart.setDataHandler(new DataHandler(source));
					try {
						messageBodyPart.setFileName(MimeUtility.encodeText(name));
					} catch (UnsupportedEncodingException e) {
						messageBodyPart.setFileName(name);
					}
					mimeMultipart.addBodyPart((BodyPart) messageBodyPart);
					continue;
				}
				logger.error("attachment file not found:" + f.getAbsolutePath());
			}

			return (Multipart) mimeMultipart;
		}
		return null;
	}

	private String cleanHTMLTag(String subject) {
		String noHTMLString = subject.replaceAll("\\<[^<>]*?>", "");
		return noHTMLString;
	}

	private Address[] convert2InternetAddress(String address) {
		Object[] arrayOfObject = null;
		if (address != null) {
			String[] addresses = address.split("[\\,;\\s]+");
			try {
				arrayOfObject = (Object[]) new InternetAddress[addresses.length];
				for (int i = 0; i < addresses.length; i++) {
					arrayOfObject[i] = new InternetAddress(addresses[i]);
				}
			} catch (AddressException e) {
				logger.error("convert2InternetAddress failed", (Throwable) e);
			}
		}

		return (Address[]) arrayOfObject;
	}
	
	/**
	 * 利用thremyleaf渲染邮件内容模板
	 * <p>
	 * ${contractCode}
	 * 
	 * @param templateName
	 * @param mode HTML5
	 * @param variables
	 * @return
	 */
	public static String render(String templateName, String mode, Map<String, Object> variables) {
//		TemplateEngine templateEngine = new TemplateEngine();
//		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//		templateResolver.setTemplateMode(mode);
////		templateResolver.setPrefix("templates/");
//		templateResolver.setPrefix("");
//		templateResolver.setSuffix(".html");
//		templateResolver.setCacheable(false);
//		templateResolver.setCharacterEncoding("UTF-8");
//		templateEngine.setTemplateResolver((ITemplateResolver) templateResolver);
//		Context context = new Context();
//		context.setVariables(variables);
//		StringWriter stringWriter = new StringWriter();
//		templateEngine.process(templateName, (IContext) context, stringWriter);
//		return stringWriter.toString();
		
		try (InputStream in = "".getClass().getResourceAsStream(templateName)) {
			byte[] data = new byte[5192];
			int size = 0;
			StringBuilder content = new StringBuilder(1024);
			while ((size = in.read(data)) > 0) {
				content.append(new String(data, "UTF-8"));
			}
			String h = content.toString();
			for (Map.Entry<String, Object> e : variables.entrySet()) {
				String k = "\\$\\{" + e.getKey() + "\\}";
				String v = e.getValue() == null ? "" : e.getValue().toString();
				h = h.replaceAll(k, v);
//				while (h.indexOf(k) >= 0) {
//					h = h.re
//				}
			}
			
			logger.debug(h);
			
			return h;
		} catch (Exception e) {
			logger.error("render", e);
		} finally {
			
		}
		
		return "";
	}
}
