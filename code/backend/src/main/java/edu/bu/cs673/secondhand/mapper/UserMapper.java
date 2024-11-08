package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.domain.UserExample;
import java.util.List;

import edu.bu.cs673.secondhand.model.UserModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User row);

    int insertSelective(User row);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") User row, @Param("example") UserExample example);

    int updateByExample(@Param("row") User row, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    User findByEmail(String email);

    User findByEmailAndToken(String email, String activationToken);

    boolean existsByAccountNumber(String accountNumber);

    List<User> findUserByList(List<Long> idList);

    @Select("SELECT active_code FROM sh_user WHERE email = #{email}")
    String findActiveCodeByEmail(String email);

    @Update("UPDATE sh_user SET active = 1 WHERE email = #{email}")
    void activateUser(String email);

    @Delete("DELETE FROM sh_user WHERE email = #{email}")
    void deleteUserByEmail(String email);

}
