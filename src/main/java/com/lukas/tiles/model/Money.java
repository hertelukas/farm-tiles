package com.lukas.tiles.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * The class which represents all money. Saved in cents
 */
public class Money implements Serializable, Comparable<Money> {
    @Serial
    private static final long serialVersionUID = -5226220695056180322L;
    private long amount;

    public Money() {

    }

    public Money(long cents) {
        amount = cents;
    }

    /**
     * @return the amount of cash in cents
     */
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void addAmount(long amount) {
        this.amount += amount;
    }

    /**
     * Sub amount only if the balance is high enough
     *
     * @param amount that gets removed in cent
     * @return if the transaction is successful
     */
    public boolean subAmountIfPossible(long amount) {
        if (this.amount < amount) {
            return false;
        }

        this.amount -= amount;
        return true;
    }

    /**
     * Sub amount only if the balance is high enough
     *
     * @param money that gets removed
     * @return if the transaction is successful
     */
    public boolean subAmountIfPossible(Money money) {
        return subAmountIfPossible(money.getAmount());
    }

    /**
     * Force the subtraction, even if balance is not high enough
     *
     * @param amount that gets removed in cent
     */
    public void subAmount(long amount) {
        this.amount -= amount;
    }

    /**
     * Force the subtraction, even if balance is not high enough
     *
     * @param amount that gets removed
     */
    public void subAmount(Money amount) {
        subAmount(amount.getAmount());
    }


    @Override
    public String toString() {
        boolean isNegative = amount < 0;

        long full = amount / 100;
        long cents = (amount - full * 100);
        String centsString = "" + Math.abs(cents);
        if (cents == 0) {
            centsString = "00";
        }
        String start = isNegative ? "-$" : "$";
        return start + Math.abs(full) + "." + centsString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public int compareTo(@NotNull Money o) {
        Long thisAmount = this.amount;
        return thisAmount.compareTo(o.amount);
    }
}
