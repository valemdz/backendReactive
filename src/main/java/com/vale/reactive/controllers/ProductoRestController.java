package com.vale.reactive.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vale.reactive.models.repositories.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.vale.reactive.models.documents.Producto;

@RestController
@RequestMapping("/app/producto")
public class ProductoRestController {
	
	private Logger logger =  LoggerFactory.getLogger( ProductoRestController.class );
	
	@Autowired
	ProductoRepository productoRepository;
	
	@GetMapping
	public Flux< Producto > getProductos() {
		return productoRepository.findAll()
								 .map( p -> { p.setNombre(p.getNombre().toUpperCase());
								 			  return p;
									 		})
								 .doOnNext( p -> logger.info( p.getNombre() ));
	}

	@GetMapping("/{id}")
	public Mono< Producto > getProducto( @PathVariable String id) {
		
		//return productoRepository.findById(id);
		
		Flux<Producto> productos =  productoRepository.findAll();		
		Mono<Producto> unProducto = productos.filter( p -> p.getId().equalsIgnoreCase(id) )
											 .next()
											 .doOnNext( p -> logger.info( p.getNombre() ) );
		return unProducto;
	}


}
