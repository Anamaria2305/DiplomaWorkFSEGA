package com.example.blooddonation.roles;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('admin')")
public @interface HasAdminAuthority {

}