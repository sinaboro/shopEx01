"쇼핑몰 프로젝트" 

1. springboot version
  : 2.7.1
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.7.1</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
```

2. java version
  : 11
```xml
<properties>
  <java.version>11</java.version>
</properties>
```

3.
이 프로젝트는 MySQL 데이터베이스를 사용합니다. MySQL 데이터베이스와의 연결을 위해 `mysql-connector-java` 라이브러리가 필요합니다. Maven을 사용하는 경우, `pom.xml` 파일에 다음 의존성을 추가하십시오:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```
4. 주요 버전별 구현 방법<br>
  스프링 부트 2.X.X ~ 2.6.X (스프링 5.X.X ~ 5.6.X)
```java
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http
	.authorizeRequests()
        .antMatchers("/").authenticated()
        .anyRequest().permitAll();
    }
}
```
스프링 부트 2.7.X ~ 3.0.X (스프링 5.7.X M2 ~ 6.0.X)
```java
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
	 .authorizeHttpRequests()
	 .requestMatchers("/admin").hasRole("ADMIN")
	 .anyRequest().authenticated();
        return http.build();
    }
}
```
스프링 부트 3.1.X ~ (스프링 6.1.X ~ )
```java
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> auth
                  .requestMatchers("/login", "/join").permitAll()
                  .anyRequest().authenticated()
        );
        return http.build();
    }
}
```
5. SecurityConfig.java : 설정 파일 변경
```java
package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        log.info("--------------------securityFilterChain-----------------------------");
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

```