package com.fastshop.net.controller.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.service.impl.VNPayService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/vnpay")
public class PaymentController {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/payment")
    public ResponseEntity<VNPayMessage> createVNPayUrl(
        @RequestParam("totalPayment") int totalPayment,
        @RequestParam("orderIdsString") String orderIdsString,
        HttpServletRequest request
    ) {
        try {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(totalPayment, orderIdsString, baseUrl)
                                           .replaceAll("\\s+", ""); // Loại bỏ khoảng trắng thừa
            return ResponseEntity.ok(new VNPayMessage("Success", vnpayUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new VNPayMessage("Failed", ""));
        }        
    }
}
