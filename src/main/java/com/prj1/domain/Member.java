package com.prj1.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {
    private Integer id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime inserted;

    // 권한
    private List<String> authority;
}
