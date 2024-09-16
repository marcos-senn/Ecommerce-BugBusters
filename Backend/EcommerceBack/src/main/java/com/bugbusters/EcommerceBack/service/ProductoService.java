package com.bugbusters.EcommerceBack.service;

import com.bugbusters.EcommerceBack.dto.dashboard.ActualizarProductoDTO;
import com.bugbusters.EcommerceBack.dto.dashboard.CategoriaDTO;
import com.bugbusters.EcommerceBack.dto.dashboard.ProductoDTO;
import com.bugbusters.EcommerceBack.entity.Categoria;
import com.bugbusters.EcommerceBack.entity.Producto;
import com.bugbusters.EcommerceBack.repository.dashboard.ICategoriaRepository;
import com.bugbusters.EcommerceBack.repository.dashboard.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;

    public ProductoService(IProductoRepository productoRepository, ICategoriaRepository categoriaRepository){ //inyección de dependencia
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }


    public List<ProductoDTO> getProductos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> new ProductoDTO(producto.getNombre(), producto.getDescripcion(),
                        producto.getPrecio(), producto.getImagenUrl(), new CategoriaDTO(producto.getCategoria().getNombre())))
                .collect(Collectors.toList());
    }

    public ProductoDTO saveProducto(ProductoDTO productoDTO){
        Producto producto = new Producto();
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setImagenUrl(productoDTO.imagenUrl());

        Categoria categoria = categoriaRepository.findByNombre(productoDTO.categoria().nombre());

        producto.setCategoria(categoria);

        productoRepository.save(producto);

        return new ProductoDTO(producto.getNombre(), producto.getDescripcion(), producto.getPrecio(),
                                producto.getImagenUrl(), new CategoriaDTO(producto.getCategoria().getNombre()));

    }

    public ProductoDTO updateProducto(ActualizarProductoDTO productoDTO){
        Producto producto = productoRepository.findById(productoDTO.id()).get();

        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setImagenUrl(productoDTO.imagenUrl());

        Categoria categoria = categoriaRepository.findByNombre(productoDTO.categoria().nombre());
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        return new ProductoDTO(producto.getNombre(), producto.getDescripcion(), producto.getPrecio(),
                producto.getImagenUrl(), new CategoriaDTO(producto.getCategoria().getNombre()));

    }

    public void deleteProducto(Long id){
        Producto producto = productoRepository.findById(id).get();
        productoRepository.delete(producto);
    }
}
