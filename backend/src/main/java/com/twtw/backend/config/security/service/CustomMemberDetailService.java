package com.twtw.backend.config.security.service;

import com.twtw.backend.module.member.entity.Member;
import com.twtw.backend.module.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    CustomMemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<Member> member = memberRepository.findById(UUID.fromString(username));

        if(member.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }

        Member curMember = member.get();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(curMember.getRole().toString());
        Collection<GrantedAuthority> list = new ArrayList<>();
        list.add(grantedAuthority);

        return new User(curMember.getId().toString(),"",list);
    }

}
