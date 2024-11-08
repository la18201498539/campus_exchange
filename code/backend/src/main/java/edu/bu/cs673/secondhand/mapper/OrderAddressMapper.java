package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.OrderAddress;
import edu.bu.cs673.secondhand.domain.OrderAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderAddressMapper {
    long countByExample(OrderAddressExample example);

    int deleteByExample(OrderAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderAddress row);

    int insertSelective(OrderAddress row);

    List<OrderAddress> selectByExampleWithRowbounds(OrderAddressExample example, RowBounds rowBounds);

    List<OrderAddress> selectByExample(OrderAddressExample example);

    OrderAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") OrderAddress row, @Param("example") OrderAddressExample example);

    int updateByExample(@Param("row") OrderAddress row, @Param("example") OrderAddressExample example);

    int updateByPrimaryKeySelective(OrderAddress row);

    int updateByPrimaryKey(OrderAddress row);
}