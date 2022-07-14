package com.example.blooddonation.service;


import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.repository.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    IStaffRepository iStaffRepository;

    /**
     *
     * @return A list with all staff members from the db.
     */
    public List<Staff> getAll() {
        return (List<Staff>) iStaffRepository.findAll();
    }

    /**
     *
     * @param id of the staff member we want to find
     * @return The staff member with the specified id if it exists or else null.
     */
    public Staff getById(Integer id) {
        Optional<Staff> staff = iStaffRepository.findById(id);
        return staff.orElse(null);
    }

    /**
     *
     * @param staff - this is the staff object with all its fields except id
     * @return Returns the saved staff member if it was possible to save it, else returns error.
     */
    public Staff saveStaff(Staff staff) {
        return iStaffRepository.save(staff);
    }

    /**
     *
     * @param staff- this is the staff object with all its fields with id
     * @return  Returns the edited staff member if it was possible to save it, else returns error.
     */
    public Staff editStaff(Staff staff) {

        return iStaffRepository.save(staff);
    }

    /**
     *
     * @param username - the username of the staff member we want to find in db
     * @return The staff member with that username if exists or else null.
     */
    public Staff findByUsername(String username) {
        Optional<Staff> staff = iStaffRepository.findAll().stream().filter(o -> o.getUsername()
                .equals(username)).findFirst();
        return staff.orElse(null);
    }

}
