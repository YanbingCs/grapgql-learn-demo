package com.proxzone.cloud.model;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:27
 */
public class DogInterface implements IAnimal {
    private String name;
    private int legs;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }
}
