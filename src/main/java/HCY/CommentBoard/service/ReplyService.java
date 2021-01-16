package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.PageResponseDTO;
import HCY.CommentBoard.dto.ReplyDTO;
import HCY.CommentBoard.entity.Board;
import HCY.CommentBoard.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO dto); // 댓글 등록.

    List<ReplyDTO> getList(Long boardId); // 특정 게시물 댓글 목록.

    void modify(ReplyDTO dto); // 댓글 수정.

    void remove(Long replyId); // 댓글 삭제.

    default Reply dtoToEntity(ReplyDTO dto) {

        Board board = Board.builder().id(dto.getBoardId()).build(); // 이게 이래도 되나..? 있는 board를 찾아 넣어야 될거 같은데..

        return Reply.builder()
                .id(dto.getId())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
    }

    default ReplyDTO entityToDTO(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .boardId(reply.getBoard().getId())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();
    }

}
