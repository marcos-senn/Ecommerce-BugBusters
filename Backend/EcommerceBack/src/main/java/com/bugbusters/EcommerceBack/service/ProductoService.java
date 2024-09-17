package com.bugbusters.EcommerceBack.service;

import com.bugbusters.EcommerceBack.dto.dashboard.ActualizarProductoDTO;
import com.bugbusters.EcommerceBack.dto.dashboard.CategoriaDTO;
import com.bugbusters.EcommerceBack.dto.dashboard.CrearProductoDTO;
import com.bugbusters.EcommerceBack.dto.dashboard.RespuestaProductoDTO;
import com.bugbusters.EcommerceBack.entity.Categoria;
import com.bugbusters.EcommerceBack.entity.Producto;
import com.bugbusters.EcommerceBack.repository.dashboard.ICategoriaRepository;
import com.bugbusters.EcommerceBack.repository.dashboard.IProductoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Value("${ruta.imagenes}")
    private String rutaImagenes;

    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;

    public ProductoService(IProductoRepository productoRepository, ICategoriaRepository categoriaRepository){ //inyección de dependencia
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }


    public List<RespuestaProductoDTO> getProductos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> new RespuestaProductoDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(),
                        producto.getPrecio(), producto.getImagenUrl(), new CategoriaDTO(producto.getCategoria().getNombre())))
                .collect(Collectors.toList());
    }

    public RespuestaProductoDTO saveProducto(CrearProductoDTO productoDTO){
        String nombreImagen = guardarImagen(productoDTO.imagen());

        Producto producto = new Producto();
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setImagenUrl("/imagenes/" + nombreImagen);

        Categoria categoria = categoriaRepository.findByNombre(productoDTO.categoria().nombre());

        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);

        return new RespuestaProductoDTO(productoGuardado.getId(), productoGuardado.getNombre(), productoGuardado.getDescripcion(),
                                        productoGuardado.getPrecio(), productoGuardado.getImagenUrl(), new CategoriaDTO(productoGuardado.getCategoria().getNombre()));

    }

    private String guardarImagen(MultipartFile imagen) {
        Path rutaDirectorio = Paths.get(rutaImagenes);
        if (!Files.exists(rutaDirectorio)) {
            try {
                Files.createDirectories(rutaDirectorio);
            } catch (IOException e) {
                throw new RuntimeException("Error al crear el directorio: " + e.getMessage(), e);
            }
        }

        String nombreOriginal = imagen.getOriginalFilename();

        // Generar un nombre único para el archivo
        String nombreArchivo = UUID.randomUUID().toString() + "_" + nombreOriginal;

        // Ruta completa donde se guardará la imagen
        Path rutaCompleta = rutaDirectorio.resolve(nombreArchivo);

        // Guardar la imagen en el sistema de archivos
        try {
            Files.copy(imagen.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }

        return nombreArchivo;
    }


    public RespuestaProductoDTO updateProducto(ActualizarProductoDTO productoDTO){
        Producto producto = productoRepository.findById(productoDTO.id()).get();

        String nombreImagen = guardarImagen(productoDTO.imagen());

        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setImagenUrl("imagenes/" + nombreImagen);

        Categoria categoria = categoriaRepository.findByNombre(productoDTO.categoria().nombre());
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        return new RespuestaProductoDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(),
                producto.getImagenUrl(), new CategoriaDTO(producto.getCategoria().getNombre()));

    }

    public void deleteProducto(Long id){
        Producto producto = productoRepository.findById(id).get();
        productoRepository.delete(producto);

        String imageName = producto.getImagenUrl();

        //Eliminar la imagen del servidor
        // Extraer solo el nombre del archivo
        Path filePath = Paths.get(imageName);
        String nombreImagen = filePath.getFileName().toString();

        // Eliminar la imagen del servidor
        Path fullPath = Paths.get(rutaImagenes).resolve(nombreImagen).normalize();
        File file = fullPath.toFile();
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("No se pudo eliminar el archivo: " + imageName);
            }
        } else {
            throw new RuntimeException("El archivo no existe: " + imageName);
        }

    }
}
