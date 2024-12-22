package com.fastshop.net.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastshop.net.model.Category;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Manufacturing;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.Shipping;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.ManufacturingDAO;
import com.fastshop.net.repository.ProductDAO;
import com.fastshop.net.repository.ShippingDAO;
import com.fastshop.net.service.FileUploadService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.utils.QrCodeGenerator;
import com.google.zxing.WriterException;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ManufacturingDAO manufacturingDAO;
    @Autowired
    ShippingDAO shippingDAO;
    @Autowired
    FileUploadService fileUploadService;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id).get();
    }

    @Override
    public List<Product> findByCategoryId(String cid) {
        Category category = categoryDAO.findById(cid).get();
        return productDAO.findByCategory(category);
    }

    @Override
    public void save(Product product) {
        if (product.getManufacturing() == null) {
            Manufacturing defaultManufacturing = new Manufacturing();
            defaultManufacturing.setCountry("Việt Nam");
            defaultManufacturing.setManufacturer("FPT");
            defaultManufacturing.setManufacturingDate(new Date());
            Manufacturing savedManufacturing = manufacturingDAO.save(defaultManufacturing);
            product.setManufacturing(savedManufacturing);
        }

        if (product.getShipping() == null) {
            Shipping defaultShipping = new Shipping();
            defaultShipping.setImporter("FPT");
            defaultShipping.setDistributor("FPT");
            defaultShipping.setImportDate(new Date());
            Shipping savedShipping = shippingDAO.save(defaultShipping);
            product.setShipping(savedShipping);
        }

        Product savedProduct = productDAO.save(product);
        try {
            String qrContent = createQrContent(savedProduct);
            MultipartFile qrCodeFile = QrCodeGenerator.generateQRCode(qrContent);
            String qrCodePath = fileUploadService.uploadFile(qrCodeFile).getFileCode();
            savedProduct.setQrCode(qrCodePath);
            productDAO.save(savedProduct);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public List<Product> findByKeywordName(String kw) {
        return productDAO.findByKeywordName(kw);
    }

    @Override
    public List<Product> findByFilter(Integer rate, String cateId, double priceFrom, double priceTo) {
        List<Product> list1 = productDAO.findByFilter(cateId, priceFrom, priceTo);
        if (rate == 0) {
            if (cateId.isEmpty()) {
                return productDAO.findAll()
                                 .stream()
                                 .filter(p -> p.getPrice() >= priceFrom && p.getPrice() <= priceTo)
                                 .collect(Collectors.toList());
            }
            return list1.stream().filter(item -> item.getCategory().getId().equals(cateId))
                                 .collect(Collectors.toList());
        }
        else {
            List<Product> list2 = new ArrayList<>();
            for (Product product : list1) {
                if (product.getComments().size() > 0) {
                    for (Comment c : product.getComments()) {
                        if (c.getRate() <= rate && list2.contains(product) == false) {
                            list2.add(product);
                        }
                    }
                }
            }
            return list2;
        }
    }

    private String createQrContent(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> productInfo = new HashMap<>();
            // productInfo.put("productId", product.getId());

            return objectMapper.writeValueAsString("http://localhost:8080/rest/products/preview/" + product.getId());
        } catch (IOException e) {
            throw new RuntimeException("Error creating QR content", e);
        }
    }
}
