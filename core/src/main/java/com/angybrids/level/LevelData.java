package com.angybrids.level;

import com.angybrids.blocks.*;
import com.angybrids.pigs.*;

import java.util.List;
import java.util.ArrayList;

public class LevelData {
    List<String> myBird;
    List<Pig> myPig;
    List<Block> blockCollection;
    int level;

    public LevelData() {
        myBird = new ArrayList<>();
        myPig = new ArrayList<>();
        blockCollection = new ArrayList<>();
    }

    public List<String> getMyBird() {
        return myBird;
    }

    public void setMyBird(List<String> myBird) {
        this.myBird = myBird;
    }

    public List<Pig> getMyPig() {
        return myPig;
    }

    public void setMyPig(List<Pig> myPig) {
        this.myPig = myPig;
    }

    public List<Block> getBlockCollection() {
        return blockCollection;
    }

    public void setBlockCollection(List<Block> blockCollection) {
        this.blockCollection = blockCollection;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}
