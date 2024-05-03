package com.prj1.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
    private Integer id;
    private String title;
    private String content;

    // 작성자 nickname 용으로 사용 됨
    private String writer;
    private LocalDateTime inserted;
    private Integer memberId;
}
