package com.nisum.ejerciciotecnico.mappers;

import com.nisum.ejerciciotecnico.entitie.PhoneEntity;
import com.nisum.ejerciciotecnico.model.PhoneDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneMapper {

    PhoneEntity PhoneDtoToPhone(PhoneDTO dto) ;
    PhoneDTO phoneToPhoneDto(PhoneEntity phoneEntity);

}
