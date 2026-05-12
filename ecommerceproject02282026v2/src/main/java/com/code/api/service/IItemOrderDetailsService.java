package com.code.api.service;

import java.util.List;
import java.util.Optional;
import com.code.api.entity.ItemOrderDetails;

public interface IItemOrderDetailsService {
	ItemOrderDetails add(ItemOrderDetails itemOrderDetails);
	ItemOrderDetails update(ItemOrderDetails itemOrderDetails);
	void delete(ItemOrderDetails itemOrderDetails);
	void deleteById(int id);
	List<ItemOrderDetails> getAll();
	Optional<ItemOrderDetails> getById(int id);
	Optional<ItemOrderDetails> getItemOrderDetailsAndItemById(int id);
}
