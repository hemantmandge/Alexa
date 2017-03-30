package com.ge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
	private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
	
	@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    	.inMemoryAuthentication()
    	.passwordEncoder(new ShaPasswordEncoder(256))
        .withUser("etluser").password("0d8b950966f82370e2f6cda05e2e2d15dcb00ae13af5068e4a87a5667b58e79c").roles("USER");
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/data/**", "/fonts/**","/img/**", "/js/**","/lib/**","/index.html", "/html/login.html");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.authenticationProvider(customAuthenticationProvider)
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/index.html", "/html/login.html", "/").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginProcessingUrl("/authenticate")
				.loginPage("/").permitAll()
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
	/* private CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }*/
}