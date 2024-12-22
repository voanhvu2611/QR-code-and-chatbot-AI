package com.fastshop.net.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fastshop.net.model.Manufacturing;
import com.fastshop.net.service.ManufacturingService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/products/manufacturing")
public class RestManufacturingController {
    
    @Autowired
    private ManufacturingService manufacturingService;

    // GET - Lấy tất cả thông tin manufacturing
    @GetMapping
    public ResponseEntity<List<Manufacturing>> getAll() {
        return ResponseEntity.ok(manufacturingService.findAll());
    }

    // GET - Lấy manufacturing theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturing> getById(@PathVariable("id") Integer id) {
        Manufacturing manufacturing = manufacturingService.findById(id);
        if (manufacturing != null) {
            return ResponseEntity.ok(manufacturing);
        }
        return ResponseEntity.notFound().build();
    }

    // GET - Lấy manufacturing theo Product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Manufacturing> getByProductId(@PathVariable("productId") Integer productId) {
        Manufacturing manufacturing = manufacturingService.findByProductId(productId);
        if (manufacturing != null) {
            return ResponseEntity.ok(manufacturing);
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Tạo mới manufacturing
    @PostMapping
    public ResponseEntity<Manufacturing> create(@RequestBody Manufacturing manufacturing) {
        try {
            // Kiểm tra xem sản phẩm đã có thông tin manufacturing chưa
            if (manufacturingService.existsByProductId(manufacturing.getProduct().getId())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(manufacturingService.save(manufacturing));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT - Cập nhật manufacturing theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturing> update(
            @PathVariable("id") Integer id,
            @RequestBody Manufacturing manufacturing) {
        if (manufacturingService.findById(id) != null) {
            manufacturing.setId(id);
            manufacturing.setUpdated(true);
            return ResponseEntity.ok(manufacturingService.save(manufacturing));
        }
        return ResponseEntity.notFound().build();
    }

    // PUT - Cập nhật manufacturing theo Product ID
    @PutMapping("/product/{productId}")
    public ResponseEntity<Manufacturing> updateByProductId(
            @PathVariable("productId") Integer productId,
            @RequestBody Manufacturing manufacturing) {
        try {
            Manufacturing updatedManufacturing = 
                manufacturingService.updateByProductId(productId, manufacturing);
            if (updatedManufacturing != null) {
                return ResponseEntity.ok(updatedManufacturing);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/reset/{id}")
    public ResponseEntity<Manufacturing> resetUpdated(@PathVariable("id") Integer id) {
        Manufacturing manufacturing = manufacturingService.resetUpdated(id);
        if (manufacturing != null) {
            return ResponseEntity.ok(manufacturing);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE - Xóa manufacturing theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        try {
            Manufacturing manufacturing = manufacturingService.findById(id);
            if (manufacturing != null) {
                manufacturingService.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // GET - Kiểm tra tồn tại manufacturing theo Product ID
    @GetMapping("/exists/product/{productId}")
    public ResponseEntity<Boolean> existsByProductId(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(manufacturingService.existsByProductId(productId));
    }
}
