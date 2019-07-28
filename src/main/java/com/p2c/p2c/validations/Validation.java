package com.p2c.p2c.validations;

import com.p2c.p2c.exception.BadRequestException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class Validation
{
    public void isLoggedUserValid(String username, HttpServletRequest request)
    {
        String loggedInUser = request.getUserPrincipal().getName();

        if (!loggedInUser.equals(username)) {
            throw new BadRequestException("Bad Request with username:" + username);
        }
    }
}
