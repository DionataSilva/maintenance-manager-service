package br.com.maintenancemanagerservice.service;

import br.com.maintenancemanagerservice.dto.*;
import br.com.maintenancemanagerservice.exceptions.UserException;
import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var password = userRecord.password();

        var encryptedPassword = encryptPassword(Objects.nonNull(password)? password : "1234");

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

    public ResponseEntity<String> update(UpdateUserRecord updateRecord) {

        //TODO: Ajustar validação
        var contextAuth = SecurityContextHolder.getContext().getAuthentication();
        var isAbleToUpdateRoles = contextAuth.getAuthorities().contains("ROLE_ADMIN");

        var userOptional = userRepository.findById(updateRecord.id());

        if (userOptional.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var user = userOptional.get();

        if(Objects.nonNull(updateRecord.roles()) && isAbleToUpdateRoles) {
            user.setUserRoles(updateRecord.roles());
        }

        if(Objects.nonNull(updateRecord.name())) {
            user.setName(updateRecord.name());
        }

        this.saveOrUpdate(user, false);
        return ResponseEntity.ok("User updated!");
    }


    public ResponseEntity<String> update(String id,  String password) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var user = userOptional.get();

        user.setPassword(encryptPassword(password));

        this.saveOrUpdate(user, false);
        return ResponseEntity.ok("Password changed!");
    }

    public ResponseEntity<String> softDelete(String userId){
        var userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var user = userOptional.get();
        user.setEnabled(!user.isEnabled());

        this.saveOrUpdate(user, false);

        return ResponseEntity.ok(String.format("User %s %s", user.getName(), user.isEnabled() ? "is enabled." : "is disabled."));
    }

    @Transactional
    public ResponseEntity<String> delete(String userName) {
        var registeredUser = userRepository.findByName(userName);

        if (Objects.isNull(registeredUser)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
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

    private static String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
