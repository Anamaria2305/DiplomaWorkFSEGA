package com.example.blooddonation.service;

import com.example.blooddonation.entity.BloodType;
import com.example.blooddonation.repository.IBloodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodTypeService {
    @Autowired
    IBloodTypeRepository iBloodTypeRepository;

    /**
     *
     * @return A list with all bloodtypes from the db.
     */
    public List<BloodType> getAll() {
        return (List<BloodType>) iBloodTypeRepository.findAll();
    }
    public BloodType getById(Integer id) {
        Optional<BloodType> bloodType = iBloodTypeRepository.findById(id);
        return bloodType.orElse(null);
    }

    /**
     *
     * @param blood- a string representing the blood group (A,B,AB and O).
     * @param rh - a string representing the rh (negativ or pozitiv)
     * @return The found bloodtype with the specified prameteres or null if it does not exist.
     */
    public BloodType getByBloodAndRh(String blood,String rh){
        Optional<BloodType> bloodType = Optional.ofNullable(iBloodTypeRepository.findByBloodAndRH(blood, rh));
        return bloodType.orElse(null);
    }

}
