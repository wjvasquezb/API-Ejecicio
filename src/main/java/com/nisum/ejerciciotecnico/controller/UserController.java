package com.nisum.ejerciciotecnico.controller;

import com.nisum.ejerciciotecnico.exceptions.ValidationException;
import com.nisum.ejerciciotecnico.model.UserAuthDTO;
import com.nisum.ejerciciotecnico.model.UserDTO;
import com.nisum.ejerciciotecnico.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

    public static final String USER_PATH="/api/v1/user";
    public static final String USERlogin_PATH="/api/v1/login";
    public static final String USER_PATH_EMAIL=USER_PATH + "/{email}";
    @Autowired
    private UserService userservice;

    @Operation(summary = "Crea el User temporal ", description = "Se Utiliza para crear un usuario temporal y poder usar los demas endPoint", responses = {
            @ApiResponse(responseCode = "200", description = "Operacion Correcta", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "No Autorizado"),
            @ApiResponse(responseCode = "403", description = "Forbidden") }
    )
    @PostMapping(USERlogin_PATH)
    public UserAuthDTO login(@Validated @RequestBody UserAuthDTO userAuthDTO) throws ValidationException {
        String token = getJWTToken(userAuthDTO.getEmail(),60000);
        UserAuthDTO user = UserAuthDTO.builder().name(userAuthDTO.getName()).email(userAuthDTO.getEmail()).password(userAuthDTO.getPassword()).token(token).build();
        userservice.saveNewUserLogin(user);
        return user;

    }

    @Operation(summary = "Actualiza el Token ", description = "Si el Token Expira se puede generar nuevamente", responses = {
            @ApiResponse(responseCode = "200", description = "Operacion Correcta", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "No Autorizado"),
            @ApiResponse(responseCode = "403", description = "Forbidden") }
    )
    @PutMapping(USERlogin_PATH)
    public ResponseEntity<UserDTO> updateToken(@RequestHeader MultiValueMap<String, String> headers,@Validated @RequestBody UserAuthDTO userAuthDTO) throws ValidationException {
        Random random = new Random();
        UserDTO userLogin = userservice.loginAuditor(headers.get("authorization").get(0).replace("Bearer ", ""));

        if(!userLogin.getIsActive()){
            return new ResponseEntity<>(userLogin, HttpStatus.UNAUTHORIZED);
        }
        String token = getJWTToken(userAuthDTO.getEmail()+random.ints(97,122+1),100000);
        userAuthDTO.setToken(token);
        UserDTO userDTO = userservice.updateTokenUserByEmail(userAuthDTO);
        return  new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @Operation(summary = "Crea Usuario Nuevo ", description = "Crea un Ususario Nuevo", responses = {
            @ApiResponse(responseCode = "201", description = "Usuario Creado", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "No Autorizado"),
            @ApiResponse(responseCode = "403", description = "Forbidden") }

    )
@PostMapping(value = USER_PATH)
    public ResponseEntity<UserDTO> createUser(@RequestHeader MultiValueMap<String, String> headers,@Validated @RequestBody UserDTO user) throws ValidationException {
    UserDTO userLogin = userservice.loginAuditor(headers.get("authorization").get(0).replace("Bearer ", ""));
    if(!userLogin.getIsActive()){
        return new ResponseEntity<>(userLogin, HttpStatus.UNAUTHORIZED);
    }
    String token = getJWTToken(user.getEmail(),100000);
    user.setToken(token);
    user.setIsActive(true);
    UserDTO userdto = userservice.saveNewUser(user);
        if(userdto==null){
          throw new ValidationException("El Correo Ya Registrado");
        }
        return new ResponseEntity<>(userdto, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza el Usuario ", description = "Se actualiza los datos del Usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Operacion Correcta", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "No Autorizado"),
            @ApiResponse(responseCode = "403", description = "Forbidden") }
    )
    @PutMapping(value = USER_PATH_EMAIL)
    public ResponseEntity<UserDTO> UpdateUser(@RequestHeader MultiValueMap<String, String> headers,@Parameter(description = "Email del Usuario a actualizar") @Validated @PathVariable("email") String email, @Validated @RequestBody UserDTO user) throws ValidationException {

        UserDTO userLogin = userservice.loginAuditor(headers.get("authorization").get(0).replace("Bearer ", ""));
        if(!userLogin.getIsActive()){
            return new ResponseEntity<>(userLogin, HttpStatus.UNAUTHORIZED);
        }
        UserDTO userdto = userservice.searchUserByEmail(email);
        if(userdto==null){
            return new ResponseEntity<>( user,HttpStatus.NOT_MODIFIED);
        }else {
            user.setId(userdto.getId());
            UserDTO userupdate = userservice.updateUserByEmail(email,user);
            return new ResponseEntity<>(userupdate, HttpStatus.OK);
        }

    }

    @Operation(summary = "Todos los Usuarios ", description = "Retorna una lista de los Usuarios registrado con todos sus datos ", responses = {
            @ApiResponse(responseCode = "200", description = "Operacion Correcta", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "No Autorizado"),
            @ApiResponse(responseCode = "403", description = "Forbidden") }
    )
@GetMapping(value = USER_PATH)
    public ResponseEntity<List<UserDTO>> getAllUser(@RequestHeader MultiValueMap<String, String> headers) throws ValidationException {
    UserDTO userLogin = userservice.loginAuditor(headers.get("authorization").get(0).replace("Bearer ", ""));
    if(!userLogin.getIsActive()){
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(userservice.listUsers(),HttpStatus.OK);
}

    private String getJWTToken(String username,int validityTime) {
        String secretKey = "mySecretKey";

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");


        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return  token;
    }
}
