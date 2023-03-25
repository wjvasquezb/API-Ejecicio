package com.nisum.ejerciciotecnico.service;

import com.nisum.ejerciciotecnico.exceptions.ValidationException;
import com.nisum.ejerciciotecnico.model.UserAuthDTO;
import com.nisum.ejerciciotecnico.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> listUsers();

    UserDTO saveNewUser(UserDTO userDTO) throws ValidationException;

    UserDTO searchUserByToken(String token) throws ValidationException;

    UserDTO searchUserByEmail(String email) throws ValidationException;

    UserDTO updateUserByEmail(String email, UserDTO user);

    UserDTO updateTokenUserByEmail(UserAuthDTO userDTO) throws ValidationException ;

    UserDTO loginAuditor(String token)throws ValidationException;


    UserDTO saveNewUserLogin(UserAuthDTO userDTO)throws ValidationException;





}
