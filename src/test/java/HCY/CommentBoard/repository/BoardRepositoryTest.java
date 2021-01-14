package HCY.CommentBoard.repository;

import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertBoard() throws Exception {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content... + i")
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });

    }

    @Test
    @Transactional
    public void testRead1() throws Exception {

        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

    }

    @Test
    public void testReadWithWriter() throws Exception {

        Object obj = boardRepository.getBoardWithWriter(100L);

        System.out.println(obj.toString());

        Object[] objs = (Object[]) obj;

        System.out.println(Arrays.toString(objs));

    }

    @Test
    public void testGetBoardWithReply() throws Exception {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

    @Test
    public void testWithReplyCount목록화면() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        //when
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        //then
        result.get().forEach(row -> {
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });

    }
    
    @Test
    public void test조회화면() throws Exception {

        Object result = boardRepository.getBoardById(100L);
        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

}