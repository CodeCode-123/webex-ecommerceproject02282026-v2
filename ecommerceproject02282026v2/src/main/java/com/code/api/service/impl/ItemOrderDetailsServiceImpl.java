package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.entity.ItemOrderDetails;
import com.code.api.repository.IItemOrderDetailsRepository;
import com.code.api.service.IItemOrderDetailsService;

@Service
@Transactional
public class ItemOrderDetailsServiceImpl implements IItemOrderDetailsService {
	@Autowired
	private IItemOrderDetailsRepository iItemOrderDetailsRepository;

	@Override
	public ItemOrderDetails add(ItemOrderDetails itemOrderDetails) {
		return iItemOrderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public ItemOrderDetails update(ItemOrderDetails itemOrderDetails) {
		return iItemOrderDetailsRepository.save(itemOrderDetails);
	}

	@Override
	public void delete(ItemOrderDetails itemOrderDetails) {
		iItemOrderDetailsRepository.delete(itemOrderDetails);
	}

	@Override
	public void deleteById(int id) {
		iItemOrderDetailsRepository.deleteById(id);
	}

	@Override
	public List<ItemOrderDetails> getAll() {
		return iItemOrderDetailsRepository.findAll();
	}

	@Override
	public Optional<ItemOrderDetails> getById(int id) {
		return iItemOrderDetailsRepository.findById(id);
	}

	@Override
	public Optional<ItemOrderDetails> getItemOrderDetailsAndItemById(int id) {
		return iItemOrderDetailsRepository.findItemOrderDetailsAndItemById(id);
	}
}
