package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.Admin;
import edu.bu.cs673.secondhand.domain.AdminExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AdminMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Admin row);

    int insertSelective(Admin row);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Admin row);

    int updateByPrimaryKey(Admin row);
   
    Admin login(@Param("accountNumber") String accountNumber, @Param("adminPassword") String adminPassword);
    List<Admin> getList(@Param("offset") int offset, @Param("limit") int limit);
    int getCount();
}
