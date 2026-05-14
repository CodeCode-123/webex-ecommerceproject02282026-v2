package com.code.api.dto;

import com.code.api.entity.ItemOrderDetails;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class CartItems{
	private List<ItemOrderDetails> itemOrderDetailsList;
}