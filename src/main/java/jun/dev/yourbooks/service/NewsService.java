package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.NewsDto;
import jun.dev.yourbooks.model.entity.News;
import jun.dev.yourbooks.model.wraper.request.NewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface NewsService {
    NewsDto createNews(NewsRequest newsRequest);

    void delete(Long id) ;

    Page<NewsDto> findRecentNews(Pageable pageable);

    NewsDto editNews(Long bookId, NewsRequest newsRequest);
}
