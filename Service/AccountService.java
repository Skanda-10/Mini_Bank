package Project.MiniBank.Service;

import Project.MiniBank.Entity.Account;
import Project.MiniBank.Entity.User;
import Project.MiniBank.Repository.Acc_Rep;
import Project.MiniBank.Repository.User_Rep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private Acc_Rep accountRepository;

    @Autowired
    private User_Rep userRepository;

    public Account createAcc(Long userId, Account account) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);
        return accountRepository.save(account);
    }

    public Account getAccById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUser(user);
    }
}
