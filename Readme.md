# Springboot 发送电子邮件
主要参考文章[Spring Boot (十)：邮件服务 - 纯洁的微笑博客](http://www.ityouknow.com/springboot/2017/05/06/spring-boot-mail.html)
并做了一些微调。
1. 类 MailServiceImpl implements MailService 改成了类 MailService，去掉了用户自定义接口MailService的声明。
2. 原代码中邮件正文写在Java代码中，这里改成了从资源文件读取
3. 发送带附件的邮件时，代码使用的 FileSystemResource，这样会与操作系统路径绑定，改成了 ClassPathResource 
读取类路径下的文件，因为文件位于 test/resource 下，没有考虑封装到jar包内，所以不考虑从资源文件进入jar包的情况。
