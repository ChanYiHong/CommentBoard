package HCY.CommentBoard.repository.search;

import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.QBoard;
import HCY.CommentBoard.entity.QMember;
import HCY.CommentBoard.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static HCY.CommentBoard.entity.QBoard.board;
import static HCY.CommentBoard.entity.QMember.member;
import static HCY.CommentBoard.entity.QReply.reply;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1..........");

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);


        log.info("jpqlQuery");
        log.info(tuple);

        List<Board> result = jpqlQuery.fetch();

        log.info("result");
        log.info(result);

        return null;
    }

//    @Override
//    public Page<Object[]> SearchPage(String type, String keyword, Pageable pageable) {
//
//        log.info("Search Page");
//
//
//    }
}
