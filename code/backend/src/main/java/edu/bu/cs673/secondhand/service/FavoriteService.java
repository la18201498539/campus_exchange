package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.Favorite;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-13:08
 *****/
public interface FavoriteService {

    /**
     *  add favorite item + user
     * @param favoriteModel
     * @return
     */
    boolean addFavorite(Favorite favoriteModel);

    /**
     *
     * @param id
     * @return
     */
    boolean deleteFavorite(Long id);

    /**
     *
     * @param userId
     * @param idleId
     * @return
     */
    Integer isFavorite(Long userId,Long idleId);

    /**
     *
     * @param userId
     * @return
     */
    List<Favorite> getAllFavorite(Long userId);
}
