package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.BlogDto;
import jun.dev.yourbooks.model.dto.request.BlogRequest;
import jun.dev.yourbooks.model.entity.User;

public interface BlogService {
    BlogDto createBlog(BlogRequest blogRequest, User publisher);
}
