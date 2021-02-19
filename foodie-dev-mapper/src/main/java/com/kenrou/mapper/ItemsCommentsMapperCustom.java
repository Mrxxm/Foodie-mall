package com.kenrou.mapper;

import com.kenrou.my.mapper.MyMapper;
import com.kenrou.pojo.ItemsComments;
import com.kenrou.pojo.vo.center.CenterItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public List<CenterItemCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}