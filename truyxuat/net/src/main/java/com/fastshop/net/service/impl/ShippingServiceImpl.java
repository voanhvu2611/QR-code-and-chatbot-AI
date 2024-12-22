package com.fastshop.net.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastshop.net.model.Shipping;
import com.fastshop.net.repository.ShippingDAO;
import com.fastshop.net.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingDAO shippingDAO;

    @Override
    public List<Shipping> findAll() {
        return shippingDAO.findAll();
    }

    @Override
    public Shipping findById(Integer id) {
        return shippingDAO.findById(id).orElse(null);
    }

    @Override
    public Shipping save(Shipping shipping) {
        return shippingDAO.save(shipping);
    }

    @Override
    public void deleteById(Integer id) {
        shippingDAO.deleteById(id);
    }

    @Override
    public Shipping findByProductId(Integer productId) {
        return shippingDAO.findByProduct_Id(productId);
    }

    @Override
    public Shipping updateByProductId(Integer productId, Shipping shipping) {
        Shipping existingShipping = shippingDAO.findByProduct_Id(productId);
        if (existingShipping != null) {
            // Giữ lại product và id của existing shipping
            shipping.setId(existingShipping.getId());
            shipping.setProduct(existingShipping.getProduct());
            
            // Cập nhật thông tin khác
            existingShipping.setImporter(shipping.getImporter());
            existingShipping.setDistributor(shipping.getDistributor());
            existingShipping.setImportDate(shipping.getImportDate());
            existingShipping.setStorageInstructions(shipping.getStorageInstructions());
            existingShipping.setShippingMethod(shipping.getShippingMethod());
            existingShipping.setShippingConditions(shipping.getShippingConditions());
            
            return shippingDAO.save(existingShipping);
        }
    return null;
}

    @Override
    public Shipping resetUpdated(Integer id) {
        Shipping shipping = shippingDAO.findById(id).orElse(null);
        if (shipping != null) {
            shipping.setUpdated(false);
            return shippingDAO.save(shipping);
        }
        return shipping;
    }
} 