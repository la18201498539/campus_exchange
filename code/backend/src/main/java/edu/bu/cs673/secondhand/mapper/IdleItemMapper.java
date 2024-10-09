package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.IdleItemExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IdleItemMapper {
    long countByExample(IdleItemExample example);

    int deleteByExample(IdleItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IdleItem record);

    int insertSelective(IdleItem record);

    List<IdleItem> selectByExample(IdleItemExample example);

    IdleItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IdleItem record, @Param("example") IdleItemExample example);

    int updateByExample(@Param("record") IdleItem record, @Param("example") IdleItemExample example);

    int updateByPrimaryKeySelective(IdleItem record);

    int updateByPrimaryKey(IdleItem record);
}