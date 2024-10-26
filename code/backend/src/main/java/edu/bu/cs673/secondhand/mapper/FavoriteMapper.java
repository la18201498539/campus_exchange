package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.Favorite;
import edu.bu.cs673.secondhand.domain.FavoriteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FavoriteMapper {
    long countByExample(FavoriteExample example);

    int deleteByExample(FavoriteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Favorite row);

    int insertSelective(Favorite row);

    List<Favorite> selectByExampleWithRowbounds(FavoriteExample example, RowBounds rowBounds);

    List<Favorite> selectByExample(FavoriteExample example);

    Favorite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Favorite row, @Param("example") FavoriteExample example);

    int updateByExample(@Param("row") Favorite row, @Param("example") FavoriteExample example);

    int updateByPrimaryKeySelective(Favorite row);

    int updateByPrimaryKey(Favorite row);
}