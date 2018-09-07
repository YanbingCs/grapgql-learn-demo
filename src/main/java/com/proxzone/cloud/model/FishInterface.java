package com.proxzone.cloud.model;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:25
 */
public class FishInterface implements IAnimal {
    private String tailColor;
    private String name;

    public String getTailColor() {
        return tailColor;
    }

    public void setTailColor(String tailColor) {
        this.tailColor = tailColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
