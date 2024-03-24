package com.example.email_service.service.impl;

import com.example.email_service.service.EmailService;
import com.example.email_service.utils.EmailUtils;
import com.lowagie.text.DocumentException;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.standard.expression.MessageExpression;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    public static final String EMAIL_TEMPLATE2 = "emailtemplate2";
    public static final String TEXT_HTML_ENCODING = "text/html";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleEmailMessage(String name, String to, String token) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            // Add attachments
            FileSystemResource forest = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/forest.jpg"));
            FileSystemResource sky = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/sky.jpg"));
            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
            helper.addAttachment(Objects.requireNonNull(forest.getFilename()), forest);
            helper.addAttachment(Objects.requireNonNull(sky.getFilename()), sky);
            helper.addAttachment(Objects.requireNonNull(homework.getFilename()), homework);

            emailSender.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {

        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            // Add attachments
            FileSystemResource forest = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/forest.jpg"));
            FileSystemResource sky = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/sky.jpg"));
            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
            helper.addInline(getContentId(forest.getFilename()), forest);
            helper.addAttachment(getContentId(sky.getFilename()), sky);
            helper.addAttachment(getContentId(homework.getFilename()), homework);

            emailSender.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private final String pdfD = System.getProperty("user.home") + "/Downloads/images/";
    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {

        try {
            Context context = new Context();
//            context.setVariable("name", name);
//            context.setVariable("url", EmailUtils.getVerificationUrl(host, token));
            context.setVariables(Map.of("name", name, "url", EmailUtils.getVerificationUrl(host, token)));
//            String text = templateEngine.process(EMAIL_TEMPLATE2, context);
            String text = templateEngine.process(EMAIL_TEMPLATE2, context);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(pdfD + "file");
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(text);
                renderer.layout();
                renderer.createPDF(fileOutputStream, false);
                renderer.finishPDF();
            } catch (FileNotFoundException e) {
            } catch (DocumentException e) {
            }

            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true); // true => this is a html text not just a simple string text
            emailSender.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
//            helper.setText(text, true); // true => this is a html text not just a simple string text
            Context context = new Context();
            context.setVariables(Map.of("name", name, "url", EmailUtils.getVerificationUrl(host, token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            // Add attachments

            // Add HTML email body
            MimeMultipart mimeMultipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, TEXT_HTML_ENCODING);
            mimeMultipart.addBodyPart(messageBodyPart);

            // Add images to the email body
            BodyPart imageBodyPart = new MimeBodyPart();
            DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/forest.jpg");
            imageBodyPart.setDataHandler(new DataHandler(dataSource));
            imageBodyPart.setHeader("Content-ID", "image"); // pointed in the image of template
            mimeMultipart.addBodyPart(imageBodyPart);

            message.setContent(mimeMultipart); // Added it to the email message

            emailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }
}
