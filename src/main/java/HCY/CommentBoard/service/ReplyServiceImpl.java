package HCY.CommentBoard.service;

import HCY.CommentBoard.dto.ReplyDTO;
import HCY.CommentBoard.entity.Reply;
import HCY.CommentBoard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public Long register(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply);
        return reply.getId();
    }

    @Override
    public List<ReplyDTO> getList(Long boardId) {
        List<Reply> result = replyRepository.findReplyByBoardId(boardId);
        return result.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void modify(ReplyDTO dto) {
        Optional<Reply> result = replyRepository.findById(dto.getId());
        if(result.isPresent()){
            Reply reply = result.get();
            reply.changeText(dto.getText());
        }
    }

    @Transactional
    @Override
    public void remove(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
