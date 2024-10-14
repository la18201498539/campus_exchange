package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.IdleItemService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Item Controller
 * Route: /item
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    IdleItemService itemService;

    /**
     * Search item by the label.
     */
    @RequestMapping("/label")
    public ResultVo findIdleItem(@RequestParam(value = "label",required = true) Integer label,
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums){
        if(page == null || page <= 0) {
            page = 1;
        }
        if(nums == null || nums <= 0){
            nums = 8;
        }
        return ResultVo.success(itemService.searchItemLabel(label,page,nums));
    }

    /**
     * Search item by the search value.
     */
    @RequestMapping("/search")
    public ResultVo findIdleItem(@RequestParam(value = "searchValue",required = false) String searchValue,
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums){
        if(searchValue == null) {
            searchValue = "";
        }
        if(page == null || page <= 0) {
            page = 1;
        }
        if(nums == null || nums <= 0){
            nums = 8;
        }
        return ResultVo.success(itemService.searchItem(searchValue,page,nums));
    }

    /**
     * Get all items that belongs to a user.
     * Route: /item/list
     * 
     * @param userId
     * @return
     */
    @RequestMapping("/list")
    public ResultVo getItemsByUserId(@RequestParam(value = "user_id", required = true) Long userId){
        return ResultVo.success(itemService.getAllItemByUser(userId));
    }

    /**
     * Get an item by id.
     * Route: /item
     * 
     * @param id
     * @return
     */
    @RequestMapping("")
    public ResultVo getItem(@RequestParam(value = "id", required = true)Long id) {
        return ResultVo.success(itemService.getItem(id));
    }

    /**
     * Create a new item.
     * Route: /item
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResultVo addItem(@CookieValue("shUserId") 
                                @NotNull(message = "Login fail, try again!")
                                @NotEmpty(message = "Login fail, try again!")
                                String userId,
                            @RequestBody(required = true) IdleItem item) {
        // TODO: add authorization
        item.setUserId(Long.valueOf(userId));
        item.setIdleStatus((byte) 1);
        item.setReleaseTime(new Date());
        if(itemService.addNewItem(item)){
            return ResultVo.success(item);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * Remove an item by item id,
     * not allowing user other than the creater to remove the item.
     * Route: /item
     * 
     * @param userId 
     * @param id 
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE)
    public ResultVo removeItem(@CookieValue("shUserId") 
                                @NotNull(message = "Login fail, try again!")
                                @NotEmpty(message = "Login fail, try again!")
                                String userId,
                                @RequestParam(value="id", required = true)Long id) {
        IdleItem item = itemService.getItem(id);
        // TODO: also allow admin to delete item
        if (item.getUserId().toString().equals(userId)) {
            return ResultVo.success(itemService.removeItem(id));
        }
        return ResultVo.fail(ErrorMsg.PARAM_ERROR);
    }

    /**
     * Update an exist item. Allow only the user who post the item to update.
     * 
     * @param userId
     * @param item
     * @return
     */
    @RequestMapping(method=RequestMethod.PUT)
    public ResultVo updateItem(@CookieValue("shUserId") 
                                @NotNull(message = "Login fail, try again!")
                                @NotEmpty(message = "Login fail, try again!")
                                String userId,
                                @RequestBody(required = true) IdleItem item) {
        // TODO: allow admin to update item
        if (item.getUserId().toString().equals(userId)) {
            if(itemService.updateItem(item)) {
                return ResultVo.success(item);
            }
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
        return ResultVo.fail(ErrorMsg.PARAM_ERROR);
    }

}
