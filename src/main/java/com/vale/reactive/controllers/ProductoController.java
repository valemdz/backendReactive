package com.vale.reactive.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.vale.reactive.models.documents.Producto;
import com.vale.reactive.models.repositories.ProductoRepository;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	private Logger logger =  LoggerFactory.getLogger( ProductoController.class );
	
	@Autowired
	ProductoRepository productoRepository;
	
	@GetMapping({"/listar","/"})
	public String listarProductos( Model model ) {
		
		Flux<Producto> productos = productoRepository
								   .findAll().map( p -> {
									   p.setNombre( p.getNombre().toUpperCase() );
									   return p;
								   } );	
		
		productos.subscribe( p -> logger.info( p.getNombre() ));
		
		// No hace falta poner suscribe tymeleaf se susbcribe automaticamente
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		
		return "listar";
	}
	
	@GetMapping("/listar-datadriver")
	public String listarDataDriver( Model model ) {
		
		Flux<Producto> productos = productoRepository
								   .findAll().map( p -> {
									   p.setNombre( p.getNombre().toUpperCase() );
									   return p;
								   } ).delayElements( Duration.ofSeconds(1) );	
		
		productos.subscribe( p -> logger.info( p.getNombre() ),
							 err -> logger.error(err.toString()),
							 new Runnable() {
								
								@Override
								public void run() {
 
									logger.info( "Termino el datadriver" );
								}
							});
		
		// No hace falta poner suscribe tymeleaf se susbcribe automaticamente
		// No hace falta cambiar nada en la vista
		model.addAttribute("productos", new ReactiveDataDriverContextVariable( productos, 2 ));
		model.addAttribute("titulo", "Listado de productos");
		
		
		return "listar";
	}
	
	
	@GetMapping("/listar-full")
	public String listarProductosFull( Model model ) {
		
		Flux<Producto> productos = productoRepository
								   .findAll().map( p -> {
									   p.setNombre( p.getNombre().toUpperCase() );
									   return p;
								   } ).repeat(5000);	
		
			
		// No hace falta poner suscribe tymeleaf se susbcribe automaticamente
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		
		return "listar";
	}

	
	@GetMapping("/listar-chunked")
	public String listarProductosChunked( Model model ) {
		
		Flux<Producto> productos = productoRepository
								   .findAll().map( p -> {
									   p.setNombre( p.getNombre().toUpperCase() );
									   return p;
								   } ).repeat(5000);;		
		
		
		
		// No hace falta poner suscribe tymeleaf se susbcribe automaticamente
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		
		return "listar-chunked";
	}
	
	
	
}
