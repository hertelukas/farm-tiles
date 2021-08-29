package com.lukas.tiles.model.finance;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract class that represents a money account. It has only the basic functionality.s
 */
public abstract class MoneyAccount implements Serializable, Comparable<MoneyAccount> {

    @Serial
    private static final long serialVersionUID = 4122192019114860655L;
    long amount;

    /**
     * A new account instance with $0.
     */
    public MoneyAccount() {

    }

    /**
     * @param cents The amount of money in cents that the account holds in the beginning.
     */
    public MoneyAccount(long cents) {
        amount = cents;
    }

    /**
     * @return Get the amount of cash in cents
     */
    public long getAmount() {
        return amount;
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

    /**
     * @param number a number in cents that should be displayed as money
     * @return a correctly formatted money string
     */
    public static String format(Number number) {
        return new EditableMoneyAccount(number.longValue()).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyAccount account = (MoneyAccount) o;
        return amount == account.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public int compareTo(@NotNull MoneyAccount o) {
        Long thisAmount = this.amount;
        return thisAmount.compareTo(o.amount);
    }
}
