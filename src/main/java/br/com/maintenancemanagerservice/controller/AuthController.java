package br.com.maintenancemanagerservice.controller;

import br.com.maintenancemanagerservice.config.security.TokenService;
import br.com.maintenancemanagerservice.dto.*;
import br.com.maintenancemanagerservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginRecord> login(@RequestBody @Valid RequestLoginRecord data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        var response = new ResponseLoginRecord(token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<RegistrationResponseRecord> register(@RequestBody @Valid RegisterUserRecord data){
        return userService.register(data);
    }

    @GetMapping("/admin/list-users")
    public ResponseEntity<List<UserResponseRecord>> listUsers() {
        return userService.listAll();
    }

    @PutMapping("/admin/delete-user")
    public ResponseEntity<String> deleteUser(@RequestHeader String userName) {
        return userService.delete(userName);
    }
}
