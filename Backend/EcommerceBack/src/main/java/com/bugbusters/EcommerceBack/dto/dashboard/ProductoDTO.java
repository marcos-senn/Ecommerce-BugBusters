package com.bugbusters.EcommerceBack.dto.dashboard;

import com.bugbusters.EcommerceBack.dto.dashboard.CategoriaDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductoDTO(
        @NotBlank(message = "Debes incluir un nombre para el producto")
        String nombre,
        @NotBlank(message = "Debes incluir una descripción para el producto")
        String descripcion,
        @NotNull(message = "Debes incluir un precio para el producto")
        @Positive(message = "El precio no puede ser negativo o cero")
        Double precio,
        @NotBlank(message = "Debes incluir un imagen para el producto")
        String imagenUrl,
        @NotNull(message = "Debes asignarle una categoria al producto")
        CategoriaDTO categoria
) {
}
