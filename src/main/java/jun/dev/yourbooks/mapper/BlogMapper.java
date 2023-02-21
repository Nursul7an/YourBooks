package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.Blog;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BlogMapper {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    public Blog toBlog(BlogRequest blogRequest, User publisher) {
        return Blog.builder()
                .text(blogRequest.getText())
                .book(bookRepo.findBookById(blogRequest.getBookId())
                        .orElseThrow(()-> new NotFoundException("No such a book found with "+ blogRequest.getBookId())))
                .createdTime(LocalDate.now())
                .publisher(publisher)
                .tag(blogRequest.getTag())
                .rate(blogRequest.getRate())
                .build();
    }

    public BlogDto toDto(Blog savedBlog) {
        return BlogDto.builder()
                .id(savedBlog.getId())
                .text(savedBlog.getText())
                .tag(savedBlog.getTag())
                .rate(savedBlog.getRate())
                .createdTime(savedBlog.getCreatedTime())
                .book(bookMapper.toDto(savedBlog.getBook()))
                .build();
    }
}
