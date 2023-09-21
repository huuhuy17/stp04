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
public class Service2Application {
	public static void main(String[] args) {
		SpringApplication.run(Service2Application.class, args);
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
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}

@RestController
class S2Controller{
	@Value("${spring.application.name}")
	private String name;
	private RestTemplate restTemplate;
	
	public S2Controller(RestTemplate restTemplate) {
		// TODO Auto-generated constructor stub
		this.restTemplate=restTemplate;
	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return new ResponseEntity<>("Hello %s".formatted(name), HttpStatus.OK);
	}
	
	@GetMapping("/callService34")
	public ResponseEntity<String> callService34() {
		String url="http://service3/callService4";
		return new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService1")
	public ResponseEntity<String> callService1(){
		String url="http://service1/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService2")
	public ResponseEntity<String> callService2(){
		String url="http://service2/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService3")
	public ResponseEntity<String> callService3(){
		String url="http://service3/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
	
	@GetMapping("/callService4")
	public ResponseEntity<String> callService4(){
		String url="http://service4/hello";
		return  new ResponseEntity<>(this.restTemplate.getForEntity(url, String.class).getBody(), HttpStatus.OK);
	}
}
