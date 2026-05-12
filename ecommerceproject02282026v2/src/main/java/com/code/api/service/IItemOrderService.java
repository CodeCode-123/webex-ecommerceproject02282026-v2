package com.code.api.service;

import java.util.List;
import java.util.Optional;
import com.code.api.entity.ItemOrder;

public interface IItemOrderService {
	ItemOrder add(ItemOrder itemOrder);
	ItemOrder update(ItemOrder itemOrder);
	void delete(ItemOrder itemOrder);
	void deleteById(int id);
	List<ItemOrder> getAll();
	Optional<ItemOrder> getById(int id);
	Optional<ItemOrder> getOrderAndItemOrderDetailsById(int orderId);
}
