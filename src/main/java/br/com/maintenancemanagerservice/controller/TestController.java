package br.com.maintenancemanagerservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1>Public route</h1>";
    }

    @GetMapping("/private")
    public String privateRoute(@AuthenticationPrincipal OidcUser principal) {
        return String.format("""
                <h1>Private route</h1>
                <h3>Principal: %s</h3>
                <h3>E-mail: %s</h3>
                <h3>Authorities: %s</h3>
                <h3>JWT Token: %s</h3>
                """,
                principal,
                principal.getAttribute("email"),
                principal.getAuthorities(),
                principal.getIdToken().getTokenValue()
        );
    }

    @GetMapping("/jwt")
    public String privateRouteJwt() {
        return String.format("""
                <h1>Private route JWT</h1>
                """);
    }
}
