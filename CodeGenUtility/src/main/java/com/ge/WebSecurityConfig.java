package com.ge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
	private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
	
	@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/data/**", "/fonts/**","/img/**", "/js/**","/lib/**","/html/index.html", "/html/login.html");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.authenticationProvider(customAuthenticationProvider)
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/index.html", "/html/home.html", "/html/login.html", "/").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginProcessingUrl("/authenticate")
				.loginPage("/html/login.html").permitAll()
				.successForwardUrl("/home")
				.failureUrl("/")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(restAuthenticationSuccessHandler)
				.failureHandler(restAuthenticationFailureHandler)
				.and()
			.logout()
				.logoutUrl("/logout")
	            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
	            .deleteCookies("JSESSIONID")
	            .permitAll();
		
		 /* http .httpBasic() .and() .authorizeRequests()
		  .antMatchers("/#/index.html", "/home.html", "/#/login.html",
		  "/").permitAll() .anyRequest().authenticated();*/
		 
		/*
		 * http .formLogin().and() .authorizeRequests()
		 * .antMatchers("/index.html", "/home.html", "/login.html",
		 * "/").permitAll() .anyRequest().authenticated();
		 */
	}
	 private CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }
}