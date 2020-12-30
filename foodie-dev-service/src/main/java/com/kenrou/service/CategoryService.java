package com.kenrou.service;

import com.kenrou.pojo.Category;
import com.kenrou.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询一级分类
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);
}
