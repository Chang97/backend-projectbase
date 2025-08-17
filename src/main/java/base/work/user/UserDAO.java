package base.work.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    UserDTO findById(Long id);
}
