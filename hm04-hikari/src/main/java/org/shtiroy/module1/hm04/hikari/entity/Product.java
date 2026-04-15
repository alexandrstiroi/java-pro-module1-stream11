package org.shtiroy.module1.hm04.hikari.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(schema = "public", name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name= "balance", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    public Product() {
    }

    public Product(String accountNumber, BigDecimal balance, ProductType productType, Long userId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
