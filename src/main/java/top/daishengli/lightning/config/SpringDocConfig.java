package top.daishengli.lightning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * API文档相关配置
 *
 * @author daishengli
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact().name("阿戴").email("daishengli1024@outlook.com");
        Info info = new Info().title("接口文档").description("项目接口文档").version("v1.0.0").contact(contact);
        return new OpenAPI().info(info);
    }
}
