package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.Order;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.OrderService;
import edu.bu.cs673.secondhand.utils.IdFactoryUtil;
import edu.bu.cs673.secondhand.utils.OrderTaskHandler;
import edu.bu.cs673.secondhand.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/***
 Email: ybinman@bu.edu
 DateTime: 10/13/24-13:12
 *****/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo addOrder(@CookieValue("shUserId")
                             @NotNull(message = "Login Fail, try again")
                             @NotEmpty(message = "Login Fail, try again") String shUserId,
                             @RequestBody Order order){
        if(OrderTaskHandler.orderService==null){
            OrderTaskHandler.orderService=orderService;
        }
        order.setOrderNumber(IdFactoryUtil.getOrderId());
        order.setCreateTime(new Date());
        order.setUserId(Long.valueOf(shUserId));
        order.setOrderStatus((byte) 0);
        order.setPaymentStatus((byte)0);
        if(orderService.addOrder(order)){
            return ResultVo.success(order);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getOrderInfo(@CookieValue("shUserId")
                                 @NotNull(message = "Login Fail, try again")
                                 @NotEmpty(message = "Login Fail, try again") String shUserId,
                                 @RequestParam Long id){
        Order orderModel=orderService.getOrder(id);
        if(orderModel.getUserId().equals(Long.valueOf(shUserId))||
                orderModel.getIdleItem().getUserId().equals(Long.valueOf(shUserId))){
            return ResultVo.success(orderModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/update")
    public ResultVo updateOrder(@CookieValue("shUserId")
                                @NotNull(message = "Login Fail, try again")
                                @NotEmpty(message = "Login Fail, try again") String shUserId,
                                @RequestBody Order orderModel,
                                @RequestParam(value = "price",required = false) String price){

        if(orderModel.getPaymentStatus()!=null&&orderModel.getPaymentStatus().equals((byte) 1)){
            orderModel.setPaymentTime(new Date());
        }
        if(orderService.updateOrder(orderModel)){
            return ResultVo.success(orderModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/my")
    public ResultVo getMyOrder(@CookieValue("shUserId")
                                   @NotNull(message = "Login Fail, try again")
                                   @NotEmpty(message = "Login Fail, try again") String shUserId){
        return ResultVo.success(orderService.getMyOrder(Long.valueOf(shUserId)));
    }

    @GetMapping("/my-sold")
    public ResultVo getMySoldIdle(@CookieValue("shUserId")
                                  @NotNull(message = "Login Fail, try again")
                                  @NotEmpty(message = "Login Fail, try again") String shUserId){
        return ResultVo.success(orderService.getMySoldIdle(Long.valueOf(shUserId)));
    }
}
