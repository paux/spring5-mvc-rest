package cc.paukner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig /*extends WebMvcConfigurationSupport*/ {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // same result, but allows tweaking
                .paths(PathSelectors.any())          // same result, but allows tweaking
                .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }
    // this Docket object will be injected into the Spring context

    // Spring Boot will do this automatically, but if not, here's what to add:
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

    private ApiInfo metaData() {
        Contact contact = new Contact("OE3SPR", "https://qrz.com/db/oe3spr", "oe3spr@oevsv.at");

        return new ApiInfo(
                "OE3SPR",
                "Spring Framework 5: Beginner to Guru",
                "1.0",
                "Terms of Service: who cares",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
