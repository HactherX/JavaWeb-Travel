package cn.hacther.travel.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {
    private static final String USER = "1035106992@qq.com"; // 发件人称号，同邮箱地址
    private static final String PASSWORD = "opnacrlzzeozbffi"; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String msgBuilder(String code,String name,String email){
        String msg = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>激活你的账户</title>\n" +
                "\n" +
                "  <style>\n" +
                "    body,html,div,ul,li,button,p,img,h1,h2,h3,h4,h5,h6 {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body,html {\n" +
                "      background: #fff;\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    h1,h2,h3,h4,h5,h6 {\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    .email_warp {\n" +
                "      height: 100vh;\n" +
                "      min-height: 500px;\n" +
                "      font-size: 14px;\n" +
                "      color: #212121;\n" +
                "      display: flex;\n" +
                "      /* align-items: center; */\n" +
                "      justify-content: center;\n" +
                "    }\n" +
                "\n" +
                "    .logo {\n" +
                "      margin: 3em auto;\n" +
                "      width: 200px;\n" +
                "      height: 60px;\n" +
                "    }\n" +
                "\n" +
                "    h1.email-title {\n" +
                "      font-size: 26px;\n" +
                "      font-weight: 500;\n" +
                "      margin-bottom: 15px;\n" +
                "      color: #252525;\n" +
                "    }\n" +
                "\n" +
                "    a.links_btn {\n" +
                "      border: 0;\n" +
                "      background: #4C84FF;\n" +
                "      color: #fff;\n" +
                "      width: 100%;\n" +
                "      height: 50px;\n" +
                "      line-height: 50px;\n" +
                "      font-size: 16px;\n" +
                "      margin: 40px auto;\n" +
                "      box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.15);\n" +
                "      border-radius: 4px;\n" +
                "      outline: none;\n" +
                "      cursor: pointer;\n" +
                "      transition: all 0.3s;\n" +
                "      text-align: center;\n" +
                "      display: block;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips {\n" +
                "      color: #757575;\n" +
                "      background: #f7f7f7;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips .desc {\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp {\n" +
                "      max-width: 140px;\n" +
                "      margin: 20px auto;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp img {\n" +
                "      max-width: 100%;\n" +
                "      max-height: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .email-footer {\n" +
                "      margin-top: 2em;\n" +
                "    }\n" +
                "\n" +
                "    #reset-password-email {\n" +
                "      max-width: 500px;\n" +
                "    }\n" +
                "    #reset-password-email .accout_email {\n" +
                "      color: #4C84FF;\n" +
                "      display: block;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <section class=\"email_warp\">\n" +
                "    <div id=\"reset-password-email\">\n" +
                "      <div class=\"logo\">\n" +
                "        <img src=\"https://img.hacther.cn/images/2022/12/08/logo.jpg\" alt=\"logo\">\n" +
                "      </div>\n" +
                "\n" +
                "      <h1 class=\"email-title\">\n" +
                "        尊敬的<span>"+ name +"</span>您好：\n" +
                "      </h1>\n" +
                "      <p>您正在为注册邮箱为如下地址的账户进行激活：</p>\n" +
                "      <b class=\"accout_email\">"+ email +"</b>\n" +
                "\n" +
                "      <p>请注意，如果这不是您本人的操作，请忽略并关闭此邮件。</p>\n" +
                "      <p>如您确认使用此邮箱注册账户，请点击下方按钮。</p>\n" +
                "\n" +
                "      <a class=\"links_btn\" href=\"http://192.168.31.167:8080/travel/user/activeUser?code="+ code +"\">激活账户</a>\n" +
                "\n" +
                "      <div class=\"warm_tips\">\n" +
                "        <div class=\"desc\">\n" +
                "          为安全起见，以上按钮为一次性链接，且仅在24小时内有效，请您尽快完成操作。\n" +
                "        </div>\n" +
                "\n" +
                "        <p>如有任何疑问或无法完成注册，请通过如下方式与我们联系：</p>\n" +
                "        <p>邮箱：hcg-sky@qq.com</p>\n" +
                "        <p>感谢您的加入！</p>\n" +
                "\n" +
                "        <div class=\"qr_warp\">\n" +
                "          <img src=\"https://img.hacther.cn/images/2022/06/21/56498676_358177904795296_6718095468246597632_n.jpg\" alt=\"微信二维码图片\">\n" +
                "        </div>\n" +
                "        <p>本邮件由系统自动发送，请勿回复。</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"email-footer\">\n" +
                "        <p>您的旅游助理</p>\n" +
                "        <p>Hacther</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n";
        return msg;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        MailUtils.sendMail("1535106992@qq.com","<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>重置您的 无敌软件的 密码</title>\n" +
                "\n" +
                "  <style>\n" +
                "    body,html,div,ul,li,button,p,img,h1,h2,h3,h4,h5,h6 {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body,html {\n" +
                "      background: #fff;\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    h1,h2,h3,h4,h5,h6 {\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    .email_warp {\n" +
                "      height: 100vh;\n" +
                "      min-height: 500px;\n" +
                "      font-size: 14px;\n" +
                "      color: #212121;\n" +
                "      display: flex;\n" +
                "      /* align-items: center; */\n" +
                "      justify-content: center;\n" +
                "    }\n" +
                "\n" +
                "    .logo {\n" +
                "      margin: 3em auto;\n" +
                "      width: 200px;\n" +
                "      height: 60px;\n" +
                "    }\n" +
                "\n" +
                "    h1.email-title {\n" +
                "      font-size: 26px;\n" +
                "      font-weight: 500;\n" +
                "      margin-bottom: 15px;\n" +
                "      color: #252525;\n" +
                "    }\n" +
                "\n" +
                "    a.links_btn {\n" +
                "      border: 0;\n" +
                "      background: #4C84FF;\n" +
                "      color: #fff;\n" +
                "      width: 100%;\n" +
                "      height: 50px;\n" +
                "      line-height: 50px;\n" +
                "      font-size: 16px;\n" +
                "      margin: 40px auto;\n" +
                "      box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.15);\n" +
                "      border-radius: 4px;\n" +
                "      outline: none;\n" +
                "      cursor: pointer;\n" +
                "      transition: all 0.3s;\n" +
                "      text-align: center;\n" +
                "      display: block;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips {\n" +
                "      color: #757575;\n" +
                "      background: #f7f7f7;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips .desc {\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp {\n" +
                "      max-width: 140px;\n" +
                "      margin: 20px auto;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp img {\n" +
                "      max-width: 100%;\n" +
                "      max-height: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .email-footer {\n" +
                "      margin-top: 2em;\n" +
                "    }\n" +
                "\n" +
                "    #reset-password-email {\n" +
                "      max-width: 500px;\n" +
                "    }\n" +
                "    #reset-password-email .accout_email {\n" +
                "      color: #4C84FF;\n" +
                "      display: block;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <section class=\"email_warp\">\n" +
                "    <div id=\"reset-password-email\">\n" +
                "      <div class=\"logo\">\n" +
                "        <img src=\"https://img.hacther.cn/images/2022/12/08/logo.jpg\" alt=\"logo\">\n" +
                "      </div>\n" +
                "\n" +
                "      <h1 class=\"email-title\">\n" +
                "        尊敬的<span>AAA</span>您好：\n" +
                "      </h1>\n" +
                "      <p>您正在为注册邮箱为如下地址的账户进行激活：</p>\n" +
                "      <b class=\"accout_email\">xxxx@abc.com</b>\n" +
                "\n" +
                "      <p>请注意，如果这不是您本人的操作，请忽略并关闭此邮件。</p>\n" +
                "      <p>如您确认使用此邮箱注册账户，请点击下方按钮。</p>\n" +
                "\n" +
                "      <a class=\"links_btn\" onclick=\"window.open('https:XXXXXXXXXXX')\">激活账户</a>\n" +
                "\n" +
                "      <div class=\"warm_tips\">\n" +
                "        <div class=\"desc\">\n" +
                "          为安全起见，以上按钮为一次性链接，且仅在24小时内有效，请您尽快完成操作。\n" +
                "        </div>\n" +
                "\n" +
                "        <p>如有任何疑问或无法完成注册，请通过如下方式与我们联系：</p>\n" +
                "        <p>邮箱：hcg-sky@qq.com</p>\n" +
                "        <p>感谢您的加入！</p>\n" +
                "\n" +
                "        <div class=\"qr_warp\">\n" +
                "          <img src=\"https://img.hacther.cn/images/2022/06/21/56498676_358177904795296_6718095468246597632_n.jpg\" alt=\"微信二维码图片\">\n" +
                "        </div>\n" +
                "        <p>本邮件由系统自动发送，请勿回复。</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"email-footer\">\n" +
                "        <p>您的旅游助理</p>\n" +
                "        <p>Hacther</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n","测试邮件");
        System.out.println("发送成功");
    }
}
