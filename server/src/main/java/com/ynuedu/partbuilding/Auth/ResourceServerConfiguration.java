package com.ynuedu.partbuilding.Auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;


//http://www.devglan.com/spring-security/spring-boot-security-oauth2-example
@Configuration
@EnableResourceServer
//@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {

        resources.resourceId(AuthorizationServerConfiguration.RESOURCE_ID)
                .stateless(false);
    }

    /**
     *
     * @param http
     * @throws Exception
     * www.devglan.com/spring-security/spring-boot-security-oauth2-example
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {


        http
                .cors()
                .and()
                .csrf().disable()
                //.httpBasic().disable()
                //.anonymous().disable() //取消匿名访问

               // .and()
                .antMatcher("/api/**")
                .authorizeRequests()
               // .anyRequest().authenticated()



                .antMatchers("oauth/token","/","/login", "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());


    }

}