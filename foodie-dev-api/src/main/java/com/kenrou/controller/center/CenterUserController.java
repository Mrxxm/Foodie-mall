package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.resource.FileUpload;
import com.kenrou.service.center.CenterUserService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "我的信息", tags = {"我的信息"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "更新用户头像", notes = "更新用户头像", httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
                                    @ApiParam(name = "userId", value = "用户Id", required = true)
                                    @RequestParam String userId,
                                   @ApiParam(name = "file", value = "用户头像", required = true)
                                   MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) {

        // 头像保存地址
//        String fileSpace = IMAGE_USER_FACE_LOCATION; // 原有方式
        String fileSpace = fileUpload.getImageUserFaceLocation(); // 资源文件配置方式

        String uploadPathPrefix = File.separator + userId;
        String showPath = "";
        // 文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 重命名 imooc-face.png -> ["imooc-face", "png"]
                    String fileNameArr[] = fileName.split("\\.");
                    // 获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    // 验证图片后缀
                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg")) {
                        return IMOOCJSONResult.errorMsg("图片格式不正确");
                    }
                    // 文件名称重组 face-{userId}.suffix (覆盖式上传) (增量式：额外拼接当前时间)
                    String newFileName = "face-" + userId + "." + suffix;
                    // 上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    showPath = userId + File.separator + newFileName;
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        outFile.getParentFile().mkdirs();
                    }
                    // 文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }

        Long time = System.currentTimeMillis() / 1000;
        String faceUrl = fileUpload.getImageServerUrl() + File.separator + showPath + "?t=" + time;
        Users user = centerUserService.updateUserFace(userId, faceUrl);
        Users handleUser = setNullProperty(user);

        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(handleUser), true);

        return IMOOCJSONResult.ok();
    }

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
