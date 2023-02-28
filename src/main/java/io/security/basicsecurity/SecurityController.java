package io.security.basicsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin/pay")
    public String adminPay() {
        return "admin pay";
    }

    @GetMapping("/admin/**")
    public String adminAll() {
        return "admin all";
    }

    @GetMapping("/denied")
    public String deny() { return "Access is denied"; }

    @GetMapping("/login")
    public String login() { return "login"; }
}
