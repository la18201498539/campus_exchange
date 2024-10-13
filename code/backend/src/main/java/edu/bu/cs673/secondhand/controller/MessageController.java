package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.Message;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.MessageService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResultVo sendMessage(@CookieValue("shUserId")
                                @NotNull(message = "Login error. Please log in again.")
                                @NotEmpty(message = "Login error. Please log in again.") String shUserId,
                                @RequestBody Message messageModel){
        messageModel.setUserId(Long.valueOf(shUserId));
        messageModel.setCreateTime(new Date());
        if(messageService.addMessage(messageModel)){
            return ResultVo.success(messageModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getMessage(@RequestParam Long id){
        return ResultVo.success(messageService.getMessage(id));
    }

    @GetMapping("/idle")
    public ResultVo getAllIdleMessage(@RequestParam Long idleId){
        return ResultVo.success(messageService.getAllIdleMessage(idleId));
    }

    @GetMapping("/my")
    public ResultVo getAllMyMessage(@CookieValue("shUserId")
                                    @NotNull(message = "Login error. Please log in again.")
                                    @NotEmpty(message = "Login error. Please log in again.") String shUserId){
        return ResultVo.success(messageService.getAllMyMessage(Long.valueOf(shUserId)));
    }

    @GetMapping("/delete")
    public ResultVo deleteMessage(@CookieValue("shUserId")
                                  @NotNull(message = "Login error. Please log in again.")
                                  @NotEmpty(message = "Login error. Please log in again.") String shUserId,
                                  @RequestParam Long id){
        if(messageService.deleteMessage(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

}
