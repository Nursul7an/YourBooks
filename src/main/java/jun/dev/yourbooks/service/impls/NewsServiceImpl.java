package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.NewsMapper;
import jun.dev.yourbooks.model.dto.NewsDto;
import jun.dev.yourbooks.model.entity.News;
import jun.dev.yourbooks.model.wraper.request.NewsRequest;
import jun.dev.yourbooks.repository.NewsRepo;
import jun.dev.yourbooks.service.NewsService;
import jun.dev.yourbooks.util.CloudStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepo newsRepo;
    private final CloudStorage cloudStorage;
    @Override
    public NewsDto createNews(NewsRequest newsRequest) {
        if (!cloudStorage.isImageFile(newsRequest.getFile()))
            throw new FileException("This file is not image");
        String imageUrl = cloudStorage.uploadFile(newsRequest.getFile());
        News news = newsMapper.toNewsFromRequest(newsRequest,imageUrl);
        News savedNews = newsRepo.save(news);
        return newsMapper.toDto(savedNews);
    }

    @Override
    public void delete(Long id) {
       News news = newsRepo.findById(id)
               .orElseThrow(()-> new NotFoundException("No such as news found with id " + id));
       newsRepo.delete(news);
    }

    @Override
    public Page<NewsDto> findRecentNews(Pageable pageable) {
        var news = newsRepo.findAllByOrderByCreatedTimeDesc(pageable);
        return news.map(newsMapper::toDto);
    }
}
