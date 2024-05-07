package com.prj1.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class CustomUser extends User {

    private Member member;

    // 상위 타입의 User 의 생성자 호출 필수
    public CustomUser(Member member) {
        // 문법 : 상위 클래스의 생성자(super)를 사용할 때는 항상 첫줄에 작성
        // authority 가 null 이면 -> ""(빈스트릥)으로 넘어와서 null check 안해도됨
        super(member.getEmail(),
                member.getPassword(),
                // Collection<GrantedAuthority 타입으로 3번째 변수
                member.getAuthority().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
        this.member = member;
    }


}
