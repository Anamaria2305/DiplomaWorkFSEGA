package com.example.blooddonation.controller;

import com.example.blooddonation.entity.Appointment;
import com.example.blooddonation.entity.Bloodtests;
import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasDoctorAuthority;
import com.example.blooddonation.roles.HasUserAuthority;
import com.example.blooddonation.service.AppointmentService;
import com.example.blooddonation.service.BloodtestService;
import com.example.blooddonation.service.StaffService;
import com.example.blooddonation.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/bloodtest")
public class BloodtestController {

    @Autowired
    BloodtestService bloodtestService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    UsersService usersService;

    @Autowired
    StaffService staffService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    @HasAdminAuthority
    public List<Bloodtests> getAll() {
        return bloodtestService.getAll();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/idApp")
    @ResponseBody
    @HasUserAuthority
    public Bloodtests getByIdAppointmentUser(@RequestParam(name = "id") Integer id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Appointment app=appointmentService.getById(id);

        if (app.getUsers().getUsername().equals(principal.getUsername()))
        return app.getBloodtests();

        else
        return null;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/idAppD")
    @ResponseBody
    @HasDoctorAuthority
    public Bloodtests getByIdAppointmentDoctor(@RequestParam(name = "id") Integer id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Appointment app=appointmentService.getById(id);
        Staff st=staffService.findByUsername(principal.getUsername());
        if (app.getHospital().getName().equals(st.getHospital().getName()))
            return app.getBloodtests();

        else
            return null;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasDoctorAuthority
    public void saveBloodtest(@RequestBody Bloodtests bloodtests,@RequestParam(name = "id") Integer id) {

        bloodtestService.saveBloodtest(bloodtests,id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ResponseBody
    @HasDoctorAuthority
    public void editBloodtest(@RequestBody Bloodtests bloodtests)  {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Appointment app=appointmentService.getById(bloodtests.getAppointment().getId());
        Staff st=staffService.findByUsername(principal.getUsername());
        if (app.getHospital().getName().equals(st.getHospital().getName()))
        bloodtestService.editBloodtest(bloodtests);
    }
}
