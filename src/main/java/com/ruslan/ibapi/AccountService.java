package com.ruslan.ibapi;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository userRepository;
    private final OperationsRepository operationsRepository;

    public AccountService(AccountRepository userRepository, OperationsRepository operationsRepository) {
        this.userRepository = userRepository;
        this.operationsRepository = operationsRepository;
    }

    public Account createUser(Account user){
        return userRepository.save(user);
    }

    public int putMoney(Long id, BigDecimal amount){
        Account account = userRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
        if(account==null){
            return 0;
        }
        Operations operations = new Operations();
        operations.setAccountId(id);
        operations.setAmount(amount);
        operations.setOperDate(LocalDateTime.now());
        operations.setOperationType(0);
        account.setBalance(account.getBalance().add(amount));

        userRepository.save(account);
        operationsRepository.save(operations);
        return 1;
    }

    public BigDecimal getBalance(Long id){
        Account account = userRepository.findById(id).orElse(null);
        if(account==null){
            return BigDecimal.valueOf(-1D);
        }
        return account.getBalance();
    }

    public int takeMoney(Long id, BigDecimal amount){
        Optional<Account> account = userRepository.findById(id);
        if(account.isPresent()) {
            Account currentAccount = account.get();
            BigDecimal currentBalance = currentAccount.getBalance();
            if (currentBalance.compareTo(amount) < 0) {
                return 0;
            }
            Operations operations = new Operations();
            operations.setAccountId(id);
            operations.setAmount(amount);
            operations.setOperDate(LocalDateTime.now());
            operations.setOperationType(1);

            currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
            userRepository.save(currentAccount);
            operationsRepository.save(operations);
            return 1;
        }else {
            throw new RuntimeException("Account not found");
        }
    }

    public List<Operations> getOperationList(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate==null && endDate==null){
            return operationsRepository.findAllByAccountId(id);
        }else {
            return operationsRepository.findAllByAccountIdAndOperDateBetween(id,startDate,endDate);
        }
    }

    public int transferMoney(Long recipient, Long remitter, BigDecimal amount){
        Account accountRecipient = userRepository.findById(recipient).orElse(null);
        Account accountRemitter = userRepository.findById(remitter).orElse(null);
        if(accountRecipient != null && accountRemitter != null){
           if(accountRemitter.getBalance().compareTo(amount) > 0 && amount.signum() > 0){
               accountRemitter.setBalance(accountRemitter.getBalance().subtract(amount));
               accountRecipient.setBalance(accountRecipient.getBalance().add(amount));
               userRepository.save(accountRemitter);
               userRepository.save(accountRecipient);
               return 0;
           }
        }else {
            throw new RuntimeException("Account not found");
        }
        return 1;

    }
}
