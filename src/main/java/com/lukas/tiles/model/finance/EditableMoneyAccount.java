package com.lukas.tiles.model.finance;

import java.io.Serial;

/**
 * The class which represents all money. Saved in cents.
 */
public final class EditableMoneyAccount extends MoneyAccount {

    @Serial
    private static final long serialVersionUID = 3847731897859200723L;

    /**
     * A new money instance with $0
     */
    public EditableMoneyAccount() {
        super();
    }

    /**
     * @param cents the amount of money in cents that the object holds in the beginning
     */
    public EditableMoneyAccount(long cents) {
        super(cents);
    }

    /**
     * This method should be used when creating an account based on a template.
     *
     * @param account An unmodifiable money account with a given amount.
     */
    public EditableMoneyAccount(UnmodifiableMoneyAccount account) {
        super(account.getAmount());
    }

    /**
     * @return Get the current balance in cents.
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Adds a given amount to the current balance.
     *
     * @param amount The amount that should be added to the account in cents.
     */
    public void addAmount(long amount) {
        this.amount += amount;
    }

    /**
     * Sub amount only if the balance is high enough.
     *
     * @param amount The amount that should be removed in cents.
     * @return Whether the transaction was successful.
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
     * @param account The amount that should be removed in cents.
     * @return Whether the transaction was successful.
     */
    public boolean subAmountIfPossible(EditableMoneyAccount account) {
        return subAmountIfPossible(account.getAmount());
    }

    /**
     * Force the subtraction, even if balance is not high enough
     *
     * @param amount The amount that gets removed in cents.
     */
    public void subAmount(long amount) {
        this.amount -= amount;
    }

    /**
     * Force the subtraction, even if balance is not high enough
     *
     * @param account The account that gets subtracted.
     */
    public void subAmount(UnmodifiableMoneyAccount account) {
        subAmount(account.getAmount());
    }
}
