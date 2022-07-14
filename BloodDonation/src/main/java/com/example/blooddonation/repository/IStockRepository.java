package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockRepository extends JpaRepository<Stock,Integer> {
}
