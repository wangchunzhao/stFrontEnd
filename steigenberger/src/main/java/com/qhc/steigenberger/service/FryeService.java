/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.qhc.steigenberger.config.ApplicationConfig;
import com.qhc.steigenberger.service.exception.ExternalServerInternalException;
import com.qhc.steigenberger.service.exception.URLNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wang@dxc.com
 * @param <T>
 *
 */
@Service
public class FryeService<T> {
	
	@Autowired
	ApplicationConfig config;

	private WebClient webClient;

	protected Builder getBuilder() {
		WebClient.Builder webClientBuilder = WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").defaultHeader("App-Locale", "chs")
				.defaultHeader(HttpHeaders.USER_AGENT,
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		webClientBuilder.filter(logRequest());
		return webClientBuilder;
	}

	private ExchangeFilterFunction logRequest() {
		return (clientRequest, next) -> {
			System.out.println("Request: {} {}" + clientRequest.method() + clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> System.out.println("{}={}" + name + value)));
			return next.exchange(clientRequest);
		};
	}

	public void putJason(String path, T params) {
		String url = config.getFryeURL()+path;
		webClient = getBuilder().baseUrl(url).build();
		Mono<String> response = webClient.put().uri(url).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(String.class);
		response.block();
	}

	public void postJason(String path, T params) {
		
		String url = config.getFryeURL()+path;
		webClient = getBuilder().baseUrl(url).build();
		Mono<String> response = webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(String.class);
		response.block();
	}

	public Date getLastUpdatedDate(String path,String params) {
		String url = config.getFryeURL()+path;
		WebClient webClient = getBuilder().baseUrl(url).build();
		Mono<Date> response = webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(Date.class);
		return response.block();
	}
	
	public List<T> getListInfo(String path,Class T) {
		String url = config.getFryeURL()+path;
		WebClient webClient = WebClient.create(url);
		Flux<T> userFlux = webClient.get().uri(url).retrieve().bodyToFlux(T);
		List<T> list = userFlux.collectList().block();
		return list;
	}
	
	/**
	 * post请求返回一个对象
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public T postInfo(T t,String path,Class T) {
		String url = config.getFryeURL()+path;
		@SuppressWarnings("unchecked")
		Mono<T> resp = WebClient.create().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(t),T)
                .retrieve().bodyToMono(T);
		return resp.block();
	}
	
	/**
	 * get请求返回一个对象
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public T getInfo(String path,Class T) {
		String url = config.getFryeURL()+path;
		WebClient webClient = WebClient.create();
		Mono<T> mono = webClient.get().uri(url).retrieve().bodyToMono(T);
		T t = mono.block();
		return t;
	}

}
