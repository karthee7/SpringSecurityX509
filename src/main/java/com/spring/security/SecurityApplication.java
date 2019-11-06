package com.spring.security;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	 @PreAuthorize("hasAuthority('ROLE_USER')")
	    @RequestMapping(value = "/user")
	    public String user(Model model, Principal principal) {
	        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
	        model.addAttribute("username", currentUser.getUsername());
	        return "user";
	    }

	@GetMapping("/isValid")
	@PreAuthorize("isMember(#foo, 'Employee','write')")
	public String isValid(@RequestParam String foo) throws Exception {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.jks"), "changeit".toCharArray(),
						"changeit".toCharArray())
				.loadTrustMaterial(ResourceUtils.getFile("classpath:truststore.jks"), "changeit".toCharArray())
				.loadTrustMaterial(null, acceptingTrustStrategy) // accept all
				.build();

		HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(client);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/user", HttpMethod.GET, null,
				String.class);
		return response.getBody();
	}

}
