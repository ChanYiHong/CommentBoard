package HCY.CommentBoard.repository;

import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void insertReply() throws Exception {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            long boardId = (long)(Math.random() * 100) + 1;
            Board board = Board.builder().id(boardId).build();
            Reply reply = Reply.builder().text("Reply..." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });

    }

    @Test
    public void readReply1() throws Exception {

        Optional<Reply> result = replyRepository.findById(100L);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

    }

    @Test
    public void testListByBoardId() throws Exception {

        List<Reply> results = replyRepository.findReplyByBoardId(5L);
        for (Reply result : results) {
            System.out.println(result);
        }

    }

}