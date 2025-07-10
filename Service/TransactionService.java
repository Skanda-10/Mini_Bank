// Project/MiniBank/Service/TransactionService.java
package Project.MiniBank.Service;

import Project.MiniBank.Entity.Account;
import Project.MiniBank.Entity.Transaction;
import Project.MiniBank.Entity.User;
import Project.MiniBank.Repository.Acc_Rep;
import Project.MiniBank.Repository.Transaction_Rep;
import Project.MiniBank.Repository.User_Rep;
import Project.MiniBank.Exception.*; // Import your custom exceptions
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import this

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private Transaction_Rep transactionRep;

    @Autowired
    private User_Rep userRep;

    @Autowired
    private Acc_Rep accRep;

    @Transactional // Ensures atomicity for both account balance update and transaction record creation
    public Transaction createTransaction(Long userId, String type, Double amount) {
        // Validate amount early
        if (amount == null || amount <= 0) {
            throw new InvalidTransactionTypeException("Transaction amount must be positive.");
        }

        User user = userRep.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<Account> accounts = accRep.findByUser(user);

        if (accounts == null || accounts.isEmpty()) {
            throw new AccountNotFoundException("No account found for user ID: " + userId);
        }

        Account account = accounts.get(0); // Assuming one account per user

        double currentBalance = account.getBalance();

        if ("withdraw".equalsIgnoreCase(type)) {
            if (amount > currentBalance) {
                throw new InsufficientBalanceException("Insufficient balance. Current: " + currentBalance + ", Attempted withdrawal: " + amount);
            }
            account.setBalance(currentBalance - amount);
        } else if ("deposit".equalsIgnoreCase(type)) {
            account.setBalance(currentBalance + amount);
        } else {
            throw new InvalidTransactionTypeException("Invalid transaction type: '" + type + "'. Must be 'withdraw' or 'deposit'.");
        }

        // Save the updated account balance
        accRep.save(account);

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setType(type);
        tx.setAmount(amount);
        tx.setStamp(LocalDateTime.now());
        transactionRep.save(tx);

        return tx;
    }

    @Transactional(readOnly = true) // Read-only for performance and to prevent accidental modifications
    public List<Transaction> getTransByUser(Long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return transactionRep.findByUser(user);
    }
}