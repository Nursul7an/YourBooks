package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.mapper.BookMapper;
import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.repository.BookRepo;
import jun.dev.yourbooks.service.BookService;
import jun.dev.yourbooks.util.CloudStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final CloudStorage cloudStorage;
    @Override
    public BookDto publishBook(BookRequest bookRequest, User publisher) {
        String imageUrl = checkImageAndUpload(bookRequest.getImage());
        String bookUrl = checkBookAndUpload(bookRequest.getBook());

        Book book = bookMapper.toBook(imageUrl, bookUrl, bookRequest, publisher);
        Book savedBook = bookRepo.save(book);
        return bookMapper.toDto(book);
    }
    private String checkImageAndUpload(MultipartFile file){
        if (file == null)
            return "anon.jpg";
        if (!cloudStorage.isImageFile(file))
            throw new FileException("File is not image file!");
        return cloudStorage.uploadFile(file);
    }
    private String checkBookAndUpload(MultipartFile file){
       cloudStorage.checkBook(file);
        return cloudStorage.uploadFile(file);
    }
}
