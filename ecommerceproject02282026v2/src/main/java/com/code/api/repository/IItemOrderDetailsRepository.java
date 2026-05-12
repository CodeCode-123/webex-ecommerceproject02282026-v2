package com.code.api.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.code.api.entity.ItemOrderDetails;

@Repository
public interface IItemOrderDetailsRepository extends JpaRepository<ItemOrderDetails, Integer>{
	@Query("SELECT i FROM ItemOrderDetails i JOIN FETCH i.item WHERE i.itemOrderDetailsId=:data")
	Optional<ItemOrderDetails> findItemOrderDetailsAndItemById(@Param("data") int id);
}