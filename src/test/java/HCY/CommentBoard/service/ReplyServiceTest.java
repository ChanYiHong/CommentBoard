package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    public void testGetList() throws Exception {
        List<ReplyDTO> list = replyService.getList(5L);

        list.forEach(System.out::println);
    }

}