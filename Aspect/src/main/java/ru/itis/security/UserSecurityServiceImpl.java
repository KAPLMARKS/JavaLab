package ru.itis.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import java.util.Optional;

@Service("UserSecurityService")
public class UserSecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = usersRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return new UserSecurityImpl(user);
        } else throw new UsernameNotFoundException("User not found");
    }
}