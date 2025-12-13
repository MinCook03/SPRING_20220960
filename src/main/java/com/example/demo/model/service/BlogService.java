package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.domain.Article; 
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 주입 자동화 (권장)
public class BlogService {

    // 기존 Article용 리포지토리 (필요 없다면 추후 삭제)
    private final BlogRepository blogRepository;
    
    // 신규 Board용 리포지토리 (변수명을 명확하게 boardRepository로 통일)
    private final BoardRepository boardRepository;

    // =============================================
    // 1. 글 저장 (Create)
    // =============================================
    public Board save(AddArticleRequest request) {
        // DTO의 toEntity()가 Board 객체를 반환하도록 설정되어 있어야 합니다.
        return boardRepository.save(request.toEntity());
    }

    // =============================================
    // 2. 글 전체 조회 (Read - List)
    // =============================================
    
    // 리스트 전체 조회 (페이징 없음)
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 페이징 조회 (컨트롤러에서 주로 사용)
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    
    // 키워드 검색 조회
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    // =============================================
    // 3. 글 상세 조회 (Read - Detail)
    // =============================================
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    // =============================================
    // 4. [수정] 글 수정 (Update)
    // =============================================
    @Transactional
    public void update(Long id, AddArticleRequest request) {
        // 1. 기존 게시글 찾기
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 2. 내용 수정
        // title, content는 수정된 내용(request)을 사용
        // user, newdate, count, likec는 기존 DB에 있던 내용(board.get...)을 그대로 다시 넣어줌 (Null 방지)
        board.update(
            request.getTitle(),
            request.getContent(),
            board.getUser(),     // 기존 작성자 유지
            board.getNewdate(),  // 기존 작성일 유지
            board.getCount(),    // 기존 조회수 유지
            board.getLikec()     // 기존 좋아요 유지
        );
    }

    // =============================================
    // 5. 글 삭제 (Delete)
    // =============================================
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // =============================================
    // (옵션) 레거시 Article 지원 메서드
    // 사용하지 않는다면 추후 삭제하세요.
    // =============================================
    public List<Article> findAllArticles() {
        return blogRepository.findAll();
    }
}