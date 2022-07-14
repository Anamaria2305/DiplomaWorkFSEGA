package com.example.blooddonation.service;


import com.example.blooddonation.entity.*;
import com.example.blooddonation.repository.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepository iAppointmentRepository;
    @Autowired
    UsersService usersService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    StockService stockService;

    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    String pattern2 = "HH:mm";
    SimpleDateFormat simpleDateFormattime = new SimpleDateFormat(pattern2);




    public List<Time> availableHours(String data) throws ParseException {
        List<Time> timeList=new ArrayList<>();
        Time startTime = new Time(8, 0,0);
        Time endTime = new Time(13, 0, 0);

        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data);
        List<Appointment> appointmentList=iAppointmentRepository.findAll().stream().filter(o-> o.getApp_date().getYear()==(date1.getYear()) &&
                o.getApp_date().getMonth()==(date1.getMonth()) &&
                o.getApp_date().getDate()==(date1.getDate())).collect(Collectors.toList());

        timeList.add(startTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        while (cal.getTime().before(endTime)) {
            cal.add(Calendar.MINUTE, 20);
            timeList.add(new java.sql.Time(cal.getTimeInMillis()));
        }

        List<Time> removeList=new ArrayList<>();
        for (Appointment ap:appointmentList) {
            for (Time t:timeList) {
                if(ap.getApp_date().getHours()==t.getHours() && ap.getApp_date().getMinutes()==t.getMinutes()){
                    removeList.add(t);
                }
            }
        }
        timeList.removeAll(removeList);
        return timeList;
    }

    /**
     *
     * @return A list containing all the appointments from the db.
     */
    public List<Appointment> getAll() {
        return (List<Appointment>) iAppointmentRepository.findAll();
    }

    /**
     *
     * @param id of the appointment we want to find
     * @return The appointment with the specified id if it exists or else null.
     */
    public Appointment getById(Integer id) {
        Optional<Appointment> appointment = iAppointmentRepository.findById(id);
        return appointment.orElse(null);
    }

    /**
     *
     * @param appointment - this is the appointment with all its fields except id
     * @return Returns the saved appointment if it was possible to save it, else returns null.
     * Also, it send an e-mail with the appointment details to the user who makes it.
     */
    public Appointment saveAppointment(Appointment appointment){

        Users users= usersService.getById(appointment.getUsers().getId());
        Hospital hospital=hospitalService.getById(appointment.getHospital().getId());

        List<Date> datesOfApp=new ArrayList<>();
        List<Appointment> appointmentList=users.getAppointmentList();
        for (Appointment a:appointmentList) {
            datesOfApp.add(a.getApp_date());
        }

        List<Date> sorted = datesOfApp.stream()
                .sorted(Comparator.comparingLong(Date::getTime))
                .collect(Collectors.toList());

        long diffrence= 90;
        if(sorted.size()!=0){
          Date  lastApp=sorted.get(sorted.size()-1);
            Date appDate=appointment.getApp_date();
            long diff = appDate.getTime() - lastApp.getTime();
            TimeUnit time = TimeUnit.DAYS;
            diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
        }
        if(diffrence < 72){
            return null;
        }
        else{
            Integer hours=appointment.getApp_date().getHours()-3;
            SimpleMailMessage mes = new SimpleMailMessage();
            mes.setFrom("anamariaraita@gmail.com");
            mes.setTo(users.getUsername());
            mes.setSubject("Detalii Programare Donare");
            mes.setText("Buna ziua, "+users.getName()+"!\n" +"Va multumim ca ati ales sa salvati o viata!\n\n"+
                    "Va asteptam in data de: "+ simpleDateFormat.format(appointment.getApp_date()) +" la ora " +hours+":"+appointment.getApp_date().getMinutes()  +" la spitalul "
                    +hospital.getName()+".\n\n"+"Adresa acestuia este urmatoarea: "+hospital.getAddress()
                    +".\n\n"+"Pentru informatii suplimentare va rugam sa ne contactati la numarul " +
                    "de telefon: "+hospital.getPhonenumber()+".\n\n"+"Va dorim o zi minunata in continuare!"+"\n\n"+"Echipa BloodDonation.");
            mailSender.send(mes);


            return iAppointmentRepository.save(appointment);
        }

    }

    /**
     *
     * @param id - the id of the appointment we want to delete
     * This method deleted the appointment with the specified id and sends an e-mail with the details
     *      deleted appointment.
     */
    public void deleteAppointment(Integer id){
        Appointment appointment= this.getById(id);
        if(appointment!=null){
            Integer hours=appointment.getApp_date().getHours()-3;
            SimpleMailMessage mes2 = new SimpleMailMessage();
            mes2.setFrom("anamariaraita@gmail.com");
            Users users= usersService.getById(appointment.getUsers().getId());
            mes2.setTo(users.getUsername());
            mes2.setSubject("Detalii Anulare Programare Donare");
            mes2.setText("Buna ziua, "+users.getName()+"!\n\n" +"Va informam ca ati anulat programarea la donare din data de "+simpleDateFormat.format(appointment.getApp_date())
                    +" la ora "+hours+":"+appointment.getApp_date().getMinutes()  +".\n\n"
                    +"Va invitam sa reveniti oricand pe site pentru a adăuga o programare noua.\n\n"+
                    "Va dorim o zi minunata in continuare!"+"\n\n"+"Echipa BloodDonation.");
            mailSender.send(mes2);
        }
        iAppointmentRepository.deleteById(id);
    }

    /**
     *
     * @param id the id of the appointment we want to change status
     *  This method changes status to confirmed of the appointments whose id is given,
     *  also, it increases the stock of the bloodtype of the user in that hospital.
     */
    public void changeConfirmed(Integer id){
        Appointment appointment= this.getById(id);

        Hospital h=appointment.getHospital();
        BloodType b=appointment.getUsers().getBloodtype();
        List <Stock> s= stockService.getAll().stream().filter(o-> o.getHospital().getName().equals(h.getName()) &&
                o.getBloodtype().equals(b)).collect(Collectors.toList());
        s.get(0).setQuantity(s.get(0).getQuantity()+0.45);
        appointment.setConfirmed(1);

        iAppointmentRepository.save(appointment);
    }

    /**
     *
     * @return Returns a list with all unconfirmed appointments.
     */
    public List<Appointment> getAllUnconfirmed() {

        return (List<Appointment>) iAppointmentRepository.findAll().stream().filter(o -> o.getConfirmed() == 0).collect(Collectors.toList());
    }

    /**
     *
     * @return  Returns a list with all confirmed appointments.
     */
    public List<Appointment> getAllConfirmed() {

        return (List<Appointment>) iAppointmentRepository.findAll().stream().filter(o -> o.getConfirmed() == 1).collect(Collectors.toList());
    }
}
