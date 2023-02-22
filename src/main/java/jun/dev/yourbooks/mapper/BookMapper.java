package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final UserMapper userMapper;
    public Book toBook(String imageUrl, String bookUrl, BookRequest bookRequest, User publisher) {
        return Book.builder()
                .name(bookRequest.getName())
                .author(bookRequest.getAuthor())
                .description(bookRequest.getDescription())
                .style(bookRequest.getStyle())
                .publisher(publisher)
                .bookUrl(bookUrl)
                .imageUrl(imageUrl)
                .publishedYear(bookRequest.getPublishedDate())
                .rating(0)
                .build();
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .style(book.getStyle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .publishedYear(book.getPublishedYear())
                .rating(book.getRating())
                .imageUrl(book.getImageUrl())
                .bookUrl(book.getBookUrl())
                .publisherDto(userMapper.toDto(book.getPublisher()))
                .build();
    }


}
