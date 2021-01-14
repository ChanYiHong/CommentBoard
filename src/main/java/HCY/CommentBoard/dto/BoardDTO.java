package HCY.CommentBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private String writerEmail; // 작성자의 email;
    private String writerName; // 작성자의 name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int replyCount; // 해당 게시글의 댓글 수;

}
