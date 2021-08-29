package com.lukas.tiles;

import com.lukas.tiles.model.finance.EditableMoneyAccount;
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
        EditableMoneyAccount money = new EditableMoneyAccount(28234);
        String expected = "$282.34";
        assertEquals(expected, money.toString());
    }

    @Test
    public void testMoneyToString2() {
        EditableMoneyAccount money = new EditableMoneyAccount(50000);
        String expected = "$500.00";
        assertEquals(expected, money.toString());
    }

    @Test
    public void testSubtraction() {
        EditableMoneyAccount money = new EditableMoneyAccount(10000); //$100.00

        //Sub $50.00
        assertTrue(money.subAmountIfPossible(5000));
        assertEquals(5000, money.getAmount());
    }

    @Test
    public void testFailedSubtraction() {
        EditableMoneyAccount money = new EditableMoneyAccount(10000); //$100.00

        //Sub $120.00
        assertFalse(money.subAmountIfPossible(12000));
        assertEquals(10000, money.getAmount());
    }

    @Test
    public void testForceSubAmount() {
        EditableMoneyAccount money = new EditableMoneyAccount(10000); //$100.00

        //Sub $120.00
        money.subAmount(12000);
        assertEquals(-2000, money.getAmount());
    }

    @Test
    public void negativeToString() {
        EditableMoneyAccount money = new EditableMoneyAccount(-4234);
        String expected = "-$42.34";

        assertEquals(expected, money.toString());
    }

    @Test
    public void subtractNegativeAmount() {
        EditableMoneyAccount money = new EditableMoneyAccount(10000);
        money.subAmount(-10000);

        assertEquals(20000, money.getAmount());
    }

    @Test
    public void testSort() {
        EditableMoneyAccount moneyLowest = new EditableMoneyAccount(-400);
        EditableMoneyAccount moneyZero = new EditableMoneyAccount();
        EditableMoneyAccount money = new EditableMoneyAccount(230);
        EditableMoneyAccount moneyHighest = new EditableMoneyAccount(500);

        List<EditableMoneyAccount> moneyList = new ArrayList<>();
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
