package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.service.ItemService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:50
 *****/
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping("/list")
    public ResultVo getItemsByUserId(@RequestParam(value = "user_id", required = true) Long userId){
        return ResultVo.success(itemService.getItemListByUserId(userId));
    }

}
