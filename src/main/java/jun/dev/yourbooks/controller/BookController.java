package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    @PostMapping("add")
    public ResponseEntity<BookDto> addBook(@Valid @ModelAttribute BookRequest bookRequest,
                                           @AuthenticationPrincipal User publisher){
        return ResponseEntity.ok(bookService.publishBook(bookRequest, publisher));
    }
}
