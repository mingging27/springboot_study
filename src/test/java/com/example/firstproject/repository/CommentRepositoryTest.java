package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //해당 클래스를 JPA와 연동하여 테스트
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")    //메서드명을 직접 수정하지 않고 테스트 이름 붙일 때
    void findByArticleId() {
        /*Case 1: 4번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 4L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(4L, "저녁메뉴투표좀", "떡볶이vs치킨");
            Comment a = new Comment(1L,"루미", "떡볶이 ㄱㄱ", article);
            Comment b = new Comment(2L,"치킨프로텍터", "치킨이지", article);
            Comment c = new Comment(3L,"뽀로로", "피자마싯겟다", article);
            List<Comment> expected = Arrays.asList(a,b,c);
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "4번 글의 모든 댓글을 출력!");
        }
        /*Case 2: 1번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 1L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "1번 글은 댓글이 없음");  //반환값이 빈 배열이므로 toString() ㄱㄴ
        }
        /*Case 3: 9번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 9L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "9번 글이 없음");
        }
        /*Case 4: 999번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 999L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "999번 글이 없음");
        }
        /*Case 5: -1번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = -1L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "-1번 글이 없음");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")    //메서드명을 직접 수정하지 않고 테스트 이름 붙일 때
    void findByNickname() {
        /*Case 1: 루미의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            String nickname = "루미";
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //3. 예상 데이터
            Comment a = new Comment(1L,"루미", "떡볶이 ㄱㄱ", new Article(4L, "저녁메뉴투표좀", "떡볶이vs치킨"));
            List<Comment> expected = Arrays.asList(a);
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "루미의 모든 댓글을 출력!");
        }
//        /*Case 2: 아이디 값이 일치 X*/
//        {
//            //1. 입력 데이터 준비
//            String nickname = "루미";
//            //2. 실제 데이터
//            List<Comment> comments = commentRepository.findByNickname(nickname);
//            //3. 예상 데이터
//            Comment a = new Comment(2L,"루미", "떡볶이 ㄱㄱ", new Article(4L, "저녁메뉴투표좀", "떡볶이vs치킨"));
//            List<Comment> expected = Arrays.asList(a);
//            //4. 비교 및 검증
//            assertEquals(comments.toString(), expected.toString(), "루미의 모든 댓글을 출력!");
//        }
        /*Case 3: Park의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            String nickname = "Park";
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //3. 예상 데이터
            Comment a = new Comment(4L,"Park", "뽀로로의 대모험", new Article(5L, "당신의 인생 영화는?", "댓글 고고고"));
            List<Comment> expected = Arrays.asList(a);
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "Park의 모든 댓글을 출력!");
        }
        /*Case 4: Park의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            String nickname = null;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //3. 예상 데이터
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "null의 댓글은 없음");
        }
        /*Case 5: ""의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            String nickname = "";
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //3. 예상 데이터
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(comments.toString(), expected.toString(), "닉네임 없음");
        }
    }
}