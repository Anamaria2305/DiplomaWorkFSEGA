package com.example.blooddonation.controller;



import com.example.blooddonation.entity.Requests;
import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasDoctorAuthority;
import com.example.blooddonation.roles.HasUserAuthority;
import com.example.blooddonation.service.RequestsService;
import com.example.blooddonation.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/requests")
public class RequestsController {

    @Autowired
    RequestsService requestsService;

    @Autowired
    StaffService staffService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('admin','user')")
    public List<Requests> getAll() {
        return requestsService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    @HasAdminAuthority
    public Requests getById(@RequestParam(name = "id") Integer id) {
        return requestsService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasDoctorAuthority
    public void saveRequest(@RequestBody Requests requests) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff= staffService.findByUsername(principal.getUsername());
        requests.setStaff(staff);
        requestsService.saveRequest(requests);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ResponseBody
    @HasDoctorAuthority
    public void editRequest(@RequestBody Requests requests) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Requests req=requestsService.getById(requests.getId());
        if(req.getStaff().getUsername().equals(principal.getUsername()))
        requestsService.editRequest(requests);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    @HasDoctorAuthority
    public void deleteRequest(@RequestParam(name = "id") Integer id ){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Requests req=requestsService.getById(id);
        if(req.getStaff().getUsername().equals(principal.getUsername()))
        requestsService.deleteRequest(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chconfirmed")
    @ResponseBody
    @HasDoctorAuthority
    public void changeConfirmed(@RequestParam(name = "id") Integer id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Requests req=requestsService.getById(id);
        Staff st=staffService.findByUsername(principal.getUsername());
        if(req.getStaff().getHospital().equals(st.getHospital()))
        requestsService.changeConfirmed(id);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/unconfirmed")
    @ResponseBody
    @HasDoctorAuthority
    public List<Requests> getAllUnconfirmed() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        return requestsService.getAllUnconfirmed().stream().filter(o -> o.getStaff().getHospital().getName().equals(staff.getHospital().getName())).collect(Collectors.toList());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/unconfirmedU")
    @ResponseBody
    public List<Requests> getAllUnconfirmedU() {
        return requestsService.getAllUnconfirmed();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmed")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('doctor','user')")
    public List<Requests> getAllConfirmed() {
        return requestsService.getAllConfirmed();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/blood")
    @ResponseBody
    @HasUserAuthority
    public List<Requests> getAllByGroup(@RequestParam(name = "group") String group) {
        return requestsService.getAll().stream().filter(o -> o.getBloodtype().getBlood().equals(group)).collect(Collectors.toList());
    }



}
