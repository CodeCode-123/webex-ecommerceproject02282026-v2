package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.entity.Item;
import com.code.api.repository.IItemRepository;
import com.code.api.service.IItemService;

@Service
@Transactional
public class ItemServiceImpl implements IItemService {
	@Autowired
	private IItemRepository iItemRepository;

	@Override
	public Item add(Item item) {
		return iItemRepository.save(item);
	}

	@Override
	public Item update(Item item) {
		return iItemRepository.save(item);
	}

	@Override
	public void delete(Item item) {
		iItemRepository.delete(item);
	}

	@Override
	public void deleteById(int id) {
		iItemRepository.deleteById(id);
	}

	@Override
	public List<Item> getAll() {
		return iItemRepository.findAll();
	}

	@Override
	public Optional<Item> getById(int id) {
		return iItemRepository.findById(id);
	}
}
