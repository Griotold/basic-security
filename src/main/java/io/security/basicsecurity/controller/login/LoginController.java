package io.security.basicsecurity.controller.login;

import io.security.basicsecurity.domain.entity.Account;
import io.security.basicsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class LoginController {
    // http://localhost:8080/login?error=true&exception=Invalid%20Username%20or%20Password2
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        // 인증실패시 에러와 예외를 화면에 던져주기
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/user/login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 로그아웃을 수행한다는 것은 로그인이이 되어있다는 말이고
        // S.C안에 인증 객체도 있다는 의미
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception,
                               Principal principal, Model model) throws Exception{
        Account account = null;
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            account = (Account) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        }else if(principal instanceof AjaxAuthenticationToken){
            account = (Account) ((AjaxAuthenticationToken) principal).getPrincipal();
        }

        model.addAttribute("username", account.getUsername());
        model.addAttribute("exception", exception);

        // 인증 객체 가져오기 : 화면에 username 뿌려주기 위해
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Object principal = authentication.getPrincipal();
//        Account account = (Account) authentication.getPrincipal();
//        model.addAttribute("username", account.getUsername());
//        model.addAttribute("exception", exception);
        return "user/login/denied";
    }
}
