package com.vale.reactive.clienteChat;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.vale.reactive.clienteChat.payload.Notificacion;

import reactor.core.publisher.Flux;


@Component
public class WebClientChat {
	
	private static final String API_BASE_URL = "http://127.0.0.1:8080";
	
	private final WebClient webClient;
	
	
	@Autowired
	public WebClientChat() {
		
		 this.webClient = WebClient.builder()
	                .baseUrl(API_BASE_URL )
	                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)              
	                .build();
	}
	
	
	public Flux<Notificacion> listNotificaciones() {
	    return webClient.get()
	           .uri("/notificaciones")
	           .retrieve()
	           .bodyToFlux(Notificacion.class);
	}
	
	
	/*public Flux<Notificacion> listNotificaciones() {
	         return webClient.get()
	                .uri("/user/repos?sort={sortField}&direction={sortDirection}",
	                        "updated", "desc")
	                .exchange()
	                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Notificacion.class));
	}*/
	
}
