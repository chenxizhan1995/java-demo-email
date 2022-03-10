package cc.xizhan.demo.demoemail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * <p> Description: Java 发送邮件示例</p>
 */
@Component
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public String hello() {
        return "hello, world\n";
    }

    /**
     * 发送简单文本邮件
     *
     * @param subject
     * @param to
     * @param content
     */
    public void sendSimpleMail(String subject, String to, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom(mailFrom);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(content);

        try {
            mailSender.send(simpleMailMessage);
            logger.info("邮件发送成功");
        } catch (Exception e) {
            logger.warn("邮件发送失败", e);
        }
    }

    /**
     * 发送 HTML 格式的邮件（无内嵌资源）
     */
    public void sendHtmlMail(String subject, String to, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setSubject("今日名句");
        messageHelper.setFrom(mailFrom);
        messageHelper.setTo(to);
        messageHelper.setText(content, true);
        try {
            mailSender.send(message);
            logger.info("邮件发送成功");
        } catch (Exception e) {
            logger.warn("邮件发送失败", e);
        }

    }

    /**
     * 发送带附件的纯文本邮件
     */
    public void sendTextMailWithAttachment(String subject, String to, String content,
                                           String attachName, InputStreamSource file)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setText(content);


        helper.addAttachment(attachName, file);
        // 添加多个附件可以使用多条 helper.addAttachment(fileName, file)
        try {
            mailSender.send(message);
            logger.info("邮件发送成功");
        } catch (Exception e) {
            logger.warn("邮件发送失败", e);
        }

    }

    /**
     * 发送带有静态资源的HTML邮件
     */
    public void sendHtmlMailWithInResource(String subject, String to, String content,
                                           String rcsId, org.springframework.core.io.Resource rcs)
            throws MessagingException {
       MimeMessage message = mailSender.createMimeMessage();

       MimeMessageHelper helper = new MimeMessageHelper(message, true);

       helper.setSubject(subject);
       helper.setFrom(mailFrom);
       helper.setTo(to);
       helper.setText(content, true);

       helper.addInline(rcsId, rcs);

       mailSender.send(message);
        // 添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现
    }
}
