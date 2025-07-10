package Project.MiniBank.Repository;

import Project.MiniBank.Entity.Account;
import Project.MiniBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Acc_Rep extends JpaRepository<Account,Long> {
    List<Account> findByUser(User user);
}
