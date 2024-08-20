package com.sparta.springintermediateasignment;

import com.sparta.springintermediateasignment.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import java.net.http.HttpHeaders;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
//    @GetMapping("/api/test")
//    public String getTest(HttpServletRequest request) {
//        System.out.println("인증완료!");
//        User user = (User)request.getAttribute("user");
//        System.out.println(user.getName());
//        return "test";
//    }

    @GetMapping("/api/test")
    public String getTest2(@RequestHeader("Authorization") String token) {
        return  token;
    }
}
