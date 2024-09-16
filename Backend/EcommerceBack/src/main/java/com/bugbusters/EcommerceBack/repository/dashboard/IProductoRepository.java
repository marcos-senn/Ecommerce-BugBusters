package com.bugbusters.EcommerceBack.repository.dashboard;

import com.bugbusters.EcommerceBack.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
}
