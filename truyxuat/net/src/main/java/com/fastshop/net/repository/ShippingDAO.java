package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fastshop.net.model.Shipping;

public interface ShippingDAO extends JpaRepository<Shipping, Integer> {
    // Tìm Shipping theo Product ID
    Shipping findByProduct_Id(Integer productId);
} 