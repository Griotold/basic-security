package io.security.basicsecurity.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @GetMapping("/messages")
    public String message() throws Exception {
        return "/user/messages";
    }

    @GetMapping("/api/messages")
    @ResponseBody // 응답 바디에 리턴값 실어서 사용자에게 응답
    public String apiMessage() {
        return "messages ok";
    }
}
