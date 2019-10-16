package com.wba.leap.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.wba.leap.card.interceptors.HeaderInterceptor;

@SpringBootApplication
public class CardApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean leapAPIFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HeaderInterceptor());
		return registration;
	}
}
