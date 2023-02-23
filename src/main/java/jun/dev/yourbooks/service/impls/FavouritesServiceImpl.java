package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.NotAllowedException;
import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.FavouritesMapper;
import jun.dev.yourbooks.model.dto.FavouritesDto;
import jun.dev.yourbooks.model.entity.Favourites;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.repository.FavouriteRepo;
import jun.dev.yourbooks.service.FavouritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouritesServiceImpl implements FavouritesService {
    private final FavouriteRepo favouriteRepo;
    private final FavouritesMapper favouritesMapper;

    @Override
    public FavouritesDto select(Long bookId, User user) {
        Favourites favourites = favouritesMapper.toFavourite(bookId, user);
        favouriteRepo.save(favourites);
        return favouritesMapper.toDto(favourites);
    }

    @Override
    public void delete(Long bookId, User user) {
        Favourites favourites = favouriteRepo.findByBook_Id(bookId)
                .orElseThrow(()-> new NotFoundException("No such a favourite book found with id"+ bookId ));
        if (!favourites.getUser().getId().equals(user.getId()))
            throw new NotAllowedException("You are allowed to delete it");
        favouriteRepo.delete(favourites);
    }

    @Override
    public List<FavouritesDto> myFavouriteBooks(User user) {
        List<Favourites> favouritesList = favouriteRepo.findAllByUser(user);
        return favouritesList.stream().map(favouritesMapper::toDto).collect(Collectors.toList());
    }
}
