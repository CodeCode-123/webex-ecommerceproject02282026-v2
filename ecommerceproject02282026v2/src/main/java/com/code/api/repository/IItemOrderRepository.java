package com.code.api.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrder;

@Repository
public interface IItemOrderRepository extends JpaRepository<ItemOrder, Integer>{
	@Query("SELECT i FROM ItemOrder i JOIN FETCH i.itemOrderDetailsList WHERE i.itemOrderId=:data")
	Optional<ItemOrder> findOrderAndItemOrderDetailsById(@Param("data") int id);
	@Query("SELECT i FROM ItemOrder i JOIN FETCH i.payment p WHERE p.razorpayOrderId=:data")
	Optional<ItemOrder> findOrderAndPaymentByRazorpayOrderId(@Param("data") String razorpayOrderId);
}
