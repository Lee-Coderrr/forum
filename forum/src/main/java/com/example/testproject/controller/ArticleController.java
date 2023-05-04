package com.example.testproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.testproject.dto.ArticleDto;
import com.example.testproject.dto.CommentDto;
import com.example.testproject.entity.Article;
import com.example.testproject.repository.ArticleRepository;
import com.example.testproject.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ArticleController {

    @Autowired // 객체 자동 연결
    private ArticleRepository articleRepo;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String formData(Model model) {
        return "articles/new";
    }

    @PostMapping("/articles/form")
    public String createArticle(ArticleDto form) {
        System.out.println(form.toString());

        Article article = form.toEntity();

        Article saved = articleRepo.save(article);

        log.info("Article saved: " + saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleRepo.findAll();

        model.addAttribute("articleList", articleEntityList);

        return "articles/index";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        
        log.info("id = " + id);
        
        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepo.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        
        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        // 3: 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Article articleEntity = articleRepo.findById(id).orElse(null);

        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleDto updateDto) {

        Article articleEntity = updateDto.toEntity();

        Article target = articleRepo.findById(articleEntity.getId()).orElse(null);

        if (target != null) {
            Article updated = articleRepo.save(articleEntity); // save는 기존 데이터가 존재하는 경우 덮어씀
            log.info(updated.toString());
        }

        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        Article target = articleRepo.findById(id).orElse(null);

        if (target != null) {
            articleRepo.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        return "redirect:/articles";
    }
}
