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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepo newsRepo;
    private final CloudStorage cloudStorage;
    @Override
    public NewsDto createNews(NewsRequest newsRequest) {
        checkImageAndUpload(newsRequest.getFile());
        String imageUrl = cloudStorage.uploadFile(newsRequest.getFile());
        News news = newsMapper.toNewsFromRequest(newsRequest,imageUrl);
        News savedNews = newsRepo.save(news);
        return newsMapper.toDto(savedNews);
    }

    @Override
    public void delete(Long id) {
       News news = newsRepo.findById(id)
               .orElseThrow(()-> new NotFoundException("No such as news found with id " + id));
       String urlImage = news.getImageUrl();

       newsRepo.delete(news);
       String imageName = urlImage.substring(48);
       cloudStorage.deleteImage(imageName);
    }

    @Override
    public Page<NewsDto> findRecentNews(Pageable pageable) {
        var news = newsRepo.findAllByOrderByCreatedTimeDesc(pageable);
        return news.map(newsMapper::toDto);
    }

    @Override
    public NewsDto editNews(Long bookId, NewsRequest newsRequest) {
        News news = newsRepo.findById(bookId)
                .orElseThrow(()-> new NotFoundException("No such a news found with id "+ bookId));
        String image = checkImageAndUpload(newsRequest.getFile());
        checkExistingImageAndDelete(news.getImageUrl());

        News updatedNews = newsMapper.toUpdateNews(news,newsRequest, image);
        newsRepo.save(updatedNews);
        return newsMapper.toDto(updatedNews) ;
    }
    private String checkImageAndUpload(MultipartFile file){
        if (file == null)
            return "anon.jpg";
        if (!cloudStorage.isImageFile(file))
            throw new FileException("File is not image file!");
        return cloudStorage.uploadFile(file);
    }
    private void checkExistingImageAndDelete(String imageUrl){
        if (!imageUrl.equals("anon.jpg")){
            String imageName = imageUrl.substring(48);
            cloudStorage.deleteImage(imageName);
        }
    }

}
