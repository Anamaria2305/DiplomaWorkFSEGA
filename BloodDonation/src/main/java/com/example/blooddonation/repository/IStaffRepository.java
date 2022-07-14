package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffRepository extends JpaRepository<Staff,Integer> {
}
