package io.security.basicsecurity.controller.user;

import io.security.basicsecurity.domain.entity.Account;
import io.security.basicsecurity.domain.dto.AccountDto;
import io.security.basicsecurity.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController{
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/mypage")
    public String myPage() throws Exception {
        return "user/mypage";
    }

    @GetMapping("/users")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(AccountDto accountDto) {
        ModelMapper modelMapper = new ModelMapper();
        // dto -> Entity
        Account account = modelMapper.map(accountDto, Account.class);
        // 암호화
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        // 서비스의 새성 메소드 호출
        userService.createUser(account);

        return "redirect:/";
    }
}