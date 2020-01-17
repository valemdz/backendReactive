package com.vale.reactive.models.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.vale.reactive.models.documents.Producto;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String>{

}
