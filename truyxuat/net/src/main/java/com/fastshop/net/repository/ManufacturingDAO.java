package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fastshop.net.model.Manufacturing;

public interface ManufacturingDAO extends JpaRepository<Manufacturing, Integer> {
    Manufacturing findByProduct_Id(Integer productId);
    boolean existsByProduct_Id(Integer productId);
} 