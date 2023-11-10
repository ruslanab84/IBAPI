package com.ruslan.ibapi;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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

    @GetMapping("/getoperlist/{id}")
    public List<Operations> getOperationList(@PathVariable Long id,
                                             @RequestParam(required = false) LocalDateTime startDate,
                                             @RequestParam(required = false) LocalDateTime endDate){
        return userService.getOperationList(id,startDate,endDate);
    }

    @PostMapping("/transfermoney/{remid}/{recid}/{amount}")
    public int transferMoney(@PathVariable Long remid, @PathVariable Long recid, @PathVariable BigDecimal amount){
        return  userService.transferMoney(remid, recid, amount);
    }

}
