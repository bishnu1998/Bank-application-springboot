package com.bank.application.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.bank.application.filter.AuthoritiesLoggingAfterFilter;
import com.bank.application.filter.CsrfCookiesFilter;
import com.bank.application.filter.RequestValidationBeforeFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName("_csrf");

		http.securityContext().requireExplicitSave(false).and()
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)).cors()
				.configurationSource(new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setMaxAge(3600L);
						return config;
					}
				}).and()
				.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
						.ignoringRequestMatchers("/contact", "/register")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.addFilterAfter(new CsrfCookiesFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class)
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount").hasRole("USER")
						.requestMatchers("myBalance").hasAnyRole("USER", "ADMIN").requestMatchers("/myLoans")
						.hasRole("USER").requestMatchers("/myCards").hasRole("USER").requestMatchers("/user")
						.authenticated().requestMatchers("/notices", "/contact", "/register").permitAll())
				.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
		return http.build();
	}

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService() {
	 * InMemoryUserDetailsManager userInfo = new InMemoryUserDetailsManager();
	 * UserDetails user =
	 * User.withUsername("user").password("user").authorities("USER").build();
	 * UserDetails admin =
	 * User.withUsername("admin").password("admin").authorities("ADMIN").build();
	 * userInfo.createUser(user); userInfo.createUser(admin);
	 * 
	 * return userInfo; }
	 * 
	 * 
	 */

	/*
	 * @Bean public UserDetailsService userDetailsSercice(DataSource dataSource) {
	 * return new JdbcUserDetailsManager(dataSource); }
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
