package com.kenrou.controller;

import com.kenrou.enums.YesOrNo;
import com.kenrou.pojo.Carousel;
import com.kenrou.pojo.Category;
import com.kenrou.pojo.vo.CategoryVO;
import com.kenrou.pojo.vo.NewItemsVO;
import com.kenrou.service.CarouselService;
import com.kenrou.service.CategoryService;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "首页", tags = {"首页相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "轮播图列表", notes = "轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> result = carouselService.queryAll(YesOrNo.YES.type);
        return IMOOCJSONResult.ok(result);
    }

    /**
     * 首页分类展示需求：
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2.如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载(懒加载)
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<Category> result = categoryService.queryAllRootLevelCat();
        return IMOOCJSONResult.ok(result);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> result = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(result);
    }

    /**
     * 最新商品
     */
    @ApiOperation(value = "获取最新商品", notes = "获取最新商品", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> result = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(result);
    }
}
