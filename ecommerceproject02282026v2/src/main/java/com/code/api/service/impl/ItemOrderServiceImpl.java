package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.entity.ItemOrder;
import com.code.api.repository.IItemOrderRepository;
import com.code.api.service.IItemOrderService;

@Service
@Transactional
public class ItemOrderServiceImpl implements IItemOrderService {
	@Autowired
	private IItemOrderRepository iItemOrderRepository;

	@Override
	public ItemOrder add(ItemOrder itemOrder) {
		return iItemOrderRepository.save(itemOrder);
	}

	@Override
	public ItemOrder update(ItemOrder itemOrder) {
		return iItemOrderRepository.save(itemOrder);
	}

	@Override
	public void delete(ItemOrder itemOrder) {
		iItemOrderRepository.delete(itemOrder);
	}

	@Override
	public void deleteById(int id) {
		iItemOrderRepository.deleteById(id);
	}

	@Override
	public List<ItemOrder> getAll() {
		return iItemOrderRepository.findAll();
	}

	@Override
	public Optional<ItemOrder> getById(int id) {
		return iItemOrderRepository.findById(id);
	}

	@Override
	public Optional<ItemOrder> getOrderAndItemOrderDetailsById(int orderId) {
		return iItemOrderRepository.findOrderAndItemOrderDetailsById(orderId);
	}

	@Override
	public Optional<ItemOrder> getOrderAndPaymentByRazorpayOrderId(String razorpayOrderId) {
		return iItemOrderRepository.findOrderAndPaymentByRazorpayOrderId(razorpayOrderId);
	}
}
