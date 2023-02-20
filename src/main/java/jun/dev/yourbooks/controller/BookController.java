package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;
import jun.dev.yourbooks.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam Long bookId, @AuthenticationPrincipal User publisher){
        bookService.deleteBook(bookId,publisher);
        return ResponseEntity.ok("You successfully deleted book with ID: "+bookId);
    }
    @GetMapping("/find/new/book")
    public ResponseEntity<List<BookDto>> findAll(){
        return ResponseEntity.ok(bookService.findAll());
    }
    @GetMapping("/find/by/style")
    public ResponseEntity<List<BookDto>> findBookByStyle(@RequestParam Style style){
        return ResponseEntity.ok(bookService.findBookByStyle(style));
    }
    @GetMapping("/find/author/book")
    public ResponseEntity<List<BookDto>> findBookByAuthorOrName(@RequestParam String value){
        return ResponseEntity.ok(bookService.findBooksByAuthorOrBook(value));
    }
    @GetMapping("/my/books")
    public ResponseEntity<List<BookDto>> myBooks(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookService.getMyBooks(user));
    }

}
