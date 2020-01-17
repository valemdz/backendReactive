package com.vale.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.vale.reactive.clienteChat.WebClientChat;
import com.vale.reactive.models.documents.Producto;
import com.vale.reactive.models.repositories.ProductoRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class BackendReactiveApplication implements CommandLineRunner {
	
	@Autowired
	WebClientChat webClientChat;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired 
	ReactiveMongoTemplate reactiveMonoTemplate;

	private Logger logger = LoggerFactory.getLogger( BackendReactiveApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BackendReactiveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//webClientChat.listNotificaciones().subscribe( n -> logger.info( n.toString() ) );
		
		
		//inicializarYBorrarConRepository();
		inicializarYBorrarConMongoTemplate();
		
	}
	
	public void inicializarYBorrarConRepository(){
		
		logger.info("inicializarYBorrarConRepository");
		
		productoRepository.deleteAll().subscribe();
		
		
		Flux.just( new Producto("tv", 15000.00),
				   new Producto("movil", 30000.00) )
				   .map( p -> { p.setCreateAt( new java.util.Date() );
						  return p;		
						 } )
				 .flatMap( p -> productoRepository.save(p) )
				 .subscribe(  p ->  logger.info("Insert: " +  p.getNombre() ) );

		/*Flux.just( new Producto("tv", 15000.00),
				   new Producto("movil", 30000.00) )
				 .flatMap( p -> productoRepository.save(p) )
				 .subscribe(  p ->  logger.info("Insert: " +  p.getNombre() ),
						 	  err -> logger.error( err.getMessage() ),
						 	  new Runnable() {							
								@Override
									public void run() {
										logger.info("Eliminaran los productos");
										productoRepository.deleteAll().subscribe();
									} }
						 	  );*/
		
	}
	
	public void inicializarYBorrarConMongoTemplate(){
		
		
		logger.info("inicializarYBorrarConMongoTemplate");
		
		reactiveMonoTemplate.dropCollection("productos").subscribe();

		Flux.just(  new Producto("TV Panasonic Pantalla LCD", 456.89),
					new Producto("Sony Camara HD Digital", 177.89),
					new Producto("Apple iPod", 46.89),
					new Producto("Sony Notebook", 846.89),
					new Producto("Hewlett Packard Multifuncional", 200.89),
					new Producto("Bianchi Bicicleta", 70.89),
					new Producto("HP Notebook Omen 17", 2500.89),
					new Producto("Mica Cómoda 5 Cajones", 150.89),
					new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89) )
				 .map( p -> { p.setCreateAt( new java.util.Date() );
					  return p;		
					 } )
				 .flatMap( p -> productoRepository.save(p) )
				 .subscribe(  p ->  logger.info("Insert: " +  p.getNombre() ) );
		
	}

}
