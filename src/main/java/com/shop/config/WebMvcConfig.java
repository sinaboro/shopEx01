package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class WebMvcConfig implements WebMvcConfigurer {

    //리소스 업로드 경로 uploadPath=file:///C:/shop/
    @Value("${uploadPath}")
    String uploadPath;

   /*
        1. /images/로 시작하는 모든 URL 요청을 처리하는 리소스 핸들러를 추가
        2. uploadPath에 설정된 경로에서 리소스를 찾도록 설정 =>
            C:/shop/ 디렉토리에서 파일을 찾음.
        3. 만약 /images/example.jpg로 요청이 들어오면,
           c:/shop/example.jpg 파일을 찾는다.
    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
