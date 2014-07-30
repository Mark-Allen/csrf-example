package com.stackoverflow.example.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;

@Configuration
@EnableWebMvcSecurity 
public class SecurityWebConfiguration extends WebSecurityConfigurerAdapter {

	protected static final Logger logger = LoggerFactory.getLogger(SecurityWebConfiguration.class);
 

	@Bean
	public CsrfRequestDataValueProcessor requestDataValueProcessor() {
		return new CsrfRequestDataValueProcessor();
	} 
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home","/register","/input","/output","/output/*","/**/*.css","/**/*.js","/**/*.png").permitAll()
                .anyRequest().authenticated();
        
        http
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        
        // TODO Important remove this !!! 
        // csrf should be enabled
        http.csrf().disable();
        
		CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); 
    }

	static class CustomAccessDeniedHandler implements AccessDeniedHandler { 
		public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
				throws IOException, ServletException {
			
			logger.warn("Arrived in custom access denied handler.");
			HttpSession session = request.getSession();
			System.out.println("Session is " +session );
	        System.out.println("Session id = " + session.getId());
			System.out.println("Session max interval="+session.getMaxInactiveInterval());
			System.out.println("Session last used="+session.getLastAccessedTime());
			System.out.println("Time now="+new Date().getTime());
			
			System.out.println();
			System.out.println("csrf:");
			Object csrf = request.getAttribute("_csrf");
			
			if (csrf==null) { 
				System.out.println("csrf is null");
			} else { 
				System.out.println(csrf.toString());
				if (csrf instanceof DefaultCsrfToken) { 
					DefaultCsrfToken token = (DefaultCsrfToken) csrf;
					System.out.println("Parm name " + token.getParameterName());
					System.out.println("Token " + token.getToken());
				}
				 
			}
			System.out.println();
			System.out.println("Request:");
			System.out.println(request.toString());
			System.out.println();
			System.out.println("Response:");
			System.out.println(response.toString());
			System.out.println();
			System.out.println("Exception:");
			System.out.println(accessDeniedException.toString());
		}
	}
}