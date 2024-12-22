package com.fastshop.net.service;

import java.util.List;
import com.fastshop.net.model.Shipping;

public interface ShippingService {
    List<Shipping> findAll();
    Shipping findById(Integer id);
    Shipping save(Shipping shipping);
    void deleteById(Integer id);
    
    Shipping findByProductId(Integer productId);
    Shipping updateByProductId(Integer productId, Shipping shipping);
    Shipping resetUpdated(Integer id);
} 