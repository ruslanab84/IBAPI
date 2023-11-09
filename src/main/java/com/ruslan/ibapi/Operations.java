package com.ruslan.ibapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operations")
public class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operation_id")
    private int operationId;
    @Column(name = "account_id")
    private long accountId;

    @Column(name="operation_type")
    private int operationType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "oper_date")
    private LocalDateTime operDate;
}
