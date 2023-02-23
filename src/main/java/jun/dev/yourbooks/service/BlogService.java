package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface BlogService {
    BlogDto createBlog(BlogRequest blogRequest, User publisher);

    void delete(Long id, User user);

    List<BlogDto> getMyBlogs(User user);

    List<BlogDto> getBlogsByTag(Tag tag);
}
