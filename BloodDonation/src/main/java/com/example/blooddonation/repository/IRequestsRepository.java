package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequestsRepository extends JpaRepository <Requests,Integer> {

}
