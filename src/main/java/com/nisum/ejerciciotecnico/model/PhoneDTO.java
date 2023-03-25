package com.nisum.ejerciciotecnico.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PhoneDTO {
    private UUID id;
    private String number;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("contrycode")
    private String contryCode;
}
