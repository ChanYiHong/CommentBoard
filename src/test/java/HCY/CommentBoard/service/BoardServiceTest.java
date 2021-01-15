package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.BoardDTO;
import HCY.CommentBoard.dto.PageRequestDTO;
import HCY.CommentBoard.dto.PageResponseDTO;
import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Member;
import HCY.CommentBoard.repository.BoardRepository;
import HCY.CommentBoard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired BoardService boardService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testRegister() throws Exception {

        BoardDTO dto = BoardDTO.builder()
                .content("Test")
                .title("TestTitle")
                .writerEmail("user55@aaa.com") // 현재 데이터베이스에 존재하는 회원 이메일.
                .build();

        Long register = boardService.register(dto);
        System.out.println(register);

    }

    @Test
    @Transactional
    public void 회원을만들어서넣어주나() throws Exception {

        BoardDTO dto = BoardDTO.builder()
                .content("Test")
                .title("TestTitle")
                .writerEmail("user55@aaa.com") // 현재 데이터베이스에 존재하는 회원 이메일.
                .build();

        Long register = boardService.register(dto);

        Optional<Board> byId = boardRepository.findById(register);
        Board findBoard = byId.get();
        Member member = findBoard.getWriter();

        System.out.println(member.getEmail());
        System.out.println(member.getName());  // null
        System.out.println(member.getPassword()); // null .. 문제있는거아님..?

    }

    @Test
    public void testList() throws Exception {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResponseDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getData()) {
            System.out.println(boardDTO);
        }

    }

    @Test
    public void testGet() throws Exception {

        Long id = 100L;
        BoardDTO boardDTO = boardService.get(id);
        System.out.println(boardDTO);

    }

    @Test
    public void testDelete() throws Exception {

        boardService.removeWithReplies(1L);

    }


    @Test
    public void testModify() throws Exception {

        BoardDTO dto = BoardDTO.builder().id(2L).title("ModifyTitle").content("Modified").build();
        boardService.modify(dto);
        BoardDTO boardDTO = boardService.get(dto.getId());
        System.out.println(boardDTO);

    }

}