package com.example.blooddonation.repository;

import com.example.blooddonation.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment,Integer> {
}
