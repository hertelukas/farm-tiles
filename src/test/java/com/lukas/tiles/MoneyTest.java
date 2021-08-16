package com.lukas.tiles;

import com.lukas.tiles.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MoneyTest {

    @Test
    public void testMoneyToString() {
        Money money = new Money(28234);
        String expected = "$282.34";
        assertEquals(expected, money.getAmountAsString());
    }

    @Test
    public void testSubtraction() {
        Money money = new Money(10000); //$100.00

        //Sub $50.00
        assertTrue(money.subAmountIfPossible(5000));
        assertEquals(5000, money.getAmount());
    }

    @Test
    public void testFailedSubtraction() {
        Money money = new Money(10000); //$100.00

        //Sub $120.00
        assertFalse(money.subAmountIfPossible(12000));
        assertEquals(10000, money.getAmount());
    }

    @Test
    public void testForceSubAmount() {
        Money money = new Money(10000); //$100.00

        //Sub $120.00
        money.subAmount(12000);
        assertEquals(-2000, money.getAmount());
    }
}
