package HCY.CommentBoard.repository;

import HCY.CommentBoard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /** Board id 값으로 게시글에 존재하는 댓글 삭제... **/
    @Modifying
    @Query("delete from Reply r where r.board.id =:id")
    void deleteByBoardId(@Param("id") Long id);

    @Query("select r from Reply r where r.board.id =:id order by r.id asc")
    List<Reply> findReplyByBoardId(@Param("id") Long id);

}
