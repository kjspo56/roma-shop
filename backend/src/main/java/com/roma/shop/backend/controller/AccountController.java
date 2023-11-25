package com.roma.shop.backend.controller;

import com.roma.shop.backend.entity.Member;
import com.roma.shop.backend.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class AccountController {
    private final MemberRepository memberRepository;

    public AccountController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/api/account/login")
    public int login(@RequestBody Map<String, String> params){
      Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
      if(member != null ){
          return member.getId();
      }
      throw new ResponseStatusException(NOT_FOUND);
    }
}
