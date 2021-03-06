package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
public final class Money {

    @Id
    @GeneratedValue
    private long id;
    private Currency currency;
    private BigDecimal amount;


    public static final Money ZERO=new Money(Currency.USD, 0);

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_DOWN);
    }


    public Money(){}

    public Money add(final Money money){
        if(currency!=money.getCurrency()){
            throw new IllegalArgumentException();
        }
        return new Money( currency,amount.doubleValue()+money.getAmount());
    }
    
    public Money times(double mult){
        return new Money(currency, amount.doubleValue()*mult);
    }

    public Money minus(final Money money){
        if(currency!=money.getCurrency() || amount.doubleValue()<money.getAmount()){
            throw new IllegalArgumentException();
        }
        return new Money( currency,amount.doubleValue()-money.getAmount());
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return currency == money.currency && Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
