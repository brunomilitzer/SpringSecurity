package com.brunomilitzer.springsecurity.security;

import org.springframework.stereotype.Service;

public interface SecurityService {

    Boolean login(String username, String password);
}
