package br.com.maintenancemanagerservice.service;

import br.com.maintenancemanagerservice.dto.*;
import br.com.maintenancemanagerservice.exceptions.UserException;
import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByName(username);

        if (Objects.isNull(user)){
            throw new UsernameNotFoundException(username);
        }

       return user;
    }

    public ResponseEntity<RegistrationResponseRecord> register(RegisterUserRecord userRecord) {
        var encryptedPassword = new BCryptPasswordEncoder().encode(userRecord.password());

        var newUser = User.builder()
                .name(userRecord.userName())
                .password(encryptedPassword)
                .userRoles(userRecord.roles())
                .build();

        try {
            newUser = this.saveOrUpdate(newUser, true);
            return ResponseEntity.ok(RegistrationResponseRecord.success(newUser.getId(), newUser.getName()));
        } catch (UserException userException) {
            // TODO: Log error
            return ResponseEntity.unprocessableEntity().body(RegistrationResponseRecord.error(List.of(userException.getMessage())));
        } catch (Exception e) {
            // TODO: Log error
            return ResponseEntity.internalServerError().build();
        }

    }

    private User update(UpdateUserRecord updateRecord) {

        this.saveOrUpdate(null, false);
        return null;
    }


    private User update(UpdatePasswordRecord passwordRecord) {

        this.saveOrUpdate(null, false);
        return null;
    }

    @Transactional
    public ResponseEntity<String> delete(String userName) {
        var registeredUser = userRepository.findByName(userName);

        if (Objects.isNull(registeredUser)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteByName(userName);

        return ResponseEntity.ok("User deleted with success!");
    }

    public User getOneById(String id) {
        return null;
    }

    public User getOneByName(String Name) {
        return null;
    }

    public ResponseEntity<List<UserResponseRecord>> listAll() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(UserResponseRecord.userResponseList(users));
    }

    @Transactional(rollbackFor = UserException.class)
    private User saveOrUpdate(User user, boolean isRegistration) {
        if(isRegistration) {
            var registeredUser = userRepository.findByName(user.getUsername());

            if(Objects.nonNull(registeredUser)) {
                throw new UserException("Already registered user!");
            }
        }


        return userRepository.save(user);
    }
}
