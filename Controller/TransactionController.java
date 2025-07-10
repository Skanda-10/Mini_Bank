// Project/MiniBank/Controller/TransactionController.java
package Project.MiniBank.Controller;

import Project.MiniBank.Entity.Transaction;
import Project.MiniBank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        Transaction transaction = transactionService.createTransaction(userId, "withdraw", amount);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED); // 201 Created for successful resource creation
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam Long userId, @RequestParam Double amount) {
        Transaction transaction = transactionService.createTransaction(userId, "deposit", amount);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED); // 201 Created
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransByUser(@PathVariable("userId") Long userId) {
        List<Transaction> transactions = transactionService.getTransByUser(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK); // 200 OK
    }
}