package com.code.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_order")
@Getter
@Setter
public class ItemOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_order_id")
	private int itemOrderId;
	@Column(name="item_order_date")
	private String itemOrderDate;
	@Column(name="total_amount")
	private double totalAmount;
	@Column(name="created_at", updatable=false)
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne
	@JoinColumn(name="users_id")
	private Users users;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="itemOrder", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ItemOrderDetails> itemOrderDetailsList;
	@OneToOne(mappedBy="itemOrder", cascade= {CascadeType.PERSIST, CascadeType.DETACH, 
			CascadeType.MERGE, CascadeType.REFRESH})
	private Payment payment;
}
