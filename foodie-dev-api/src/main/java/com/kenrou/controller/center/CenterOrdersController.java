package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "我的信息", tags = {"我的信息"})
@RestController
@RequestMapping("myorders")
public class CenterOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询我的订单", notes = "查询我的订单", httpMethod = "POST")
    @PostMapping("query")
    public IMOOCJSONResult query(@RequestParam String userId,
                                 @RequestParam Integer orderStatus,
                                 @ApiParam(name = "page", value = "页码", required = false)
                                 @RequestParam Integer page,
                                 @ApiParam(name = "pageSize", value = "分页大小", required = false)
                                 @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("用户不存在");
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult result = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return IMOOCJSONResult.ok(result);
    }


}
