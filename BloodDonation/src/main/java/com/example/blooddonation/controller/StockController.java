package com.example.blooddonation.controller;


import com.example.blooddonation.entity.Hospital;
import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Stock;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasDoctorAuthority;
import com.example.blooddonation.service.StaffService;
import com.example.blooddonation.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    StockService stockService;
    @Autowired
    StaffService staffService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<Stock> getAll() {
        return stockService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    public Stock getById(@RequestParam(name = "id") Integer id) {

        return stockService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasAdminAuthority
    public void saveStock(@RequestBody Stock stock) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Stock st1=stockService.getById(stock.getId());
        Staff staff=staffService.findByUsername(principal.getUsername());
        if(st1.getHospital().equals(staff.getHospital()));
        stockService.saveStock(stock);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    @ResponseBody
    @HasDoctorAuthority
    public void editStock(@RequestParam(name = "gr") Integer gr,@RequestParam(name = "cuan") Float qt) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        Optional<Stock> st=staff.getHospital().getStockList().stream().filter(o->o.getBloodtype().getId()==gr).findFirst();
        if(st!=null){
            Stock stock=stockService.getById(st.get().getId());
            if(stock.getQuantity()-qt>=0)
            {
                double newqt=stock.getQuantity()-qt;
                stock.setQuantity(newqt);
                stockService.editStock(stock);
            }


        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hospitalid")
    @ResponseBody
    @HasDoctorAuthority
    public List<Stock> getByHospitalId() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        return stockService.getAll().stream().filter(o-> o.getHospital().getId()==staff.getHospital().getId()).collect(Collectors.toList());
    }
}
