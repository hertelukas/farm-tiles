package com.lukas.tiles.model.finance;

import java.io.Serial;

/**
 * An unmodifiable instance of an account. Adding or removing money is not possible.
 * <p>
 * Should be used whenever possible.
 */
public final class UnmodifiableMoneyAccount extends MoneyAccount {
    @Serial
    private static final long serialVersionUID = 845250218164334512L;

    /**
     * @param cents The amount of money in cents that the account holds.
     */
    public UnmodifiableMoneyAccount(long cents) {
        super(cents);
    }

    /**
     * @param account Create a new unmodifiable account based on an editable account.
     */
    public UnmodifiableMoneyAccount(EditableMoneyAccount account) {
        super(account.amount);
    }

    /**
     * Represents an empty account.
     */
    public static UnmodifiableMoneyAccount EmptyAccount = new UnmodifiableMoneyAccount(0);
}
