package com.example.blooddonation.service;


import com.example.blooddonation.entity.Appointment;
import com.example.blooddonation.entity.Bloodtests;
import com.example.blooddonation.repository.IBloodtestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BloodtestService {

    @Autowired
    IBloodtestRepository iBloodtestRepository;

    @Autowired
    AppointmentService appointmentService;


    /**
     *
     * @return  Returns a list with all bloodtests.
     */
    public List<Bloodtests> getAll() {
        return (List<Bloodtests>)
                iBloodtestRepository.findAll();
    }

    /**
     *
     * @param id of the bloodtest we want to find.
     * @return The found bloodtest with that id or null if it does not exist.
     */
    public Bloodtests getById(Integer id) {
        Optional<Bloodtests> bloodtests = iBloodtestRepository.findById(id);
        return bloodtests.orElse(null);
    }

    /**
     *
     * @param bloodtests - the object of type Bloodtests we want to save in the db
     * @return The saved objects if the saving was succesfully, an error status if not.
     */
    public Bloodtests saveBloodtest(Bloodtests bloodtests,Integer id){
        Appointment app= appointmentService.getById(id);
        app.setBloodtests(bloodtests);
        bloodtests.setAppointment(app);
        appointmentService.saveAppointment(app);
        return iBloodtestRepository.save(bloodtests);
    }

    /**
     *
     * @param bloodtests - the object of type Bloodtests we want to edit in the db,including id
     * @return The edited object if it was successful, an error status if not.
     */
    public Bloodtests editBloodtest(Bloodtests bloodtests){
        Bloodtests b1=this.getById(bloodtests.getId());
        b1.setSida(bloodtests.getSida());
        b1.setLeucemie(bloodtests.getLeucemie());
        b1.setHepatitab(bloodtests.getHepatitab());
        b1.setHepatitac(bloodtests.getHepatitac());
        b1.setTrombocite(bloodtests.getTrombocite());
        b1.setHemoglobina(bloodtests.getHemoglobina());
        b1.setColesterol(bloodtests.getColesterol());
        b1.setLeucocite(b1.getLeucocite());
        return iBloodtestRepository.save(b1);
    }
}
