package HCY.CommentBoard.repository;

import HCY.CommentBoard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /** Board id 값으로 게시글에 존재하는 댓글 삭제... **/
    @Modifying
    @Query("delete from Reply r where r.board.id =:id")
    void deleteByBoardId(@Param("id") Long id);

}
