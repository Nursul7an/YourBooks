package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.model.dto.FavouritesDto;
import jun.dev.yourbooks.model.entity.Favourites;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavouritesMapper {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    public Favourites toFavourite(Long bookId, User user) {
        return Favourites.builder()
                .book(bookRepo.findBookById(bookId)
                        .orElseThrow(()-> new NotFoundException("No such a book found with id " + bookId)))
                .user(user)
                .build();
    }

    public FavouritesDto toDto(Favourites favourites) {
        return FavouritesDto.builder()
                .id(favourites.getId())
                .bookDto(bookMapper.toDto(favourites.getBook()))
                .build();
    }
}
