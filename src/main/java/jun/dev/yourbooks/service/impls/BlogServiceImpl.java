package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.BlogMapper;
import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.Blog;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.repository.BlogRepo;
import jun.dev.yourbooks.repository.BookRepo;
import jun.dev.yourbooks.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepo blogRepo;
    private final BlogMapper blogMapper;
    private final BookRepo bookRepo;
    @Override
    public BlogDto createBlog(BlogRequest blogRequest, User publisher) {
        Blog blog = blogMapper.toBlog(blogRequest,publisher);
        Blog savedBlog = blogRepo.save(blog);
        // TODO :
        //  Complete update function of rate
        updateRating(blog.getBook());
        return blogMapper.toDto(savedBlog);
    }
    private void updateRating(Book book){
        List<Book> books = bookRepo.findAll();

    }
}
