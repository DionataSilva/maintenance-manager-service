package br.com.maintenancemanagerservice.controller;

import br.com.maintenancemanagerservice.config.security.TokenService;
import br.com.maintenancemanagerservice.dto.RequestLoginRecord;
import br.com.maintenancemanagerservice.dto.ResponseLoginRecord;
import br.com.maintenancemanagerservice.dto.RegisterUserRecord;
import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginRecord> login(@RequestBody @Valid RequestLoginRecord data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        var response = new ResponseLoginRecord(token);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/admin/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRecord data){
        var user = userRepository.findByName(data.userName());

        if(user != null) {
            return ResponseEntity.unprocessableEntity().body("User already exists!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = User.builder()
                .name(data.userName())
                .password(encryptedPassword)
                .roles(Set.of(data.role()))
                .build();

        userRepository.save(newUser);

        return ResponseEntity.ok("Successfully registered user! ");
    }

    @GetMapping("/admin/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/admin/delete-user")
    public ResponseEntity deleteUser(@RequestHeader String userName) {
        // Todo: validar user antes de excluir
        userRepository.deleteByUsername(userName);
        return ResponseEntity.ok("User deleted with success!");
    }

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1>Public route</h1>";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "<h1>Private route</h1>";
    }
}
