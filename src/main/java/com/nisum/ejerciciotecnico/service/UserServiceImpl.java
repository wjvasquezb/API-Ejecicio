package com.nisum.ejerciciotecnico.service;

import com.nisum.ejerciciotecnico.entitie.PhoneEntity;
import com.nisum.ejerciciotecnico.entitie.UserEntity;
import com.nisum.ejerciciotecnico.exceptions.ValidationException;
import com.nisum.ejerciciotecnico.mappers.PhoneMapper;
import com.nisum.ejerciciotecnico.mappers.UserMapper;
import com.nisum.ejerciciotecnico.model.UserAuthDTO;
import com.nisum.ejerciciotecnico.model.UserDTO;
import com.nisum.ejerciciotecnico.repository.PhoneRepository;
import com.nisum.ejerciciotecnico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userrepository;

    @Autowired
    private UserMapper usermapper;


    @Override
    public List<UserDTO> listUsers() {
        return userrepository.findAll().stream().map(usermapper::userEntityToUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO saveNewUser(UserDTO userDTO) throws ValidationException {

        UserEntity userbyEmail = userrepository.findUserEntitiesByEmail(userDTO.getEmail());
        if (userbyEmail != null) {
            return null;
        }
        UserEntity userEntity = usermapper.userDtoToUserEntity(userDTO);
        userEntity.setIsActive(true);
        if(userEntity.getPhones()!=null) {
            for (PhoneEntity phone : userEntity.getPhones()) {
                phone.setUser(userEntity);
            }
        }
        try {
            UserEntity  save = userrepository.save(userEntity);
    return usermapper.userEntityToUserDto(save);
}catch (Exception e){
 throw  new   ValidationException("Error al Crear el Usuario: "+ e.getMessage());
}
    }

    @Override
    public UserDTO searchUserByToken(String token) throws ValidationException {

        UserEntity userEntitiesByToken = userrepository.findUserEntitiesByToken(token);
        if (userEntitiesByToken==null){
            throw new ValidationException("El Token no se Encuentra Registrado");
        }

        return usermapper.userEntityToUserDto(userEntitiesByToken);
    }



    @Override
    @Transactional
    public UserDTO searchUserByEmail(String email) throws ValidationException {
        UserEntity userEntitiesByEmail = userrepository.findUserEntitiesByEmail(email);
        if (userEntitiesByEmail == null) {
            throw new ValidationException("El Ususario No se Encuentra Registrado");
        }
        return usermapper.userEntityToUserDto(userEntitiesByEmail);
    }

    @Override
    @Transactional
    public UserDTO updateUserByEmail(String email, UserDTO user) {
        UserEntity userEntitiesByEmail = userrepository.findUserEntitiesByEmail(email);

        usermapper.updateUserEntityFromDto(userEntitiesByEmail, user);

        return usermapper.userEntityToUserDto(userrepository.save(userEntitiesByEmail));
    }

    @Override
    @Transactional
    public UserDTO updateTokenUserByEmail(UserAuthDTO userDTO) throws ValidationException {
        UserEntity userEntitiesByEmail = userrepository.findUserEntitiesByEmail(userDTO.getEmail());
        if(userEntitiesByEmail==null){
            throw  new ValidationException("Correo No registrado");

        }else if(userEntitiesByEmail.equals(userDTO.getPassword()))
        {
            throw  new ValidationException("Password  Incorreto");
        }else{

        }
        return usermapper.userEntityToUserDto(userrepository.save(userEntitiesByEmail));
    }

    @Override
    @Transactional
    public UserDTO loginAuditor(String token) throws ValidationException {
        UserDTO userDTO = searchUserByToken(token);
        if(userDTO.getIsActive()) {
            userDTO.setLastLogin(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
            userrepository.save(usermapper.userDtoToUserEntity(userDTO));
        }
        return userDTO;
    }

    @Override
    @Transactional
    public UserDTO saveNewUserLogin(UserAuthDTO userDTO) throws ValidationException {
       try {
           UserEntity userEntity = usermapper.userDtoToUserEntity(usermapper.userAuthToUserDto(userDTO));
           userEntity.setIsActive(true);
           UserEntity userSave = userrepository.save(userEntity);
           return usermapper.userEntityToUserDto(userSave);
       }catch (Exception e ){
           throw new ValidationException("El Usuario no se puedo Registrar " + e.getMessage());
       }



    }


}
