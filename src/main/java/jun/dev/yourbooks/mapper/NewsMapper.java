package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.model.dto.NewsDto;
import jun.dev.yourbooks.model.entity.News;
import jun.dev.yourbooks.model.dto.request.NewsRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewsMapper {
    public News toNewsFromRequest(NewsRequest newsRequest, String imageUrl) {
        return News.builder()
                .title(newsRequest.getTitle())
                .description(newsRequest.getDescription())
                .imageUrl(imageUrl)
                .createdTime(LocalDateTime.now())
                .build();
    }

    public NewsDto toDto(News savedNews) {
        return NewsDto.builder()
                .id(savedNews.getId())
                .title(savedNews.getTitle())
                .description(savedNews.getDescription())
                .imageUrl(savedNews.getImageUrl())
                .createdTime(savedNews.getCreatedTime())
                .build();
    }

    public News toUpdateNews(News news, NewsRequest newsRequest,String image) {
        news.setTitle(newsRequest.getTitle());
        news.setDescription(newsRequest.getDescription());
        news.setImageUrl(image);
        return news;
    }
}
