package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.BoardDTO;
import HCY.CommentBoard.dto.PageRequestDTO;
import HCY.CommentBoard.dto.PageResponseDTO;
import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); // 목록 처리.

    BoardDTO get(Long id); // 조회 처리.

    void removeWithReplies(Long id); // 삭제 기능.

    void modify(BoardDTO dto); // 수정 기능.

    default Board dtoToEntity(BoardDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCnt) {
        BoardDTO boardDTO = BoardDTO.builder()
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .replyCount(replyCnt.intValue())
                .build();

        return boardDTO;
    }
}
