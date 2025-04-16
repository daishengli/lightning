package top.daishengli.lightning.util;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 公用工具类
 *
 * @author daishengli
 */
@Component
public class CommonUtil {

    @Resource
    JavaMailSenderImpl mailSender;

    @Resource
    TimedCache<String, Object> timedCache;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * 发送数字验证码
     *
     * @param email 邮箱
     * @param length 验证码长度
     * @return 发送成功与否
     */
    public Boolean sendCode(String email, Integer length) {
        String code = randomCode(length);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");
        message.setText("您的验证码是：" + code + "。5分钟内有效。");
        message.setTo(email);
        message.setFrom(username);
        mailSender.send(message);
        timedCache.put(email, code);
        return true;
    }

    /**
     * 生成数字验证码
     *
     * @param length 验证码位数
     * @return 验证码
     */
    private String randomCode(Integer length) {
        length = Optional.ofNullable(length).orElse(4);
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = RandomUtil.randomInt(0, 10);
            code.append(num);
        }
        return code.toString();
    }
}
