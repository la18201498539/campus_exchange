package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.Address;
import edu.bu.cs673.secondhand.domain.AddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AddressMapper {
    long countByExample(AddressExample example);

    int deleteByExample(AddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Address row);

    int insertSelective(Address row);

    List<Address> selectByExampleWithRowbounds(AddressExample example, RowBounds rowBounds);

    List<Address> selectByExample(AddressExample example);

    Address selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Address row, @Param("example") AddressExample example);

    int updateByExample(@Param("row") Address row, @Param("example") AddressExample example);

    int updateByPrimaryKeySelective(Address row);

    int updateByPrimaryKey(Address row);

    int updateByUserIdSelective(Address record);

    List<Address> getDefaultAddress(Long userId);
}