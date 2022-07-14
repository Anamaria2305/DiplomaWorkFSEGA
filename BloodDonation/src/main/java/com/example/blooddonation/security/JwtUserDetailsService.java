package com.example.blooddonation.security;

import java.util.HashSet;
import java.util.Set;

import com.example.blooddonation.entity.Staff;
import com.example.blooddonation.entity.Users;
import com.example.blooddonation.service.StaffService;
import com.example.blooddonation.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UsersService userService;
    @Autowired
    StaffService staffService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user1 = userService.findByUsername(username);
        Staff staff=staffService.findByUsername(username);
        Set<GrantedAuthority> authorities =new HashSet<>();
        if (user1 == null && staff==null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else if(user1!=null && staff==null){
            authorities.add(new SimpleGrantedAuthority("user"));
            User user = new User(user1.getUsername(), user1.getPassword(),
                    authorities);
            return user;
        }else{

            if(staff.getRole().equals("admin")){
                authorities.add(new SimpleGrantedAuthority("admin"));
            }
            else{
                authorities.add(new SimpleGrantedAuthority("doctor"));
            }
            User user = new User(staff.getUsername(), staff.getPassword(),
                    authorities);
            return user;
        }
    }
}