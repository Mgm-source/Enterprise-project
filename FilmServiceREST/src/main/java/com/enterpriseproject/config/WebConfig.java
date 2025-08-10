package com.enterpriseproject.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.enterpriseproject.film.Converters.CsvHttpConverter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters){

		converters.add(new CsvHttpConverter());
	}

}
