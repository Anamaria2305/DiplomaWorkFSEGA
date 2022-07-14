package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IHospitalsRepository extends JpaRepository<Hospital,Integer> {

    @Query("select h from Hospital h where h.name = ?1")
    Hospital findByName(String name);
}
