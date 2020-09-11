package br.com.felipedantas.salesapi.salesapi.api.controller;

import br.com.felipedantas.salesapi.salesapi.api.dto.AccountCredentialsDTO;
import br.com.felipedantas.salesapi.salesapi.model.entity.User;
import br.com.felipedantas.salesapi.salesapi.security.jwt.JwtTokenProvider;
import br.com.felipedantas.salesapi.salesapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping( value = "/signin", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    @ApiOperation("SIGN IN")
    public ResponseEntity signIn( @RequestBody AccountCredentialsDTO accountCredentialsDTO ){
        try {
            String username = accountCredentialsDTO.getUsername();
            String password = accountCredentialsDTO.getPassword();
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );
            User user = userService.getByUsername( username );
            String token = "";
            if( user != null ){
                token = jwtTokenProvider.createToken( username, user.getRoles() );
            } else {
                throw new UsernameNotFoundException( "Username " + username + "not found." );
            }
            Map<Object, Object> model = new HashMap<>();
            model.put( "username", username );
            model.put( "token", token );
            return ResponseEntity.ok( model );
        } catch ( BadCredentialsException badCredentialsException ) {
            throw new BadCredentialsException( "Invalid username/password supplied." );
        }
    }
}
