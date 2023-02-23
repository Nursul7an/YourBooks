package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.FavouritesDto;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.service.FavouritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favourites")
public class FavouritesController {
    private final FavouritesService favouritesService;
    @PostMapping("/select")
    public ResponseEntity<FavouritesDto> selectFavourite(@RequestParam Long bookId,
                                                         @AuthenticationPrincipal User user){
        return ResponseEntity.ok(favouritesService.select(bookId,user));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long bookId, @AuthenticationPrincipal User user){
        favouritesService.delete(bookId, user);
        return ResponseEntity.ok("You successfully delete.");
    }
    @GetMapping("/my/favourite/books")
    public ResponseEntity<List<FavouritesDto>> myFavouritesBook(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(favouritesService.myFavouriteBooks(user));
    }
}
