package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;

import java.util.List;

public interface BookService {
    BookDto publishBook(BookRequest bookRequest, User publisher);

    void deleteBook(Long bookId, User publisher);

    List<BookDto> findAll();

    List<BookDto> findBookByStyle(Style style);

    List<BookDto> findBooksByAuthorOrBook(String value);

    List<BookDto> getMyBooks(User user);

    List<BookDto> findByTopRate();

    String download(Long id);

    String readBook(Long id);
}
