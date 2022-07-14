package com.example.blooddonation.controller;


import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Stock;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasDoctorAuthority;
import com.example.blooddonation.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/staff")
public class StaffController {

    @Autowired
    StaffService staffService;
    @Autowired
    private PasswordEncoder bcryptEncoder;


    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    @HasAdminAuthority
    public List<Staff> getAll() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());

        return staffService.getAll().stream().filter(o -> o.getHospital().equals(staff.getHospital())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    @HasAdminAuthority
    public Staff getById(@RequestParam(name = "id") Integer id) {

        return staffService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasAdminAuthority
    public void saveStaff(@RequestBody Staff staff) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff2=staffService.findByUsername(principal.getUsername());
        staff.setHospital(staff2.getHospital());
        String pass=staff.getPassword();
        staff.setPassword(bcryptEncoder.encode(pass));
        staffService.saveStaff(staff);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('doctor','admin')")
    public void editStaff(@RequestParam(name = "oldpass") String oldpass,@RequestParam(name = "newpass") String newpass) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        if(bcryptEncoder.matches(oldpass,staff.getPassword())) {
            System.out.println("aici");
            staff.setPassword(bcryptEncoder.encode(newpass));
            staffService.editStaff(staff);
        }
    }
}
