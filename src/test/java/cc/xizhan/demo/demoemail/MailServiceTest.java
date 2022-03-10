package cc.xizhan.demo.demoemail;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class MailServiceTest {
    @Resource
    MailService mailService;

    @Test
    public void hellTest(){
        System.out.println(mailService.hello());
    }
    @Test
    public void sendSimpleMail() throws IOException {
        InputStream fin = this.getClass().getResourceAsStream("/simple-email.txt");
        String content = StreamUtils.copyToString(fin, StandardCharsets.UTF_8);

        mailService.sendSimpleMail("今日名句", "chenxizhan1995dev2@163.com",
                content);
    }

    @Test
    public void sendHtmlMail() throws MessagingException, IOException {
        InputStream fin = this.getClass().getResourceAsStream("/simple-email.html");
        String content = StreamUtils.copyToString(fin, StandardCharsets.UTF_8);
        mailService.sendHtmlMail("今日名句", "chenxizhan1995dev2@163.com",
                content);
    }

    @Test
    public void sendTextMailWithAttachment()
            throws IOException, MessagingException {
        InputStream fin = this.getClass().getResourceAsStream("/simple-email.txt");
        String content = StreamUtils.copyToString(fin, StandardCharsets.UTF_8);

        ClassPathResource file = new ClassPathResource("MIME内容类型.png");
        mailService.sendTextMailWithAttachment("今日名句", "chenxizhan1995dev2@163.com",
                        content,"附件1-MIME内容类型.png", file);
    }

    @Test
    public void sendHtmlMailWithInResource()
            throws IOException, MessagingException {

        InputStream fin = this.getClass().getResourceAsStream("/email.html");
        String content = StreamUtils.copyToString(fin, StandardCharsets.UTF_8);

        ClassPathResource rcs = new ClassPathResource("MIME内容类型.png");
        mailService.sendHtmlMailWithInResource("MIME内容类型", "chenxizhan1995dev2@163.com",
                        content,"001", rcs);
        // 添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现

        // 注：我开始把 “MIME内容类型.png”用作 rcsId，结果打开邮件，图片不能正常显示。
        // 改成001就好了。八成是 rcsId 的合法字符有限制。
    }
}
