package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.UsersMaster;
import ph.com.project01.webportal.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMasterService {

    @Autowired
    private UserMasterRepository userMasterRepository;

    public UsersMaster login(String accountId, String password) {
        UsersMaster user = userMasterRepository.findByAccountId(accountId);
        if (user != null && password.equals(user.getPassword())) { 
            return user; 
        }
        return null; 
    }
}
