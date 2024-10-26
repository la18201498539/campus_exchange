package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.Address;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.AddressService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-15:49
 *****/
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/info")
    public ResultVo getAddress(@CookieValue("shUserId")
                                @NotNull(message = "Login Fail, try again")
                                @NotEmpty(message = "Login Fail, try again") String shUserId,
                               @RequestParam(value = "id",required = false) Long id){

        if(null==id){
            return ResultVo.success(addressService.getAddressByUser(Long.valueOf(shUserId)));
        }else {
            return ResultVo.success(addressService.getAddressById(id,Long.valueOf(shUserId)));
        }
    }

    @PostMapping("/add")
    public ResultVo addAddress(@CookieValue("shUserId")
                               @NotNull(message = "Login Fail, try again")
                               @NotEmpty(message = "Login Fail, try again") String shUserId,
                               @RequestBody Address addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.addAddress(addressModel)){
            return ResultVo.success(addressModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/update")
    public ResultVo updateAddress(@CookieValue("shUserId")
                                  @NotNull(message = "Login Fail, try again")
                                  @NotEmpty(message = "Login Fail, try again") String shUserId,
                                  @RequestBody Address addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.updateAddress(addressModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/delete")
    public ResultVo deleteAddress(@CookieValue("shUserId")
                                  @NotNull(message = "Login Fail, try again")
                                  @NotEmpty(message = "Login Fail, try again") String shUserId,
                                  @RequestBody Address addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.deleteAddress(addressModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

}
