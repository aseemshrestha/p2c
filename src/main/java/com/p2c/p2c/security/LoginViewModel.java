package com.p2c.p2c.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginViewModel
{
    private String username;
    private String password;
}
