package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    public ResponseEntity<BlogDto> addBlog(@Valid @RequestBody BlogRequest blogRequest,
                                           @AuthenticationPrincipal User publisher){
        return ResponseEntity.ok(blogService.createBlog(blogRequest, publisher));
    }
}
