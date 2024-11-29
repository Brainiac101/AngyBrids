package com.angybrids;

import com.angybrids.level.Level;
import static org.junit.Assert.*;
import com.angybrids.pages.ShopPage;
import com.angybrids.powerUps.Inventory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameTester {
    private Level level;

    @Before
    public void setup() {
        level = new Level("x");
    }

    @Test
    public void testWinUpdatesCoins() {
        ShopPage.setCoins(0);
        assertEquals(100, level.handleWin());
    }

    @Test
    public void testWinUpdatesCoin() {
        ShopPage.setCoins(0);
        level.pigCounter = 0;
        level.birdCounter = 0;
        assertEquals(100, level.handleWin());
    }

    @Test
    public void testLossUpdatesCoins() {
        ShopPage.setCoins(0);
        level.pigCounter = 2;
        level.birdCounter = 0;
        assertEquals(0, level.handleLoss());
    }

    @Test
    public void testBirdGroundContact(){
        level.bodiesToDelete=new ArrayList<>();
        level.birdGroundContact();
        assertEquals("BIRD HP DECREASED TO 0", level.alert);
    }

    @Test
    public void testInventory(){
        Inventory.inventory.put("test",3);
        assertEquals(3,Inventory.getItemCount("test"));
    }

}

