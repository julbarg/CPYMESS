package com.claro.cpymes.services.remote;

import org.springframework.stereotype.Service;

import com.claro.cpymes.dto.UserDTO;


@Service
public interface LoginService {

   public boolean authenticate(UserDTO user) throws Exception;

}
