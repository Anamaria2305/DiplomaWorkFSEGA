package com.example.blooddonation.service;

import com.example.blooddonation.entity.Hospital;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.repository.IHospitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    IHospitalsRepository iHospitalsRepository;

    /**
     *
     * @return A list with all hospitals from the db.
     */
    public List<Hospital> getAll() {
        return (List<Hospital>) iHospitalsRepository.findAll();
    }

    public Hospital getById(Integer id) {
        Optional<Hospital> hospital = iHospitalsRepository.findById(id);
        return hospital.orElse(null);
    }

    /**
     *
     * @param hospital- the object of type Hospital we want to save in the db
     * @return  The saved objects if the saving was succesfully, an error status if not.
     */
    public Hospital saveHospital(Hospital hospital){
        return iHospitalsRepository.save(hospital);
    }

    /**
     *
     * @param hospital - the object of type Hospital we want to edit in the db
     * @return  The saved objects if the editing was successfully, an error status if not.
     */
    public Hospital editHospital(Hospital hospital){

        return iHospitalsRepository.save(hospital);
    }

}
