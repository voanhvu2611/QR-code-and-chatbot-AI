package com.fastshop.net.controller.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fastshop.net.service.impl.VNPayService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*")
//@org.springframework.stereotype.Controller
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/pay")
    public String home(){
        return "index";
    }

    @GetMapping("/vnpay-payment")
    public String returnPayment(HttpServletRequest request, Model model){
        {
            int paymentStatus = vnPayService.orderReturn(request);

            String orderInfo = request.getParameter("vnp_OrderInfo");
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");

            int totalPriceInt = Integer.parseInt(totalPrice);
            totalPriceInt = totalPriceInt / 100;
            totalPrice = String.valueOf(totalPriceInt);

            model.addAttribute("orderId", orderInfo);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("paymentTime", paymentTime);
            model.addAttribute("transactionId", transactionId);

            if (paymentStatus == 1) {
                return "ordersuccess";
            } else {
                return "orderfail";
            }
        }
    }
}