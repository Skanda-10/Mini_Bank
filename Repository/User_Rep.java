package Project.MiniBank.Repository;

import Project.MiniBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_Rep extends JpaRepository<User,Long> {
}
