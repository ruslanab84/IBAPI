package com.ruslan.ibapi;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService userService;

    public AccountController(AccountService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public BigDecimal getBalance(@PathVariable Long id){
       return userService.getBalance(id);
       //return user.getBalance();
    }

    @PostMapping("/putMoney/{id}/{amount}")
    public Integer putMoney(@PathVariable Long id,@PathVariable BigDecimal amount){
        return userService.putMoney(id,amount);
    }

    @PostMapping
    public Account createUser(@RequestBody Account user){
        return userService.createUser(user);
    }

    @PostMapping("/takeAmount/{id}/{amount}")
    public Integer takeMoney(@PathVariable Long id,@PathVariable BigDecimal amount){
        return userService.takeMoney(id,amount);
    }

}
