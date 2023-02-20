package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.exception.NotAllowedException;
import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.BookMapper;
import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.request.BookRequest;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;
import jun.dev.yourbooks.repository.BookRepo;
import jun.dev.yourbooks.service.BookService;
import jun.dev.yourbooks.util.CloudStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteBook(Long bookId, User publisher) {
        Book book = checkBook(bookId,publisher);
        bookRepo.delete(book);
        deleteFile(book.getImageUrl());
    }

    private Book checkBook(Long bookId, User publisher) {
        Book book = bookRepo.findBookById(bookId).
                orElseThrow(()-> new NotFoundException(" No such as book found with id" + bookId));
        if (!publisher.getId().equals(book.getPublisher().getId()))
            throw new NotAllowedException(" You are allowed to delete someone's book");
        return book;
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepo.findAllByOrderByCreatedTimeDesc();
        return books.stream().
                map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findBookByStyle(Style style) {
        List<Book> bookList = bookRepo.findAllByStyle(style);
        return bookList.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findBooksByAuthorOrBook(String value) {
        List<Book> books = bookRepo.findByNameAndAuthor(value);
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getMyBooks(User user) {
        List<Book> bookList = bookRepo.findAllByPublisher(user);
        return bookList.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public void deleteFile(String imageUrl){
        String imageName = imageUrl.substring(48);
        cloudStorage.deleteImage(imageName);
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
