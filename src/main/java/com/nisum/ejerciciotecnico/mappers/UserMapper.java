package com.nisum.ejerciciotecnico.mappers;


import com.nisum.ejerciciotecnico.entitie.UserEntity;
import com.nisum.ejerciciotecnico.model.UserAuthDTO;
import com.nisum.ejerciciotecnico.model.UserDTO;
import org.mapstruct.*;


@Mapper( nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserEntity userDtoToUserEntity(UserDTO dto);
    UserDTO userEntityToUserDto(UserEntity user);

    void updateUserEntityFromDto(@MappingTarget UserEntity user,UserDTO dto);

    UserDTO userAuthToUserDto(UserAuthDTO userAuthDTO);


}
