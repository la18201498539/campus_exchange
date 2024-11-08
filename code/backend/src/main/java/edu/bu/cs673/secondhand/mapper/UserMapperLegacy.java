package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/
@Mapper
public interface UserMapperLegacy {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User userLogin(@Param("email") String email, @Param("userPassword") String userPassword);

    User selectByPrimaryKey(Long id);

    Long selectByUserName(String nickname);

    List<User> getUserList();

    List<User> findUserByList(List<Long> idList);

    List<User> getNormalUser(int begin, int nums);

    List<User> getBanUser(int begin, int nums);

    List<User> getUserByNumber(String searchValue, int mode);

    int countNormalUser();

    int countBanUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePassword(@Param("newPassword") String newPassword,
                       @Param("oldPassword") String oldPassword,@Param("id") Long id);

    @Update("UPDATE sh_user SET is_active = 1, updated_at = NOW() WHERE activation_token = #{token}")
    int activateUser(@Param("token") String token);

    @Select("SELECT active_code FROM sh_user WHERE email = #{email}")
    String findActiveCodeByEmail(String email);

    @Delete("DELETE FROM sh_user WHERE email = #{email}")
    void deleteUserByEmail(String email);

    @Update("UPDATE sh_user SET active_code = #{activeCode} WHERE email = #{email}")
    int insertActiveCodeByEmail(@Param("activeCode") String activeCode, @Param("email") String email);
}
