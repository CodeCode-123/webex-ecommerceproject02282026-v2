package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.api.entity.Payment;
import com.code.api.repository.IPaymentRepository;
import com.code.api.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Autowired
	private IPaymentRepository iPaymentRepository;

	@Override
	public Payment createPayment(Payment payment) {
		return iPaymentRepository.save(payment);
	}

	@Override
	public Optional<Payment> getPaymentById(int paymentId) {
		return iPaymentRepository.findById(paymentId);
	}

	@Override
	public List<Payment> getAllPayments() {
		return iPaymentRepository.findAll();
	}
}
