package net.google.journalApp.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {

	@Autowired
	private RazorpayClient razorpayClient;

	public Map<String, Object> createOrder(double amount) {
		try {
			// Convert rupees → paise (and make it integer)
			int amountInPaise = (int) Math.round(amount * 100);

			JSONObject options = new JSONObject();
			options.put("amount", amountInPaise);
			options.put("currency", "INR");
			options.put("receipt", "rcpt_" + System.currentTimeMillis());

			Order order = razorpayClient.orders.create(options);

			// Return only what frontend actually needs
			Map<String, Object> response = new HashMap<>();
			response.put("id", order.get("id"));
			response.put("amount", order.get("amount"));
			response.put("currency", order.get("currency"));
			response.put("status", order.get("status")); // optional

			return response;

		} catch (RazorpayException e) {
			// Log properly in production (use SLF4J / Logback)
			System.err.println("Razorpay order creation failed: " + e.getMessage());
			throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage(), e);
		}
	}
}
