package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.Admin;
import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.AdminService;
import edu.bu.cs673.secondhand.service.IdleItemService;
import edu.bu.cs673.secondhand.service.OrderService;
import edu.bu.cs673.secondhand.service.UserServiceLegacy;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/

@RestController
@RequestMapping("/admin")
public class adminController {

    @Resource
    private AdminService adminService;

    @Resource
    private IdleItemService idleItemService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserServiceLegacy userService;

    @GetMapping("login")
    public ResultVo login(@RequestParam("accountNumber") @NotNull @NotEmpty String accountNumber,
                          @RequestParam("adminPassword") @NotNull @NotEmpty String adminPassword,
                          HttpServletResponse response
                          ){
        Admin adminModel=adminService.login(accountNumber,adminPassword);
        if (null == adminModel) {
            return ResultVo.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
//        session.setAttribute("admin",adminModel);
        Cookie cookie = new Cookie("adminId", String.valueOf(adminModel.getId()));
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        return ResultVo.success(adminModel);
    }

    @GetMapping("loginOut")
    public ResultVo loginOut( @CookieValue("adminId")
                              @NotNull(message = "Login fail, try again")
                              @NotEmpty(message = "Login fail, try again")String adminId,HttpServletResponse response){
        Cookie cookie = new Cookie("adminId",adminId);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResultVo.success();
    }

    @GetMapping("list")
    public ResultVo getAdminList(@CookieValue("adminId")
                                 @NotNull(message = "Login fail, try again.")
                                 @NotEmpty(message = "Login fail, try again.")
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums
                                 ){

        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(adminService.getAdminList(p,n));
    }

    @PostMapping("add")
    public ResultVo addAdmin(@CookieValue("adminId")
                             @NotNull(message = "Login fail, try again.")
                             @NotEmpty(message = "Login fail, try again.")
                             @RequestBody Admin adminModel
                             ){

//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        if(adminService.addAdmin(adminModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.PARAM_ERROR);
    }

    @GetMapping("idleList")
    public ResultVo idleList(@CookieValue("adminId")
                             @NotNull(message = "Login fail, try again.")
                             @NotEmpty(message = "Login fail, try again.")
                             @RequestParam("status") @NotNull @NotEmpty Integer status,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.adminGetIdleList(status,p,n));
    }

    @PostMapping("updateIdleStatus")
    public ResultVo updateIdleStatus(@CookieValue("adminId")
                                     @NotNull(message = "Login fail, try again.")
                                     @NotEmpty(message = "Login fail, try again.")
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status
    )
    {
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        IdleItem idleItemModel=new IdleItem();
        idleItemModel.setId(id);
        idleItemModel.setIdleStatus(status.byteValue());
        if(idleItemService.updateItem(idleItemModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("orderList")
    public ResultVo orderList(@CookieValue("adminId")
                              @NotNull(message = "Login fail, try again.")
                              @NotEmpty(message = "Login fail, try again.")
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(orderService.getAllOrder(p,n));
    }

    @DeleteMapping("deleteOrder")
    public ResultVo deleteOrder(@CookieValue("adminId")
                                @NotNull(message = "Login fail, try again.")
                                @NotEmpty(message = "Login fail, try again.")
                                @RequestParam("id") @NotNull @NotEmpty Long id){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        if(orderService.deleteOrder(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("userList")
    public ResultVo userList(@CookieValue("adminId")
                             @NotNull(message = "Login fail, try again.")
                             @NotEmpty(message = "Login fail, try again.")
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums,
                             @RequestParam("status") @NotNull @NotEmpty Integer status){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(userService.getUserByStatus(status,p,n));
    }

    @PostMapping("updateUserStatus")
    public ResultVo updateUserStatus(@CookieValue("adminId")
                                     @NotNull(message = "Login fail, try again.")
                                     @NotEmpty(message = "Login fail, try again.")
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        User userModel=new User();
        userModel.setId(id);
        userModel.setUserStatus(status.byteValue());
        if(userService.updateUserInfo(userModel))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }


    // Select inactive IdelItem
    @GetMapping("queryIdle")
    public ResultVo queryIdle(@RequestParam(value = "findValue",required = false) String findValue,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums,
                              @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(null==findValue){
            findValue="";
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }

        System.out.println(findValue + " " + page + " " + nums + " " + status);

        if(status == 1)
            return ResultVo.success(idleItemService.searchItem(findValue,p,n));
        return ResultVo.success(idleItemService.searchItemLabel(status,p,n));

    }

    // Select Oreder by Id
    @GetMapping("queryOrder")
    public ResultVo queryOrder(@CookieValue("adminId")
                               @NotNull(message = "Login fail, try again.")
                               @NotEmpty(message = "Login fail, try again.")
                               @RequestParam(value = "page",required = false) Integer page,
                               @RequestParam(value = "nums",required = false) Integer nums,
                               @RequestParam(value = "searchValue",required = false) String searchValue){

//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }

        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
//        System.out.println("---------------------" + searchValue + "--------------");
        if(null == searchValue || "".equals(searchValue))
            return ResultVo.success(orderService.getAllOrder(p,n));
        return ResultVo.success(orderService.findOrderByNumber(searchValue, p, n));
    }




    // select idemInfo by account
    @GetMapping("queryUser")
    public ResultVo queryUser(@CookieValue("adminId")
                              @NotNull(message = "Login fail, try again.")
                              @NotEmpty(message = "Login fail, try again.")
                              @RequestParam(value = "searchValue",required = false) String searchValue,
                              @RequestParam(value = "mode",required = false) Integer mode,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
//        if(session.getAttribute("admin")==null){
//            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
//        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
//        return ResultVo.success(userService.getUserByStatus(0,p,n));

        if(mode == 1){
            if(null == searchValue || "".equals(searchValue)){
                return ResultVo.success(userService.getUserByStatus(0,p,n));
            }else{
                return ResultVo.success(userService.getUserByNumber(searchValue,mode));
            }
        }else if(mode == 2){
            if(null == searchValue || "".equals(searchValue)){
                return ResultVo.success(userService.getUserByStatus(1,p,n));
            }else{
                return ResultVo.success(userService.getUserByNumber(searchValue,mode));
            }
        }else
            return ResultVo.success(adminService.getAdminList(p,n));

    }

}

