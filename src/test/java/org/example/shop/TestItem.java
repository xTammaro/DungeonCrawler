package org.example.shop;

import shop.Item;

public class TestItem extends Item {
    public TestItem(String name, int value){
        super(name,value);
    }

    public Item makeClone() {
        return new TestItem(this.getName(),this.getValue());
    }
}
