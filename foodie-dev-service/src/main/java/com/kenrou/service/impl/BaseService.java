package com.kenrou.service.impl;

import com.github.pagehelper.PageInfo;
import com.kenrou.utils.PagedGridResult;

import java.util.List;

public class BaseService {

    protected PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
