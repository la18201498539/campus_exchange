package edu.bu.cs673.secondhand.mapper;

import edu.bu.cs673.secondhand.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/15/24-15:42
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
}
