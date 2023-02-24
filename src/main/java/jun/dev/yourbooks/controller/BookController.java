package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.dto.response.ResponseMyBooks;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;
import jun.dev.yourbooks.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @GetMapping("/find/new")
    public ResponseEntity<List<BookDto>> findNewBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }
    @GetMapping("/find/by/style")
    public ResponseEntity<List<BookDto>> findBookByStyle(@RequestParam Style style){
        return ResponseEntity.ok(bookService.findBookByStyle(style));
    }
    @GetMapping("/find/author/name")
    public ResponseEntity<List<BookDto>> findBookByAuthorOrName(@RequestParam String value){
        return ResponseEntity.ok(bookService.findBooksByAuthorOrBook(value));
    }
    @GetMapping("/my/books")
    public ResponseEntity<List<BookDto>> myBooks(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookService.getMyBooks(user));
    }
    @GetMapping("/find/by/top/rate")
    public ResponseEntity<List<BookDto>> findByTopRate(){
        return ResponseEntity.ok(bookService.findByTopRate());
    }
    @GetMapping("/download")
    public ResponseEntity<?> downloadBook(@RequestParam Long id){
        return ResponseEntity.ok(bookService.download(id));
    }
    @GetMapping("/read")
    public ResponseEntity<String> readBook(@RequestParam Long id){
        return ResponseEntity.ok(bookService.readBook(id));
    }

}
