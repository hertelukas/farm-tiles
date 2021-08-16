package com.lukas.tiles.model;

/**
 * The class which represents all money. Saved in cents
 */
public class Money {
    private long amount;

    public Money() {

    }

    public Money(long cents) {
        amount = cents;
    }

    public long getAmount() {
        return amount;
    }

    public String getAmountAsString() {
        long full = amount / 100;
        return "$" + full + "." + (amount - full * 100);
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
     * Force the subtraction, even if balance is not high enough
     *
     * @param amount that gets removed in cent
     */
    public void subAmount(long amount) {
        this.amount -= amount;
    }


}
