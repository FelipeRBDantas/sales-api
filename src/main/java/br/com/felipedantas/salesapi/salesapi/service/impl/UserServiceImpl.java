package br.com.felipedantas.salesapi.salesapi.service.impl;

import br.com.felipedantas.salesapi.salesapi.model.entity.User;
import br.com.felipedantas.salesapi.salesapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
    }
}
