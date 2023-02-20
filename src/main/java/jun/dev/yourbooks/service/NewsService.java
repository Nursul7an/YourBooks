package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.NewsDto;
import jun.dev.yourbooks.model.dto.request.NewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsDto createNews(NewsRequest newsRequest);

    void delete(Long id) ;

    Page<NewsDto> findRecentNews(Pageable pageable);

    NewsDto editNews(Long bookId, NewsRequest newsRequest);
}
