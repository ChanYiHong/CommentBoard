package HCY.CommentBoard.controller;

import HCY.CommentBoard.dto.BoardDTO;
import HCY.CommentBoard.dto.PageRequestDTO;
import HCY.CommentBoard.dto.PageResponseDTO;
import HCY.CommentBoard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.........." + pageRequestDTO);

        PageResponseDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping("/register")
    public void registerGet(){
        log.info("register............");
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("dto") BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);

        Long id = boardService.register(dto);
        log.info("Board ID : " + id);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, @RequestParam("id") Long id, Model model) {
        log.info("id : " + id);
        BoardDTO boardDTO = boardService.get(id);

        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modifyPost(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                           @ModelAttribute("dto") BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("modify controller..........");

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());

        redirectAttributes.addAttribute("id", dto.getId());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {

        log.info("delete.......... : " + id);
        boardService.removeWithReplies(id);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/board/list";
    }

}
