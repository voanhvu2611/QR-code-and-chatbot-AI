package com.fastshop.net.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastshop.net.model.Manufacturing;
import com.fastshop.net.repository.ManufacturingDAO;
import com.fastshop.net.service.ManufacturingService;

@Service
public class ManufacturingServiceImpl implements ManufacturingService {
    @Autowired
    private ManufacturingDAO manufacturingDAO;

    @Override
    public List<Manufacturing> findAll() {
        return manufacturingDAO.findAll();
    }

    @Override
    public Manufacturing findById(Integer id) {
        return manufacturingDAO.findById(id).orElse(null);
    }

    @Override
    public Manufacturing findByProductId(Integer productId) {
        return manufacturingDAO.findByProduct_Id(productId);
    }

    @Override
    public Manufacturing save(Manufacturing manufacturing) {
        return manufacturingDAO.save(manufacturing);
    }

    @Override
    public Manufacturing updateByProductId(Integer productId, Manufacturing manufacturing) {
        Manufacturing existingManufacturing = manufacturingDAO.findByProduct_Id(productId);
        if (existingManufacturing != null) {
            // Giữ lại ID và Product
            manufacturing.setId(existingManufacturing.getId());
            manufacturing.setProduct(existingManufacturing.getProduct());
            
            // Cập nhật thông tin
            existingManufacturing.setCountry(manufacturing.getCountry());
            existingManufacturing.setManufacturer(manufacturing.getManufacturer());
            existingManufacturing.setAddress(manufacturing.getAddress());
            existingManufacturing.setManufacturingDate(manufacturing.getManufacturingDate());
            existingManufacturing.setCertificationNumber(manufacturing.getCertificationNumber());
            existingManufacturing.setQualityStandards(manufacturing.getQualityStandards());
            
            return manufacturingDAO.save(existingManufacturing);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        manufacturingDAO.deleteById(id);
    }

    @Override
    public boolean existsByProductId(Integer productId) {
        return manufacturingDAO.existsByProduct_Id(productId);
    }

    @Override
    public Manufacturing resetUpdated(Integer id) {
        Manufacturing manufacturing = manufacturingDAO.findById(id).orElse(null);
        if (manufacturing != null) {
            manufacturing.setUpdated(false);
            return manufacturingDAO.save(manufacturing);
        }
        return manufacturing;
    }
} 