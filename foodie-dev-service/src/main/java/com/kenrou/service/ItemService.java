package com.kenrou.service;

import com.kenrou.enums.CommentLevel;
import com.kenrou.pojo.Items;
import com.kenrou.pojo.ItemsImg;
import com.kenrou.pojo.ItemsParam;
import com.kenrou.pojo.ItemsSpec;
import com.kenrou.pojo.vo.CommentLevelCountsVO;
import com.kenrou.pojo.vo.ItemCommentVO;
import com.kenrou.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询评价数
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询评价(分页)
     */
    public PagedGridResult queryPageComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表(分页)
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品列表by分类(分页)
     */
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);
}
