package HCY.CommentBoard.repository;

import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.repository.search.BoardRepositoryCustom;
import HCY.CommentBoard.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository, BoardRepositoryCustom {

    @Query("select b, w from Board b left join b.writer w where b.id =:id")
    Object getBoardWithWriter(@Param("id") Long id);

    @Query("select b, r from Board b left join Reply r on r.board = b where b.id =:id")
    List<Object[]> getBoardWithReply(@Param("id") Long id);

    /** 목록화면에 필요한 내용 . 게시물 번호, 제목, 작성기간, / 회원 이름, 이메일 / 해당 게시물 댓글 수  Board를 기준으로 조인.**/

    @Query(value = "select b, w, count(r) from Board b left join b.writer w left join Reply r on r.board = b group by b",
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    /** 조회 화면용 **/

    @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r on r.board = b where b.id =:id")
    Object getBoardById(@Param("id") Long id);

}
