package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import jun.dev.yourbooks.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    @PostMapping("/add")
    public ResponseEntity<BlogDto> addBlog(@Valid @RequestBody BlogRequest blogRequest,
                                           @AuthenticationPrincipal User publisher){
        return ResponseEntity.ok(blogService.createBlog(blogRequest, publisher));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlog(@RequestParam Long id, @AuthenticationPrincipal User user){
        blogService.delete(id, user);
        return ResponseEntity.ok("You successfully deleted blog with id " + id);
    }
    @GetMapping("my/blog")
    public ResponseEntity<List<BlogDto>> myBlogs(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(blogService.getMyBlogs(user));
    }
    @GetMapping("/by/tag")
    public ResponseEntity<List<BlogDto>> getBlogByTag(@RequestParam Tag tag){
        return ResponseEntity.ok(blogService.getBlogsByTag(tag));
    }

}
