package com.example.blooddonation.repository;

import com.example.blooddonation.entity.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBloodTypeRepository extends JpaRepository<BloodType,Integer> {

    @Query("select b from BloodType b where b.blood = ?1 and b.rh = ?2")
    BloodType findByBloodAndRH(String blood, String rh);

}
