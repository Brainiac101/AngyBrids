//package com.angybrids.blocks;
//
//import com.badlogic.gdx.graphics.Texture;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//
//public class Blocks {
//    private Class<? extends Bar> bar;
//
//    public Blocks(Class<? extends Bar> bar) {
//        this.bar = bar;
//    }
//
//    public ArrayList<Texture> generateBlock() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Constructor<? extends Bar> constructor = bar.getConstructor(boolean.class);
//        ArrayList<Texture> blocks = new ArrayList<>();
//        Bar newbar1 = constructor.newInstance(false);
//        Bar newbar2 = constructor.newInstance(false);
//        Bar newbar3 = constructor.newInstance(true);
//        Bar newbar4 = constructor.newInstance(true);
//        blocks.add(newbar1.getBar());
//        blocks.add(newbar2.getBar());
//        blocks.add(newbar3.getBar());
//        blocks.add(newbar4.getBar());
//        return blocks;
//    }
//}
