package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.NotAllowedException;
import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.BlogMapper;
import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.Blog;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import jun.dev.yourbooks.repository.BlogRepo;
import jun.dev.yourbooks.repository.BookRepo;
import jun.dev.yourbooks.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

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
        updateAverageRate(savedBlog.getBook());
        return blogMapper.toDto(savedBlog);
    }

    @Override
    public void delete(Long id, User user) {
        Blog blog = blogRepo.findById(id).orElseThrow(()-> new NotFoundException("No such a boo found with "+ id));
        if (!blog.getPublisher().getId().equals(user.getId()))
            throw new NotAllowedException("You are allowed to delete it!");
        blogRepo.delete(blog);
    }

    @Override
    public List<BlogDto> getMyBlogs(User user) {
        List<Blog> blogs = blogRepo.findBlogsByPublisher(user);
        return blogs.stream().map(blogMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BlogDto> getBlogsByTag(Tag tag) {
        List<Blog> blogs = blogRepo.findAllByTag(tag);
        return blogs.stream().map(blogMapper::toDto).collect(Collectors.toList());
    }

    private void updateAverageRate(Book book){
        List<Blog> blogs = blogRepo.findAllByBook(book);
        double sum = 0;
        for (Blog  obj: blogs)
            sum += obj.getRate();
        double averageRate = sum / blogs.size();
        updateRateBook(book,averageRate);
    }
    private void updateRateBook(Book book, double averageRate){
        Book bookRate = bookRepo.findBookById(book.getId()).
                orElseThrow(()-> new NotFoundException("No such a book found with "+ book.getId()));
        DecimalFormat format = new DecimalFormat("#.0");
        bookRate.setRating(Double.parseDouble(format.format(averageRate)));
        bookRepo.save(bookRate);
    }
}
