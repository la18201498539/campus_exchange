package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.Favorite;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.FavoriteService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-13:26
 *****/
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Resource
    FavoriteService favoriteService;

    @PostMapping("/add")
    public ResultVo addFavorite(@CookieValue("shUserId")
                                @NotNull(message = "Login Fail, try again")
                                @NotEmpty(message = "Login Fail, try again") String shUserId,
                                @RequestBody Favorite favoriteModel){
        favoriteModel.setUserId(Long.valueOf(shUserId));
        favoriteModel.setCreateTime(new Date());
        if(favoriteService.addFavorite(favoriteModel)){
            return ResultVo.success(favoriteModel.getId());
        }
        return ResultVo.fail(ErrorMsg.FAVORITE_EXIT);
    }

    @DeleteMapping("/delete")
    public ResultVo deleteFavorite(@CookieValue("shUserId")
                                   @NotNull(message = "Login Fail, try again")
                                   @NotEmpty(message = "Login Fail, try again") String shUserId,
                                   @RequestParam Long id){
        if(favoriteService.deleteFavorite(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/check")
    public ResultVo checkFavorite(@CookieValue("shUserId")
                                  @NotNull(message = "Login Fail, try again")
                                  @NotEmpty(message = "Login Fail, try again") String shUserId,
                                  @RequestParam Long idleId){
        return ResultVo.success(favoriteService.isFavorite(Long.valueOf(shUserId),idleId));
    }

    @GetMapping("/my")
    public ResultVo getMyFavorite(@CookieValue("shUserId")
                                  @NotNull(message = "Login Fail, try again")
                                  @NotEmpty(message = "Login Fail, try again") String shUserId){
        return ResultVo.success(favoriteService.getAllFavorite(Long.valueOf(shUserId)));
    }

}
