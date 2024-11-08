package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.OrderAddress;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.OrderAddressService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-16:29
 *****/
@RestController
@RequestMapping("/order-address")
public class OrderAddressController {

    @Resource
    OrderAddressService orderAddressService;


    @PostMapping("/add")
    public ResultVo addOrderAddress(@CookieValue("shUserId")
                                    @NotNull(message = "Login Fail, try again")
                                    @NotEmpty(message = "Login Fail, try again") String shUserId,
                                    @RequestBody OrderAddress orderAddressModel){
        return ResultVo.success(orderAddressService.addOrderAddress(orderAddressModel));
    }

    @PostMapping("/update")
    public ResultVo updateOrderAddress(@CookieValue("shUserId")
                                       @NotNull(message = "Login Fail, try again")
                                       @NotEmpty(message = "Login Fail, try again") String shUserId,
                                       @RequestBody OrderAddress orderAddressModel){
        if(orderAddressService.updateOrderAddress(orderAddressModel)){
            return ResultVo.success(orderAddressModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getOrderAddress(@CookieValue("shUserId")
                                    @NotNull(message = "Login Fail, try again")
                                    @NotEmpty(message = "Login Fail, try again") String shUserId,
                                    @RequestParam Long orderId){
        return ResultVo.success(orderAddressService.getOrderAddress(orderId));
    }

}
