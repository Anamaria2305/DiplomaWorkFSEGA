package com.example.blooddonation.controller;

import com.example.blooddonation.entity.Users;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasUserAuthority;
import com.example.blooddonation.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    UsersService usersService;


    @HasAdminAuthority
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<Users> getAll() {
        return usersService.getAll();
    }

    @HasAdminAuthority
    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    public Users getById(@RequestParam(name = "id") Integer id) {
        return usersService.getById(id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    public void saveUser(@RequestBody Users user) throws IOException {
        usersService.saveUser(user);
    }


    @HasUserAuthority
    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ResponseBody
    public void editUser(@RequestBody Users user) throws IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal.getUsername().equals(user.getUsername()))
        usersService.editUser(user);

    }


}
