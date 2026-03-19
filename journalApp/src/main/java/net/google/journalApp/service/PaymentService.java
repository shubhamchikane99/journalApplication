package net.google.journalApp.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import net.google.journalApp.entity.Payment;
import net.google.journalApp.entity.UserAccessRole;
import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.PaymentRepository;
import net.google.journalApp.repository.UserAccessRoleRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class PaymentService {

	@Autowired
	private RazorpayClient razorpayClient;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private UserAccessRoleRepository userAccessRoleRepository;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

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
			response.put("key", keyId);

			return response;

		} catch (RazorpayException e) {
			// Log properly in production (use SLF4J / Logback)
			System.err.println("Razorpay order creation failed: " + e.getMessage());
			throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage(), e);
		}
	}

	public Payment verifyPayment(Payment payment) {
		// verify payment

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Users findUserName = userRepository.findByUserName(userName);
		payment.setUserId(findUserName.getId());

		UserAccessRole userAccessRole = new UserAccessRole();
		userAccessRole.setPlanId(payment.getPlanId());
		userAccessRole.setUserId(findUserName.getId());
		userAccessRole.setJson(payment.getAccesJson());
		userAccessRole.setName(payment.getPlanName());

		userAccessRoleRepository.save(userAccessRole);

		return paymentRepository.save(payment);
	}
}
