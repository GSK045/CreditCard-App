package com.sm.card_app.util;

import java.time.LocalDate;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sm.card_app.bean.CardRequestBean;
import com.sm.card_app.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailService {

	private final JavaMailSender javaMailSender;

	public String cardRequestMail(User user) throws MessagingException {
		String to = user.getEmail();
		String subject = "Credit Card Request ";
		String htmlContent = "<html>" + "<body style='font-family: Arial, sans-serif;'>"
				+ "<h2 style='color: #4CAF50;'>Your Credit Card Request is Pending</h2>" + "<p>Dear "
				+ user.getUsername() + ",</p>"
				+ "<p>Thank you for submitting your request for a credit card. We are currently reviewing your application, and it is in the initial stage of processing.</p>"
				+ "<p>Here are the details of your request:</p>"
				+ "<table style='border-collapse: collapse; width: 100%;'>" + "<tr>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Request Date</th>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Status</th>" + "</tr>"
				+ "<tr>" + "<td style='border: 1px solid #dddddd; padding: 8px;'>" + LocalDate.now() + "</td>"
				+ "<td style='border: 1px solid #dddddd; padding: 8px;'>Pending</td>" + "</tr>" + "</table>"
				+ "<p>You can check status of your credit card by login into website, with below login credentials.</p>"
				+ "<h3>Login Information</h3>" + "<p><strong>Username (Email): </strong>" + user.getEmail() + "</p>"
				+ "<p><strong>Password: </strong>Your password is your registered mobile number.</p>" + "<br>"
				+ "<p>Please note that we will notify you once the review process is complete.</p>"
				+ "<p>If you have any questions or need further assistance, feel free to contact our support team.</p>"
				+ "<br>" + "<p>Best regards,</p>" + "<p><strong>The Credit Card Service Team</strong></p>" + "</body>"
				+ "</html>";
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true); // true indicates HTML content
			javaMailSender.send(message);
			return "Mail sent Successfully ";
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Error occurred While sending request mail to user  emial : {} ", user.getEmail());
			throw e;
		}
	}

	public String approvedCardMail(CardRequestBean cardRequest) throws MessagingException {
		String to = cardRequest.getEmail();
		String subject = "Credit Card Request Status Update ";
		String htmlContent = "<html>" + "<body style='font-family: Arial, sans-serif;'>"
				+ "<h2 style='color: #4CAF50;'>Your Credit Card Request has been Approved</h2>" + "<p>Dear "
				+ cardRequest.getUserName() + ",</p>"
				+ "<p>We are pleased to inform you that your credit card request has been approved!</p>"
				+ "<p>Here are the details of your approved request:</p>"
				+ "<table style='border-collapse: collapse; width: 100%;'>" + "<tr>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Approval Date</th>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Status</th>" + "</tr>"
				+ "<tr>" + "<td style='border: 1px solid #dddddd; padding: 8px;'>" + LocalDate.now() + "</td>"
				+ "<td style='border: 1px solid #dddddd; padding: 8px;color: #069b06;'>Approved</td>" + "</tr>"
				+ "</table>"
				+ "<p>Your credit card will be issued soon, and you will receive it within the next few days. Please keep an eye on your mail for further instructions.</p>"
				+ "<p>If you have any questions or need further assistance, feel free to contact our support team.</p>"
				+ "<br>" + "<h3>Login Information</h3>" + "<p><strong>Username (Email): </strong>"
				+ cardRequest.getEmail() + "</p>"
				+ "<p><strong>Password: </strong>Your password is your registered mobile number.</p>" + "<br>"
				+ "<p>Best regards,</p>" + "<p><strong>The Credit Card Service Team</strong></p>" + "</body>"
				+ "</html>";

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true); // true indicates HTML content
			javaMailSender.send(message);
			return "Mail sent Successfully ";
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Error occurred While sending request mail to user  email : {} ", cardRequest.getEmail());
			throw e;
		}
	}

	public String rejectedCardMail(CardRequestBean cardRequest) throws MessagingException {
		String to = cardRequest.getEmail();
		String subject = "Credit Card Request Status Update ";
		String htmlContent = "<html>" + "<body style='font-family: Arial, sans-serif;'>"
				+ "<h2 style='color: #F44336;'>Your Credit Card Request has been Rejected</h2>" + "<p>Dear "
				+ cardRequest.getUserName() + ",</p>"
				+ "<p>We regret to inform you that your credit card request has been rejected after careful consideration.</p>"
				+ "<p>Here are the details of your request:</p>"
				+ "<table style='border-collapse: collapse; width: 100%;'>" + "<tr>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Request Date</th>"
				+ "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Status</th>" + "</tr>"
				+ "<tr>" + "<td style='border: 1px solid #dddddd; padding: 8px;'>" + LocalDate.now() + "</td>"
				+ "<td style='border: 1px solid #dddddd; padding: 8px; color: #e91b1b;'>Rejected</td>" + "</tr>"
				+ "</table>" + "<p>Reason for rejection: <strong  style='color: #e91b1b;'>" + cardRequest.getReason()
				+ "</strong></p>" + // Adding rejection reason from user object
				"<p>Unfortunately, your application did not meet our approval criteria. Please note that this decision is final, and we cannot process further requests at this time.</p>"
				+ "<p>If you have any questions or need clarification on the rejection, feel free to contact our support team for assistance.</p>"
				+ "<br>" + "<h3>Login Information</h3>" + "<p><strong>Username (Email): </strong>"
				+ cardRequest.getEmail() + "</p>"
				+ "<p><strong>Password: </strong>Your password is your registered mobile number.</p>" + "<br>"
				+ "<p>Best regards,</p>" + "<p><strong>The Credit Card Service Team</strong></p>" + "</body>"
				+ "</html>";

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true); // true indicates HTML content
			javaMailSender.send(message);
			return "Mail sent Successfully ";
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Error occurred While sending request mail to user  email : {} ", cardRequest.getEmail());
			throw e;
		}
	}

}
