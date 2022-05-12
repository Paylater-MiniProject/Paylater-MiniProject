package com.mandiri.services;

import com.mandiri.entities.dtos.RegisterDto;
import com.mandiri.entities.models.Role;
import com.mandiri.entities.models.User;

import java.util.List;

public interface UserService {
    User saveUser(RegisterDto user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String userName);
    List<User> getUsers();
}
