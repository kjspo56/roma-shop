package com.roma.shop.backend.controller;

import com.roma.shop.backend.entity.Member;
import com.roma.shop.backend.repository.MemberRepository;
import com.roma.shop.backend.service.JwtService;
import com.roma.shop.backend.service.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class AccountController {
    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    public AccountController(MemberRepository memberRepository, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res){
      Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
      if(member != null ){
          JwtService jwtService = new JwtServiceImpl();
          int id = member.getId();
          String token = jwtService.getToken("id", id);
          Cookie cookie = new Cookie("token", token);

          cookie.setHttpOnly(true);
          cookie.setPath("/");

          res.addCookie(cookie);
          return  new ResponseEntity<>(id, HttpStatus.OK);

      }
      throw new ResponseStatusException(NOT_FOUND);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);
        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
