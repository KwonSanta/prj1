package com.prj1.service;

import com.prj1.domain.CustomUser;
import com.prj1.domain.Member;
import com.prj1.mapper.BoardMapper;
import com.prj1.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    private final MemberMapper mapper;
    // encoding 주입
    private final BCryptPasswordEncoder encoder;
    private final BoardMapper boardMapper;

    // 회원가입 기능
    public void signup(Member member) {
        // 암호 encoding 해서 저장
//        String password = member.getPassword();
//        String encodedPassword = encoder.encode(password);
//        member.setPassword(encodedPassword);
        member.setPassword(encoder.encode(member.getPassword()));

        mapper.insert(member);
    }

    public List<Member> list() {
        return mapper.selectAll();
    }

    public Member get(Integer id) {
        return mapper.selectById(id);
    }

    public void remove(Integer id) {
        // board 테이블에서 레코드 삭제
        boardMapper.deleteBoardByMemberId(id);

        // member 테이블에서 레코드 삭제
        mapper.deleteById(id);
    }

    public void modify(Member member) {
        if (member.getPassword() != null && member.getPassword().length() > 0) {
            // 암호를 입력했을 때만 변경
            member.setPassword(encoder.encode(member.getPassword()));
        } else {
            // 그렇지 않으면 기존 암호 유지
            Member old = mapper.selectById(member.getId());
            member.setPassword(old.getPassword());
        }
    }

    public String emailCheck(String email) {
        Member member = mapper.selectByEmail(email);
        if (member == null) {
            // 사용 가능한 이메일
            return "사용 가능한 이메일입니다.";
        } else {
            // 이미 존재하는 이메일
            return "이미 존재하는 이메일입니다.";
        }
    }

    public boolean hasAccess(Integer id, Authentication auth) {
        if (auth == null) {
            return false;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUser user) {
            Member member = user.getMember();
            return member.getId().equals(id);
        }
        return false;
    }

    public boolean isAdmin(Authentication authentication) {
        Object o = authentication.getPrincipal();
        if (o instanceof CustomUser user) {
            return user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(s -> s.equals("admin"));
        }
        return false;
    }
}
