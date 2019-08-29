package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class WebServcieTool<T> {
	/**
	 * get请求
	  *  查询列表信息
	 * @param baseUrl
	 * @param url
	 * @param param
	 * @param T
	 * @return
	 */
	public List<T> findAll(String baseUrl,String url,Class T) {
		WebClient webClient = WebClient.create(baseUrl);
		Flux<T> userFlux = webClient.get().uri(url).retrieve().bodyToFlux(T);
		List<T> list = userFlux.collectList().block();
		return list;
	}
	
	/**
	 * get请求
	  *  查询单个信息
	 * @param url
	 * @param T
	 * @return
	 */
	public T findOne(String url,Class T) {
		WebClient webClient = WebClient.create();
		Mono<T> mono = webClient.get().uri(url).retrieve().bodyToMono(T);
		T t = mono.block();
		return t;
	}
	
	/**
	 * post请求
	  *  提交数据
	  *  使用bean来post
	 * @param url
	 * @param T
	 * @return
	 */
	public String postInfo(T t,String url,Class T) {
		
		@SuppressWarnings("unchecked")
		Mono<String> resp = WebClient.create().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(t),T)
                .retrieve().bodyToMono(String.class);
		return resp.block();
	}
	

	/**
	 * get请求
	  *  删除信息
	 * @param url
	 * @param T
	 * @return
	 */
	public String delete(String url) {
		WebClient webClient = WebClient.create();
		Mono<String> mono = webClient.get().uri(url).retrieve().bodyToMono(String.class);
		String t = mono.block();
		return t;
	}
}
