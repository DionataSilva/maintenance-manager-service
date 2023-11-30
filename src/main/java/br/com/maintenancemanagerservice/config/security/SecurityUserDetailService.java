package br.com.maintenancemanagerservice.config.security;

import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (Objects.isNull(user)){
            throw new UsernameNotFoundException(username);
        }

       return user;
    }

    public void saveUserDetail(User user) {
        if(Objects.isNull(userRepository.findByUsername(user.getUsername()))) {
            userRepository.save(user);
        }
    }
}
