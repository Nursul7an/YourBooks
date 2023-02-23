package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.FavouritesDto;
import jun.dev.yourbooks.model.entity.User;

import java.util.List;

public interface FavouritesService {
    FavouritesDto select(Long bookId, User user);

    void delete(Long bookId, User user);

    List<FavouritesDto> myFavouriteBooks(User user);
}
