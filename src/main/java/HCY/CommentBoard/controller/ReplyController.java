package HCY.CommentBoard.controller;

import HCY.CommentBoard.dto.ReplyDTO;
import HCY.CommentBoard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("boardId") Long boardId) {
        List<ReplyDTO> list = replyService.getList(boardId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        log.info(replyDTO);

        Long id = replyService.register(replyDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> remove(@PathVariable("replyId") Long replyId) {
        replyService.remove(replyId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        replyService.modify(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
