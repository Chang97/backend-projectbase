package base.work.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    public UserDTO findById(Long id) {
        return userDAO.findById(id);
        
    }

    
}
