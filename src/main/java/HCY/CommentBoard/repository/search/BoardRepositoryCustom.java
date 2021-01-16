package HCY.CommentBoard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
