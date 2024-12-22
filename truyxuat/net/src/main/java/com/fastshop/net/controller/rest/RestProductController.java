package com.fastshop.net.controller.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDTO;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.ProductService;

@CrossOrigin("*")
@RestController
public class RestProductController {
    @Autowired
    ProductService productSevice;
    @Autowired
    CategoryService categoryService;

    public static String uploadDir = Paths.get("net", "src", "main", "resources", "static", "dist", "img", "products")
            .toAbsolutePath().toString().replace("\\", "/");
    
    @GetMapping("/rest/products/{id}")
    public Product getProductById(@PathVariable("id") Integer id) {
        return productSevice.findById(id);
    }

    @GetMapping("/rest/products")
    public List<Product> getAll(Model model) {
        return productSevice.findAll();
    }

    @PutMapping("/rest/products/{id}")
    public Product put(@PathVariable("id") Integer id, 
                      @RequestParam(value = "productImage", required = false) MultipartFile fileProductImage,
                      @ModelAttribute("productDTO") ProductDTO productDTO) {
        try {
            Product product = productSevice.findById(id);
            if (product == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm với id: " + id);
            }
            
            // Cập nhật thông tin cơ bản
            product.setName(productDTO.getName());
            product.setCategory(categoryService.findById(productDTO.getCategory()));
            product.setPrice(productDTO.getPrice());
            product.setNumber(productDTO.getNumber());
            product.setAvailable(productDTO.getNumber() > 0);
            
            // Xử lý hình ảnh
            if (fileProductImage != null && !fileProductImage.isEmpty()) {
                String imageUUID = fileProductImage.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
                Files.write(fileNameAndPath, fileProductImage.getBytes());
                product.setImage(imageUUID);
            } else if (productDTO.getImageName() != null && !productDTO.getImageName().isEmpty()) {
                product.setImage(productDTO.getImageName());
            }

            productSevice.save(product);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xử lý file ảnh: " + e.getMessage());
        }
    }

    @PostMapping("/rest/products")
    public Product post(@RequestBody Product product) {
        productSevice.save(product);
        return product;
    }

    @DeleteMapping("/rest/products/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productSevice.deleteById(id);
    }
}
