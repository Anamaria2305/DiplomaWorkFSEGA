package com.example.blooddonation.service;

import com.example.blooddonation.entity.Users;
import com.example.blooddonation.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    IUsersRepository iUsersRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     *
     * @return A list with all users from the db.
     */
    public List<Users> getAll() {
        return (List<Users>) iUsersRepository.findAll();
    }

    /**
     *
     * @param id - id of the user we want to find
     * @return The user with the specified id if it exists or else null.
     */
    public Users getById(Integer id) {
        Optional<Users> user = iUsersRepository.findById(id);
        return user.orElse(null);
    }

    /**
     *
     * @param user- this is the user object with all its fields except id
     * @return Returns the saved user if it was possible to save it, else returns error.
     */
    public Users saveUser(Users user){
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return iUsersRepository.save(user);
    }

    /**
     *
     * @param user-this is the user object with all its fields with id
     * @return Returns the edited user if it was possible to save it, else returns error.
     */
    public Users editUser(Users user){
        Users user1=this.getById(user.getId());
        user1.setPassword(bcryptEncoder.encode(user.getPassword()));
        return iUsersRepository.save(user1);
    }

    /**
     *
     * @param username - the username of the staff member we want to find in db
     * @return The user with that username if exists or else null.
     */
    public Users findByUsername(String username) {
        Optional<Users> user = iUsersRepository.findAll().stream().filter(o -> o.getUsername()
                .equals(username)).findFirst();
        return user.orElse(null);
    }
}
