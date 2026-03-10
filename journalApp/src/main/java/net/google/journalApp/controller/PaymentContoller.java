package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayClient;

import net.google.journalApp.entity.Payment;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.PaymentService;

@RestController
@RequestMapping("v1/payment")
public class PaymentContoller {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/test-payment")
	public String testPayment() throws Exception {

		RazorpayClient client = new RazorpayClient("keyId", "keySecret");

		return "Razorpay Connected Successfully";
	}

	@GetMapping("/create-order")
	public ServiceResponse createOrder(@RequestParam("amount") double amount) {

		return ServiceResponse.asSuccess(paymentService.createOrder(amount));

	}

	@PostMapping("/verify-payment")
	public ServiceResponse verifyPayment(@RequestBody Payment payment) {

		return ServiceResponse.asSuccess(paymentService.verifyPayment(payment));

	}

}
