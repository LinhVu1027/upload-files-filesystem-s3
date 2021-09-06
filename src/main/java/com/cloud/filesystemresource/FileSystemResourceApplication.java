package com.cloud.filesystemresource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.lang.reflect.Type;

@SpringBootApplication
public class FileSystemResourceApplication {
	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(FileSystemResourceApplication.class, args);
	}

	@Bean
	public MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter() {
		return new MultipartJackson2HttpMessageConverter(objectMapper);
	}

	public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
		protected MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
			super(objectMapper, MediaType.APPLICATION_OCTET_STREAM, MediaType.TEXT_PLAIN);
		}

		@Override
		public boolean canWrite(Class<?> clazz, MediaType mediaType) {
			return false;
		}

		@Override
		public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
			return false;
		}

		@Override
		protected boolean canWrite(MediaType mediaType) {
			return false;
		}
	}
}
