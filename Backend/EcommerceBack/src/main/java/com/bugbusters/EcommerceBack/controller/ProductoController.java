package com.bugbusters.EcommerceBack.controller;

import com.bugbusters.EcommerceBack.service.ProductoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    //Controladores para el crud del admin

    //TODO: crear los endpoints
    //TODO: investigar como tratar archivos de imagenes



    //Controladores para la página productos
}
