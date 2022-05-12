package com.mandiri.entities.dtos;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String username;
    private String password;
    private String email;
    private String nik;
    private String role;
    private String address;
}
