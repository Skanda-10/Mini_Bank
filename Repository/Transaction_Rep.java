package Project.MiniBank.Repository;

import Project.MiniBank.Entity.Transaction;
import Project.MiniBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Transaction_Rep extends JpaRepository<Transaction ,Long> {
    List<Transaction> findByUser(User user);
}
