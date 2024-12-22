package com.fastshop.net.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fastshop.net.model.Shipping;
import com.fastshop.net.service.ShippingService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class RestShippingController {
    
    @Autowired
    private ShippingService shippingService;

    // GET - Lấy tất cả thông tin shipping
    @GetMapping
    public ResponseEntity<List<Shipping>> getAll() {
        return ResponseEntity.ok(shippingService.findAll());
    }

    // GET - Lấy shipping theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getById(@PathVariable("id") Integer id) {
        Shipping shipping = shippingService.findById(id);
        if (shipping != null) {
            return ResponseEntity.ok(shipping);
        }
        return ResponseEntity.notFound().build();
    }

    // GET - Lấy shipping theo Product ID
    @GetMapping("/rest/products/shipping/{productId}")
    public ResponseEntity<Shipping> getByProductId(@PathVariable("productId") Integer productId) {
        Shipping shipping = shippingService.findByProductId(productId);
        if (shipping != null) {
            return ResponseEntity.ok(shipping);
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Tạo mới shipping
    @PostMapping
    public ResponseEntity<Shipping> create(@RequestBody Shipping shipping) {
        try {
            return ResponseEntity.ok(shippingService.save(shipping));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT - Cập nhật shipping theo ID
    @PutMapping("/rest/products/shipping/{id}")
    public ResponseEntity<Shipping> update(@PathVariable("id") Integer id, @RequestBody Shipping shipping) {
        if (shippingService.findById(id) != null) {
            shipping.setId(id);
            shipping.setUpdated(true);
            return ResponseEntity.ok(shippingService.save(shipping));
        }
        return ResponseEntity.notFound().build();
    }

    // PUT - Cập nhật shipping theo Product ID
    @PutMapping("/rest/products/update/shipping/{productId}")
    public ResponseEntity<Shipping> updateByProductId(
            @PathVariable("productId") Integer productId,
            @RequestBody Shipping shipping) {
        try {
            Shipping updatedShipping = shippingService.updateByProductId(productId, shipping);
            if (updatedShipping != null) {
                return ResponseEntity.ok(updatedShipping);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT - Reset updated flag
    @PutMapping("/rest/products/reset/shipping/{id}")
    public ResponseEntity<Shipping> resetUpdated(@PathVariable("id") Integer id) {
        Shipping shipping = shippingService.resetUpdated(id);
        if (shipping != null) {
            return ResponseEntity.ok(shipping);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE - Xóa shipping theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (shippingService.findById(id) != null) {
            shippingService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
