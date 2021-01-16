package HCY.CommentBoard.repository.search;

import HCY.CommentBoard.entity.Board;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static HCY.CommentBoard.entity.QBoard.board;
import static HCY.CommentBoard.entity.QMember.member;
import static HCY.CommentBoard.entity.QReply.reply;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QueryResults<Tuple> queryResult = queryFactory
                .select(board, member, reply.count())
                .from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(
                        board.id.gt(0L),
                        titleEq(type, keyword),
                        contentEq(type, keyword),
                        writerEq(type, keyword)
                )
                .orderBy(
                        getOrderSpecifier(pageable.getSort())
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(board)
                .fetchResults();

        List<Tuple> content = queryResult.getResults();
        long totalCount = queryResult.getTotal();

        return new PageImpl<Object[]>(content.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList()),
                                        pageable, totalCount);
    }

    private BooleanExpression titleEq(String type, String keyword) {
        if(type != null) return type.contains("t") ? board.title.containsIgnoreCase(keyword) : null;
        else return null;
    }

    private BooleanExpression contentEq(String type, String keyword) {
        if(type != null) return type.contains("c") ? board.content.containsIgnoreCase(keyword) : null;
        else return null;
    }

    private BooleanExpression writerEq(String type, String keyword) {
        if(type != null) return type.contains("w") ? board.writer.name.containsIgnoreCase(keyword) : null;
        else return null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        // Sort
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return orders;
    }
}
