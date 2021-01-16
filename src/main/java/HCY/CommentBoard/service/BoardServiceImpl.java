package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.BoardDTO;
import HCY.CommentBoard.dto.PageRequestDTO;
import HCY.CommentBoard.dto.PageResponseDTO;
import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Member;
import HCY.CommentBoard.repository.BoardRepository;
import HCY.CommentBoard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info("register...");

        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getId();
    }

    @Override
    public PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
        //Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("id").descending()));

        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("id").descending())
        );

        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long id) {

        log.info("inquiry Board : " + id);

        Object result = boardRepository.getBoardById(id);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);

    }

    @Transactional // 한 트렌젝션 안에서 동작.
    @Override
    public void removeWithReplies(Long id) {
        replyRepository.deleteByBoardId(id); // 댓글 부터 삭제처리.
        boardRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void modify(BoardDTO dto) {
        Board board = boardRepository.getOne(dto.getId());
        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());
    }
}
