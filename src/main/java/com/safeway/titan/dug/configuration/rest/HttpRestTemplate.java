package com.safeway.titan.dug.configuration.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;

@Configuration
@ConfigurationProperties("eom_rest")
public class HttpRestTemplate {
	
	@NotNull
	@Setter
	private String user;
	@NotNull
	@Setter
	private String password;

	@Bean
	public RestTemplate createRestTemplate() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Accept", MediaType.APPLICATION_XML_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
		Map<String, String> params = new HashMap<String, String>();
		RestTemplate rt = new RestTemplate();
		rt.getInterceptors().add(new BasicAuthorizationInterceptor(user, password));
		return rt;
	}

	/*
	 * HttpHeaders createHeaders(String username, String password){ return new
	 * HttpHeaders() {{ String auth = username + ":" + password; byte[] encodedAuth
	 * = Base64.encodeBase64( auth.getBytes(CharSet.forName("US-ASCII")) ); String
	 * authHeader = "Basic " + new String( encodedAuth ); set( "Authorization",
	 * authHeader ); }}; }
	 */
}
