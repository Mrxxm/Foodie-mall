package com.kenrou.pojo.vo;

import com.kenrou.pojo.Items;
import com.kenrou.pojo.ItemsImg;
import com.kenrou.pojo.ItemsParam;
import com.kenrou.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情VO
 */
public class ItemInfoVo {

    private Items item;
    private List<ItemsImg> itemsImgList;
    private List<ItemsSpec> itemsSpecList;
    private ItemsParam itemsParam;

    public Items getItem() {
        return item;
    }

    public void setItems(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemsImgList() {
        return itemsImgList;
    }

    public void setItemsImgList(List<ItemsImg> itemsImgList) {
        this.itemsImgList = itemsImgList;
    }

    public List<ItemsSpec> getItemsSpecList() {
        return itemsSpecList;
    }

    public void setItemsSpecList(List<ItemsSpec> itemsSpecList) {
        this.itemsSpecList = itemsSpecList;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }
}
