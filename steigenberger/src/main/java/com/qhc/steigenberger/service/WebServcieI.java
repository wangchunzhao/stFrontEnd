package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * 
 * @author lizuoshan
 *
 * @param <T>
 */
@Component
public class WebServcieI<T> {
	
	public List<T> getAll(String baseUrl,String url,Object param,Class T) {
		WebClient webClient = WebClient.create(baseUrl);
		Flux<T> userFlux = webClient.get().uri(url+param).retrieve().bodyToFlux(T);
		List<T> list = userFlux.collectList().block();
		return list;
	}
	
	public T getOne(String url,Class T) {
		WebClient webClient = WebClient.create();
		Mono<T> mono = webClient.get().uri(url).retrieve().bodyToMono(T);
		T t = mono.block();
		return t;
	}
	
//	public List<Role> dobusiness(String baseUrl,String url,Object param,Class ccc) {
//		String baseUrl = "http://localhost:8081/frye/";
//		WebClient webClient = WebClient.create(baseUrl);
//		Flux<Role> userFlux = webClient.get().uri("role/roleList/"+param).retrieve().bodyToFlux(Role.class);
//		userFlux.subscribe(System.out::println);
//		
//		List<Role> roles = userFlux.collectList().block();
//		return roles;
//	}
}
