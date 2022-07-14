package com.example.blooddonation.controller;


import com.example.blooddonation.entity.Appointment;
import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.roles.HasAdminAuthority;
import com.example.blooddonation.roles.HasDoctorAuthority;
import com.example.blooddonation.roles.HasUserAuthority;
import com.example.blooddonation.service.AppointmentService;
import com.example.blooddonation.service.StaffService;
import com.example.blooddonation.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping(value = "/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    StaffService staffService;

    @Autowired
    UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    @HasAdminAuthority
    public List<Appointment> getAll() {
        return appointmentService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id")
    @ResponseBody
    @HasAdminAuthority
    public Appointment getById(@RequestParam(name = "id") Integer id) {

        return appointmentService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    @HasUserAuthority
    public void saveAppointment(@RequestBody Appointment appointment) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Users users=usersService.findByUsername(principal.getUsername());
        appointment.setUsers(users);
        appointmentService.saveAppointment(appointment);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    @HasUserAuthority
    public void deleteAppointment(@RequestParam(name = "id") Integer id ){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Appointment app=appointmentService.getById(id);

        if(app.getUsers().getUsername().equals(principal.getUsername()))
        appointmentService.deleteAppointment(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chconfirmed")
    @ResponseBody
    @HasDoctorAuthority
    public void changeConfirmed(@RequestParam(name = "id") Integer id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Appointment app=appointmentService.getById(id);
        Staff st=staffService.findByUsername(principal.getUsername());
        if(app.getHospital().equals(st.getHospital()))

        appointmentService.changeConfirmed(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unconfirmed")
    @ResponseBody
    @HasDoctorAuthority
    public List<Appointment> getAllUnconfirmed() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        return appointmentService.getAllUnconfirmed().stream().filter(o -> o.getHospital().getName().equals(staff.getHospital().getName())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmed")
    @ResponseBody
    @HasDoctorAuthority
    public List<Appointment> getAllConfirmed() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Staff staff=staffService.findByUsername(principal.getUsername());
        return appointmentService.getAllConfirmed().stream().filter(o -> o.getHospital().getName().equals(staff.getHospital().getName())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byUser")
    @ResponseBody
    @HasUserAuthority
    public List<Appointment> getAllByUsser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return appointmentService.getAll().stream().filter(o -> o.getUsers().getUsername().equals(principal.getUsername())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hours")
    @ResponseBody
    public List<Time> getHours(@RequestParam(name = "date") String data) throws ParseException {
        return appointmentService.availableHours(data);
    }
}
