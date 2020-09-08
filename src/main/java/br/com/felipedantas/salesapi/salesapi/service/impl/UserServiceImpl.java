package br.com.felipedantas.salesapi.salesapi.service.impl;

import br.com.felipedantas.salesapi.salesapi.model.entity.User;
import br.com.felipedantas.salesapi.salesapi.model.repository.UserRepository;
import br.com.felipedantas.salesapi.salesapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Transactional( readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT )
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User userByUsername = userRepository.getUserByUsername( username );
        if ( userByUsername != null ) return userByUsername;
        else throw new UsernameNotFoundException( "Username " + userByUsername + " not found." );
    }
}
