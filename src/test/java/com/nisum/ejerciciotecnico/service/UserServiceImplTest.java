package com.nisum.ejerciciotecnico.service;

import com.nisum.ejerciciotecnico.entitie.UserEntity;
import com.nisum.ejerciciotecnico.exceptions.ValidationException;
import com.nisum.ejerciciotecnico.model.PhoneDTO;
import com.nisum.ejerciciotecnico.model.UserAuthDTO;
import com.nisum.ejerciciotecnico.model.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
private String token=null;
    private   UserDTO userDTO;
    private UserAuthDTO userAuthDTO;
    private List<UserDTO> userDTOS=new ArrayList<>();
    Random random= new Random();
@Autowired
private UserService userService;
    @BeforeEach
    void setUp() throws ValidationException {

         userAuthDTO= UserAuthDTO.builder().email("email@email.com").password("12qwaszxC").name("name").build();

        PhoneDTO phoneone = PhoneDTO.builder().number("1234565").cityCode("12").contryCode("574").build();
        PhoneDTO phonetwo = PhoneDTO.builder().number("789454").cityCode("13").contryCode("534").build();
        Set<PhoneDTO> phones = new HashSet<>();
        phones.add(phoneone);
        phones.add(phonetwo);
        token=getJWTToken(random.ints(97,122+1).limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString(),90000);
         userDTO=UserDTO.builder().email("emails@email.com").password("222qwaszxC").name("names").token(token).build();
        userDTOS.add(userService.saveNewUser(userDTO));
    }





    @Test
    @Transactional
    void saveNewUser() throws ValidationException {
        PhoneDTO phoneone = PhoneDTO.builder().number("123456").cityCode("1").contryCode("57").build();
        PhoneDTO phonetwo = PhoneDTO.builder().number("78945").cityCode("1").contryCode("53").build();
        Set<PhoneDTO> phones = new HashSet<>();
        phones.add(phoneone);
        phones.add(phonetwo);
        UserDTO userDTO=UserDTO.builder().email("email@email.com").password("22qwaszxC").name("name").phones(phones).build();
        UserDTO user = userService.saveNewUser(userDTO);
        assertEquals(userDTO.getName(),user.getName());
        assertEquals(userDTO.getEmail(),user.getEmail());
    }

    @Test
    @Transactional
    void searchUserByToken() throws ValidationException {
        UserDTO user = userService.searchUserByToken(token);
        assertNotNull(user);
    }

    @Test
    @Transactional
    void searchUserByEmail() throws ValidationException {
        UserDTO user = userService.searchUserByEmail(userDTO.getEmail());
        assertNotNull(user);
    }

    @Test
    @Transactional
    void updateUserByEmail() {
        userDTO.setName("nameDemo");
        UserDTO user = userService.updateUserByEmail(userDTO.getEmail(), userDTO);
       assertEquals(userDTO.getName(),user.getName());
    }

    @Test
    @Transactional
    void updateTokenUserByEmail() throws ValidationException {

        userService.saveNewUserLogin(userAuthDTO);
        String tokenTwo=getJWTToken(random.ints(97,122+1).limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString(),90000);
        userAuthDTO.setToken(tokenTwo);
        UserDTO user = userService.updateTokenUserByEmail(userAuthDTO);
        assertNotEquals(token,user.getToken());
    }
    @Test
    @Transactional
    void updateTokenUserByEmailFail() throws ValidationException {
        UserAuthDTO userAuth= UserAuthDTO.builder().email("email2@email.com").password("122qwaszxC").name("name").build();
        String tokenTwo=getJWTToken(random.ints(97,122+1).limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString(),90000);
        userAuth.setToken(tokenTwo);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> userService.updateTokenUserByEmail(userAuth),
                "Correo No registrado"
        );
        assertTrue(thrown.getMessage().contentEquals("Correo No registrado"));

    }

    @Test
    @Transactional
    void loginAuditor() throws ValidationException {
        UserDTO user = userService.loginAuditor(token);
        assertNotNull(user);
    }
    @Test
    @Transactional
    void loginAuditorFail() throws ValidationException {
        String tokenTwo=getJWTToken(random.ints(97,122+1).limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString(),90000);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> userService.loginAuditor(tokenTwo),
                "El Token no se Encuentra Registrado"
        );
        assertTrue(thrown.getMessage().contentEquals("El Token no se Encuentra Registrado"));

    }

    @Test
    @Transactional
    void saveNewUserLogin() throws ValidationException {
        UserAuthDTO  userAuth= UserAuthDTO.builder().email("email@email.com").password("12qwaszxC").name("name").build();

        UserDTO user = userService.saveNewUserLogin(userAuth);
        assertNotNull(user);
        assertEquals(user.getName(),userAuth.getName());

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