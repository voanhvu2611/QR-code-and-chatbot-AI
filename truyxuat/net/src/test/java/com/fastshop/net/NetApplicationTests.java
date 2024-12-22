package com.fastshop.net;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.fastshop.net.model.Product;
import com.fastshop.net.service.ProductService;

@SpringBootTest
class NetApplicationTests {
	@Autowired
	ProductService productService;

	@Test
	void contextLoads() {
		List<Product> products = productService.findAll();
		System.out.println(products.size());
	}

}

