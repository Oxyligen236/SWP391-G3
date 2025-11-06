package hrms.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "pqm1290@gmail.com";
    private static final String EMAIL_PASSWORD = "ewktsapqnogdnfqi";

    /**
     * Gửi email forgot password với Reply-To là email của user Admin có thể trả
     * lời trực tiếp cho user
     */
    public boolean sendForgotPasswordEmail(String toEmail, String userEmail, String userName, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(EMAIL_USERNAME, "HRMS System"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error setting sender address: " + e.getMessage());
            }

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setReplyTo(InternetAddress.parse(userEmail));

            try {
                subject = MimeUtility.encodeText(subject, "UTF-8", "B");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error encoding email subject: " + e.getMessage());
            }
            message.setSubject(subject);

            String htmlContent = String.format(
                    "<p>Dear Admin,</p>"
                    + "<p>User <strong>%s</strong> with email <strong>%s</strong> has requested a password reset.</p>"
                    + "<p>Message from user:</p>"
                    + "<blockquote>%s</blockquote>",
                    userName,
                    userEmail,
                    body
            );

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        }
    }
}
