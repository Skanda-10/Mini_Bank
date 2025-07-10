package Project.MiniBank.Controller;

import Project.MiniBank.Entity.Account;
import Project.MiniBank.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/user/{userId}")
    public Account createAcc(@PathVariable("userId") Long userId, @RequestBody Account account){
        return accountService.createAcc(userId, account);
    }

    @GetMapping("/{id}")
    public Account getAccById(@PathVariable("id") Long id){
        return accountService.getAccById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Account> getAccByUserId(@PathVariable("userId") Long userId){
        return accountService.getAccByUserId(userId);
    }
}
