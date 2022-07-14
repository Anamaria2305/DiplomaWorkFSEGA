package com.example.blooddonation.controller;

import com.example.blooddonation.entity.BloodType;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.service.BloodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/bloodtype")
public class BloodTypeController {

    @Autowired
    BloodTypeService bloodTypeService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<BloodType> getAll() {
        return bloodTypeService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    public BloodType getById(@RequestParam(name = "id") Integer id) {

        return bloodTypeService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bloodrh")
    @ResponseBody
    public BloodType getByBloodAndRh(@RequestParam(name = "blood") String blood, @RequestParam(name = "rh") String rh) {
        return bloodTypeService.getByBloodAndRh(blood,rh);
    }
}
