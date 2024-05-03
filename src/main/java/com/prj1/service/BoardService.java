package com.prj1.service;

import com.prj1.domain.Board;
import com.prj1.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardService {

    private final BoardMapper mapper;

    public void add(Board board) {
        mapper.insert(board);
    }

    public Board get(Integer id) {
        return mapper.selectById(id);
    }

    public List<Board> list() {
        return mapper.selectAll();
    }

    public void remove(Integer id) {
        mapper.deleteById(id);
    }

    public void modify(Board board) {
        mapper.update(board);
    }

    // paging
    public Map<String, Object> list(Integer page) {
        int offset = (page - 1) * 10;
        int numberOfBoard = mapper.countAll();
        int lastPageNumber = (numberOfBoard - 1) / 10 + 1;
        int endPageNumber = (page - 1) / 10 * 10 + 10;
        int beginPageNumber = endPageNumber - 9;

        // endPage 는 lastPage 보다 클 수 없게 설정
        endPageNumber = Math.min(endPageNumber, lastPageNumber);

        // 이전, 다음
        int prevPageNumber = beginPageNumber - 10;
        int nextPageNumber = beginPageNumber + 10;
        // 첫 페이징구간일 때 이전, 마지막 구간일 때 다음 버튼 오류 잡기

        // Controller 에 리턴값 참고바람
        return Map.of("boardList", mapper.selectAllByPage(offset),
                "pageInfo", Map.of("lastPageNumber", lastPageNumber,
                                        "endPageNumber", endPageNumber,
                                        "beginPageNumber", beginPageNumber,
                                        "prevPageNumber", prevPageNumber,
                                        "nextPageNumber", nextPageNumber,
                                        "currentPageNumber", page)
                );
    }
}
