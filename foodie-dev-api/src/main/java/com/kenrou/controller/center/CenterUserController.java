package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.service.center.CenterUserService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "我的信息", tags = {"我的信息"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;


    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(@RequestParam String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  BindingResult bindingResult,
                                  HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = getErrors(bindingResult);
            return IMOOCJSONResult.errorMap(errorMap);
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        Users handleUser = setNullProperty(user);

        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(handleUser), true);

        // TODO 后续要改，增加令牌token，整合redis

        return IMOOCJSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {

        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error: errorList) {
            String errorFiled = error.getField();
            String errorMsg = error.getDefaultMessage();

            map.put(errorFiled, errorMsg);
        }

        return map;
    }
}
