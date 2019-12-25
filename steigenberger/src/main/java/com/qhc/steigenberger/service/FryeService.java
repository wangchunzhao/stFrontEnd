/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.service.exception.ExternalServerInternalException;
import com.qhc.steigenberger.service.exception.URLNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Walker
 *
 */
@Service
public class FryeService {

	@Value("${qhc.frye.url}")
	String fryeUrl;

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
			System.out.println(String.format("Request: %s %s", clientRequest.method(), clientRequest.url()));
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> System.out.println(String.format("%s=%s", name, value))));
			return next.exchange(clientRequest);
		};
	}
	/**
	 * 
	 * @param path
	 * @param params
	 */
	public <T> void putJason(String path, T params) {

		webClient = getBuilder().baseUrl(fryeUrl).build();
		Mono<String> response = webClient.put().uri(path).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(String.class);
		response.block();
	}
	/**
	 * 
	 * @param path
	 * @param params
	 */
	public <T> void postJason(String path, T params) {
		
		webClient = getBuilder().baseUrl(fryeUrl).build();
		Mono<String> response = webClient.post().uri(path).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(String.class);
		response.block();
	}
	/**
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public Date getLastUpdatedDate(String path, String params) {
		String url = fryeUrl + path;
		WebClient webClient = getBuilder().baseUrl(url).build();
		Mono<Date> response = webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(Date.class);
		return response.block();
	}

	public <T> List<T> getListInfo(String path, Class T) {
		String url = fryeUrl + path;
		WebClient webClient = WebClient.create(url);
		Flux<T> userFlux = webClient.get().uri(url).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToFlux(T);
		List<T> list = userFlux.collectList().block();
		return list;
	}
	
	/**
	 * 得到一个map结果集
	 */
	public Map<String,Object> getMapData(String path) {
		String url = fryeUrl + path;
		WebClient webClient = getBuilder().baseUrl(url).build();
		Mono<Map> response = webClient.get().uri(url)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(Map.class);
		return response.block();
	}
	

	/**
	 * post请求返回一个对象
	 * 
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public <T> T postInfo(Object t, String path, Class T) {
		String url = fryeUrl + path;
		@SuppressWarnings("unchecked")
		Mono<T> resp = WebClient.create().post().uri(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(t), T).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(T);
		return resp.block();
	}
	/**
	 * 
	 * @param path
	 * @param pars
	 * @param T 
	 * @return
	 */
	public <T> T postForm(String path, Map<String,String> pars, Class T) {
		String url = fryeUrl + path;
		
		@SuppressWarnings("unchecked")
		Mono<T> resp = WebClient.create().post().uri(url).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(pars).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(T);
		return resp.block();
	}
	/**
	 * 
	 * @param path
	 * @param pars
	 * @param T 
	 * @return
	 */
	public <T> T postForm(String path, Object pars, Class T) {
		String url = fryeUrl + path;
		
		@SuppressWarnings("unchecked")
		Mono<T> resp = WebClient.create().post().uri(url).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(pars).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(T);
		return resp.block();
	}
	
	/**
	 * put请求返回一个对象
	 * 
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public <T> T putInfo(T t, String path, Class T) {
		String url = fryeUrl + path;
		@SuppressWarnings("unchecked")
		Mono<T> resp = WebClient.create().put().uri(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(t), T).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(T);
		return resp.block();
	}
	
	/**
	 * 
	 * @param path
	 * @param params
	 */
	public <T> T putForm(String path, Object params, Class T) {
		webClient = getBuilder().baseUrl(fryeUrl).build();
		Mono<String> response = webClient.put().uri(path).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToMono(T);
		return (T)response.block();
	}

	/**
	 * get请求返回一个对象
	 * 
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public <T> T getInfo(String path, Class T) {
		String url = fryeUrl + path;
		WebClient webClient = WebClient.create();
		Mono<T> mono = webClient.get().uri(url).retrieve().bodyToMono(T);
		T t = mono.block();
		return t;
	}

	/**
	 * get请求返回一个对象
	 * 
	 * @param t
	 * @param path
	 * @param T
	 * @return
	 */
	public <T> T getInfo(String path, Object params, Class T) {
		String url = fryeUrl + path;
		
		Map<String, ?> map = new HashMap<String, Object>();
		if (params != null) {
			if (params instanceof Map) {
				map = (Map)params;
			} else {
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.convertValue(params, Map.class);
			}
			StringBuilder sb = new StringBuilder(64);
			for (Map.Entry<String, ?> e : map.entrySet()) {
				sb.append("&").append(e.getKey()).append("={").append(e.getKey()).append("}");
			}
			if (sb.length() > 0) {
				if (path.indexOf("?") >= 0) {
					url += sb.toString();
				} else {
					url += "?" + sb.substring(1);
				}
			}
		}
		
		WebClient webClient = WebClient.create();
		Mono<T> mono = webClient.get().uri(url, map).retrieve().bodyToMono(T);
		T t = mono.block();
		return t;
	}

}
