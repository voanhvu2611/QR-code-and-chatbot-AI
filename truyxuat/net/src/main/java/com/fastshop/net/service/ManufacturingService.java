package com.fastshop.net.service;

import java.util.List;
import com.fastshop.net.model.Manufacturing;

public interface ManufacturingService {
    List<Manufacturing> findAll();
    Manufacturing findById(Integer id);
    Manufacturing findByProductId(Integer productId);
    Manufacturing save(Manufacturing manufacturing);
    Manufacturing updateByProductId(Integer productId, Manufacturing manufacturing);
    void deleteById(Integer id);
    boolean existsByProductId(Integer productId);
    Manufacturing resetUpdated(Integer id);
} 