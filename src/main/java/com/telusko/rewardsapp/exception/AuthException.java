package com.telusko.rewardsapp.exception;

import org.springframework.stereotype.Component;

@Component
public class AuthException extends Exception
{
    public AuthException(String message)
    {
        super(message);
    }
}
