package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Bloodtests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBloodtestRepository extends JpaRepository<Bloodtests,Integer> {
}
