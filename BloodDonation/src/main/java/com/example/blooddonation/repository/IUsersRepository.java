package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<Users,Integer> {
}
