package sbsamir.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sbsamir.model.User;
import sbsamir.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return UserDetailsImpl.build(user);
    }

    
}
