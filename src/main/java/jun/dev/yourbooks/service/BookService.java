package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface BookService {
    BookDto publishBook(BookRequest bookRequest, User publisher);
}
