package HCY.CommentBoard.repository.search;

import HCY.CommentBoard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Board search1();

//    Page<Object[]> SearchPage(String type, String keyword, Pageable pageable);

}
