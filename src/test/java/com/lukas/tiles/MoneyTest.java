package com.lukas.tiles;

import com.lukas.tiles.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MoneyTest {

    @Test
    public void testMoneyToString() {
        Money money = new Money(28234);
        String expected = "$282.34";
        assertEquals(expected, money.toString());
    }

    @Test
    public void testMoneyToString2() {
        Money money = new Money(50000);
        String expected = "$500.00";
        assertEquals(expected, money.toString());
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

    @Test
    public void negativeToString() {
        Money money = new Money(-4234);
        String expected = "-$42.34";

        assertEquals(expected, money.toString());
    }

    @Test
    public void subtractNegativeAmount() {
        Money money = new Money(10000);
        money.subAmount(-10000);

        assertEquals(20000, money.getAmount());
    }

    @Test
    public void testSort() {
        Money moneyLowest = new Money(-400);
        Money moneyZero = new Money();
        Money money = new Money(230);
        Money moneyHighest = new Money(500);

        List<Money> moneyList = new ArrayList<>();
        moneyList.add(money);
        moneyList.add(moneyLowest);
        moneyList.add(moneyHighest);
        moneyList.add(moneyZero);

        moneyList.sort(Comparator.naturalOrder());

        System.out.println(moneyList);

        assertTrue(moneyList.get(0).getAmount() <= moneyList.get(1).getAmount());
        assertTrue(moneyList.get(1).getAmount() <= moneyList.get(2).getAmount());
        assertTrue(moneyList.get(2).getAmount() <= moneyList.get(3).getAmount());
    }
}
