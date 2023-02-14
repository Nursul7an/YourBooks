package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.NewsDto;
import jun.dev.yourbooks.model.wraper.request.NewsRequest;
import jun.dev.yourbooks.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @PostMapping("/create")
    public ResponseEntity<NewsDto> addNews(@Valid @ModelAttribute NewsRequest newsRequest){
        return ResponseEntity.ok(newsService.createNews(newsRequest));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNews(@RequestParam Long id){
        newsService.delete(id);
        return ResponseEntity.ok("You successfully deleted the post");
    }
    @GetMapping("/all/news")
    public ResponseEntity<Page<NewsDto>> getRecentNews(@PageableDefault(size = 5)Pageable pageable){
        return ResponseEntity.ok(newsService.findRecentNews(pageable));
    }
    @PutMapping("/update")
    public ResponseEntity<NewsDto> updateNews(@RequestParam Long bookId,
                                              @ModelAttribute NewsRequest newsRequest){
        return ResponseEntity.ok(newsService.editNews(bookId,newsRequest));
    }
}
