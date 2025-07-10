package Project.MiniBank.Service;

import Project.MiniBank.Entity.Account;
import Project.MiniBank.Entity.User;
import Project.MiniBank.Repository.User_Rep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private User_Rep userRep;

    @Autowired
    private AccountService accountService;

    public User getUserById(Long id){
        return userRep.findById(id).orElse(null);
    }

    public User createUser(User user){
        User savedUser = userRep.save(user);

        Account account = new Account();
        account.setAccType("savings");
        account.setBalance(10000.00);
        accountService.createAcc(savedUser.getId(), account);
        System.out.println("User saved: " + savedUser.getId());
        System.out.println("Creating account for user: " + account.getAccType());

        return savedUser;

    }

    public User updateUser(Long id, User userDetails){
        Optional<User> optUser = userRep.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return userRep.save(user);
        }
        return null;
    }

    public User delUser(Long id) {
        userRep.deleteById(id);
        return null;
    }
}