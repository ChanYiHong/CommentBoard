package HCY.CommentBoard.dto;

import HCY.CommentBoard.entity.Board;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<DTO, EN> {

    private List<DTO> data;

    private int page;
    private int size;
    private int totalPage;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;

    private List<Integer> pageList;

    public PageResponseDTO(Page<EN> result, Function<EN, DTO> fn){
        data = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();
        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();

        int tempEnd = (int) Math.ceil(page / 10.0) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
