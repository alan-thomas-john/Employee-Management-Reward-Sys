package com.errs.management.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("scrtspring@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		// checking if list contains data or not
		if (list != null && list.size() > 0)
			// convert list to array
			message.setCc(getCcArray(list));
		emailSender.send(message);

	}

	private String[] getCcArray(List<String> ccList) {
		String[] cc = new String[ccList.size()];
		for (int i = 0; i < ccList.size(); i++) {
			cc[i] = ccList.get(i);
		}
		return cc;
	}
}
