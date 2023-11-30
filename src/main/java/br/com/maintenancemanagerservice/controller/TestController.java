package br.com.maintenancemanagerservice.controller;

import br.com.maintenancemanagerservice.config.security.TokenService;
import br.com.maintenancemanagerservice.dto.LoginRequestRecordDto;
import br.com.maintenancemanagerservice.dto.RegisterRecordDTO;
import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestRecordDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRecordDTO data){
        if(userRepository.findByUsername(data.userName()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = User.builder()
                .username(data.userName())
                .password(encryptedPassword)
                .userRoles(Set.of(data.role()))
                .build();

        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1>Public route</h1>";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "<h1>Private route</h1>";
    }

    @GetMapping("/jwt")
    public String privateRouteJwt() {
        return String.format("""
                <h1>Private route JWT</h1>
                """);
    }
}
