package com.mandiri.services;

import com.mandiri.entities.dtos.RegisterDto;
import com.mandiri.entities.models.Role;
import com.mandiri.entities.models.User;
import com.mandiri.repositories.RoleRepository;
import com.mandiri.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public User saveUser(RegisterDto user) {
        log.info("Saving new user {} to the database", user.getUsername());
        User userSave = new User();
        userSave.setName(user.getName());
        userSave.setUsername(user.getUsername());
        userSave.setEmail(user.getEmail());
        userSave.setNik(user.getNik());
        userSave.setPassword(user.getPassword());
        userSave.setAddress(user.getAddress());

        userRepository.save(userSave);
        addRoleToUser(user.getUsername(), user.getRole());
        return userSave;
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            throw new RuntimeException("Role not found");
        } else {
            user.getRoles().add(role);
        }
    }

    @Override
    public User getUser(String userName) {
        log.info("Fetching user {}", userName);
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(String Id){
        log.info("Fetching user by Id");
        return userRepository.findById(Id).get();
    }

}
