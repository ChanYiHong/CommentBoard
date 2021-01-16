package HCY.CommentBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long id;
    private String text;
    private String replyer;
    private Long boardId;
    private LocalDateTime createdDate, modifiedDate;

}
