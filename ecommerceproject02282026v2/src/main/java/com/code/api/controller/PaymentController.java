package com.code.api.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.service.IItemOrderDetailsService;
import com.code.api.service.IItemOrderService;
import com.code.api.service.IPaymentService;
import com.code.api.service.IUsersService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.validation.Valid;

import com.code.api.dto.ItemOrderRequestDto;
import com.code.api.dto.SignatureTestDto;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Payment;
import com.code.api.entity.Users;
import com.code.api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Value("${razorpay.key.id}")
	private String keyId;
	@Value("${razorpay.key.secret}")
	private String keySecret;
	@Autowired
	private IItemOrderDetailsService iItemOrderDetailsService;
	@Autowired
	private IUsersService iUsersService;
	@Autowired
	private IPaymentService iPaymentService;
	@Autowired
	private IItemOrderService iItemOrderService;
	
	@GetMapping("/")
	public List<Payment> getAllPayments() {
		return iPaymentService.getAllPayments();
	}
	
	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable("id") int id) {
		Optional<Payment> payment = iPaymentService.getPaymentById(id);
		if (payment.isEmpty()) {
			throw new ResourceNotFoundException("Payment", "paymentId", String.valueOf(id));
		}
		return payment.get();
	}
	
	@GetMapping("/test-signature")
	public String testSignature(@RequestBody SignatureTestDto signatureTestDto) 
			throws InvalidKeyException, NoSuchAlgorithmException {
		String orderId = signatureTestDto.getOrderId();
		String paymentId = signatureTestDto.getPaymentId();
		return generateSignature(orderId, paymentId, keySecret);
	}
	
	@PostMapping("/createorder")
	public String create(@Valid @RequestBody ItemOrderRequestDto itemOrderRequestDto) 
			throws RazorpayException {
		//create RazorpayClient object using keyId and keySecret from the Razorpay account
		RazorpayClient client = new RazorpayClient(keyId, keySecret);
		//create a JSONObject
		JSONObject options = new JSONObject();
		//create a users object and get users information from the dto
		//if tempUsers is found in the database, set users to be tempUsers.get()
		Users users = null;
		if (itemOrderRequestDto.getUsers() != null 
				&& itemOrderRequestDto.getUsers().getUsersId() > 0) {
			int userId = itemOrderRequestDto.getUsers().getUsersId();
			Optional<Users> tempUsers = iUsersService.getById(userId);
			if (!tempUsers.isEmpty()) {
				users = tempUsers.get();
			}
		}
		//create a new ItemOrder object
		ItemOrder itemOrder = null;
		//if get itemOrder from dto, set itemOrder to be the order
		//otherwise, create a new itemOrder
		int itemOrderId = itemOrderRequestDto.getItemOrderId();
		double total = 0;
		//set the tempItemOrderDetailsList
		List<ItemOrderDetails> tempItemOrderDetailsList = null;
		//check if itemOrder can be found in the database, if found, set itemOrder to be dbItemOrder.get()
		if (itemOrderId > 0) {
			Optional<ItemOrder> dbItemOrder = iItemOrderService.getById(itemOrderId);
			if (!dbItemOrder.isEmpty()) {
				itemOrder = dbItemOrder.get();
			    if (itemOrder.getItemOrderDetailsList() != null && 
			    		itemOrder.getItemOrderDetailsList().size() > 0) {
			    	tempItemOrderDetailsList = itemOrder.getItemOrderDetailsList();
			    }
			} else {
				throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(itemOrderId));
			}
		}
		//if the dto contains the itemOrderDetailsList, set the itemOrderDetailsList
		if (itemOrderRequestDto.getCartItems() != null 
				&& itemOrderRequestDto.getCartItems().getItemOrderDetailsList() != null
				&& itemOrderRequestDto.getCartItems().getItemOrderDetailsList().size() > 0) {
			tempItemOrderDetailsList = itemOrderRequestDto.getCartItems().getItemOrderDetailsList(); 
		}		
		int tempId = 0;
		Optional<ItemOrderDetails> dbDetails;
		int tempQty = 0;
		double tempPrice = 0;
		ItemOrderDetails tempDetails;
		List<ItemOrderDetails> itemOrderDetailsList = new ArrayList<>(); 
		//each itemOrderDetails in the dto will be retrieved from the database by id
		//if found, add to the list, if not found, throw exception, 
		//prevent incorrect Postman requests if the records are different from the database
		for (ItemOrderDetails details: tempItemOrderDetailsList) {
			//get tempId from the dto
			tempId = details.getItemOrderDetailsId();
			//retrieve from the database by using tempId, if not found, throw exception
			dbDetails = iItemOrderDetailsService.getById(tempId);
			if (dbDetails.isEmpty()) {
				throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(tempId));
			}
			//set tempDetails as the dbDetails.get(), set other attributes
			tempDetails = dbDetails.get();
			tempDetails.setItemOrder(itemOrder);
			//add to the list
			itemOrderDetailsList.add(tempDetails);
			//get qty and itemPrice to calculate amount and total
			tempQty = dbDetails.get().getQty();
			tempPrice = dbDetails.get().getItem().getItemPrice();
			total += tempPrice * tempQty;
			//System.out.println("qty: " + tempQty);
			//System.out.println("price: " + tempPrice);
			//System.out.println("total: " + total);
		}
		//if itemOrder == null, set itemOrder
		if (itemOrder == null) {
			itemOrder = new ItemOrder();
			itemOrder.setItemOrderDetailsList(itemOrderDetailsList);
			itemOrder.setTotalAmount(total);
			LocalDateTime nowTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
			String formattedDate = nowTime.format(formatter);
			itemOrder.setItemOrderDate(formattedDate);
		}
		//if users != null set users
		if (users != null) {
			itemOrder.setUsers(users);
		}
		//create options of the JSONObject, amount, currency, and receipt
		options.put("amount", (int)(total * 100));
		//System.out.println("Amount: " + options.getInt("amount"));
		options.put("currency", "USD");
		options.put("receipt", "txn_" + System.currentTimeMillis());
		com.razorpay.Order razorOrder = client.orders.create(options);
		//create the payment and set the attributes
		Payment payment = new Payment();
		payment.setAmount(total);
		payment.setRazorpayOrderId(razorOrder.get("id"));
		payment.setRazorpayPaymentId(razorOrder.get("receipt"));
		payment.setStatus(razorOrder.get("status"));
		payment.setItemOrder(itemOrder);
		//create the razorpay and save to the database
		iPaymentService.createPayment(payment);
		return razorOrder.toString();
	}
	
	@PostMapping("/confirmpayment")
	public ResponseEntity<String> confirmPayment(@RequestBody Map<String, String> data) {
		String secret = keySecret;
		String orderId = data.get("razorpay_order_id");
		String paymentId = data.get("razorpay_payment_id");
		String signature = data.get("razorpay_signature");
		try {
			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", orderId);
			options.put("razorpay_payment_id", paymentId);
			options.put("razorpay_signature", signature);
			boolean isValid = Utils.verifyPaymentSignature(options, secret);
			if (isValid) {
				return ResponseEntity.ok("Payment Successful");
			}
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification Failed");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
	}

	private static String generateSignature(String orderId, String paymentId, String secret) 
			throws NoSuchAlgorithmException, InvalidKeyException {
		String payload = orderId + "|" + paymentId;
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		mac.init(secretKey);
		byte[] hash = mac.doFinal(payload.getBytes());
		StringBuilder hex = new StringBuilder();
		for (byte b: hash) {
			String s = Integer.toHexString(0xff & b);
			if (s.length() == 1) {
				hex.append('0');
			}
			hex.append(s);
		}
		return hex.toString();
	}
}
