package com.example.blooddonation.controller;

import com.example.blooddonation.entity.Hospital;
import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.service.HospitalService;
import com.example.blooddonation.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    StaffService staffService;


    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<Hospital> getAll() {
        return hospitalService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    @HasAdminAuthority
    public Hospital getById(@RequestParam(name = "id") Integer id) {
        return hospitalService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasAdminAuthority
    public void saveHospital(@RequestBody Hospital hospital) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        staff.setHospital(hospital);
        List<Staff> staffList=new ArrayList<>();
        staffList.add(staff);
        staffService.saveStaff(staff);
        hospitalService.saveHospital(hospital);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    @ResponseBody
    @HasAdminAuthority
    public void editHospital(@RequestParam(name = "id") Integer id,@RequestParam(name = "name") String name,
    @RequestParam(name = "address") String address,@RequestParam(name = "nr") String nr) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        Hospital hospital=hospitalService.getById(id);
        if(staff.getHospital().getId()== hospital.getId())
        hospital.setName(name);
        hospital.setAddress(address);
        hospital.setPhonenumber(nr);
        hospitalService.editHospital(hospital);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    @ResponseBody
    @HasAdminAuthority
    public Hospital getByAdmin() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        return staff.getHospital();
    }
}
