package com.kenrou.controller;

import com.kenrou.pojo.*;
import com.kenrou.pojo.vo.CommentLevelCountsVO;
import com.kenrou.pojo.vo.ItemInfoVo;
import com.kenrou.service.ItemService;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品详情", tags = {"商品详情相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "商品详情页", notes = "商品详情页", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg("商品不存在");
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemsImgList);
        itemInfoVo.setItemSpecList(itemsSpecList);
        itemInfoVo.setItemParams(itemsParam);

        return IMOOCJSONResult.ok(itemInfoVo);
    }

    @ApiOperation(value = "查询商品评价等级数", notes = "查询商品评价等级数", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg("商品id不存在");
        }
        CommentLevelCountsVO result = itemService.queryCommentCounts(itemId);

        return IMOOCJSONResult.ok(result);
    }

    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",  value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "页码", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页大小", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg("商品id不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult result = itemService.queryPageComments(itemId, level, page, pageSize);

        return IMOOCJSONResult.ok(result);
    }
}
