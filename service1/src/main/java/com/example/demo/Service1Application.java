package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class Service1Application {
	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}
}

@Configuration
class Config{
//	@Bean
//	OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url2) {
//	    return OtlpHttpSpanExporter.builder()
//	            .setEndpoint(url2)
//	            .build();
//	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(RestTemplateBuilder builder) {//NOTE CHO NAY DUNG RestTemplateBuilder NEU DUNG new RestTemplate THI SE KO TRACE DC
		return builder.build();
	}
}

@RestController
class S1Controller{
	@Value("${spring.application.name}")
	private String name;
	private RestTemplate restTemplate;
	
	public S1Controller(RestTemplate restTemplate) {
		// TODO Auto-generated constructor stub
		this.restTemplate=restTemplate;
	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello %s".formatted(this.name), HttpStatus.OK);
	}
	
	@GetMapping("/callAllServices")
	public ResponseEntity<String> callAllServices() {
		String format="http://service%d/hello";
		StringBuilder builder=new StringBuilder();
		for(int i=1;i<=4;++i) {
			builder.append(this.restTemplate.getForEntity(format.formatted(i), String.class).getBody()).append("<br>");
		}
		return new ResponseEntity<String>(builder.toString(),HttpStatus.OK);
	}
	
	@GetMapping("/callService234")
	public ResponseEntity<String> callService234() {
		String url="http://service2/callService34";
		return new ResponseEntity<>(this.restTemplate.getForEntity(url,String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService1")
	public ResponseEntity<String> callService1() {
		String url="http://service1/hello";
		return new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService2")
	public ResponseEntity<String> callService2() {
		String url="http://service2/hello";
		return new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService3")
	public ResponseEntity<String> callService3() {
		String url="http://service3/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService4")
	public ResponseEntity<String> callService4() {
		String url="http://service4/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
}
