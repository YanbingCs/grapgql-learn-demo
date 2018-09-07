package com.proxzone.cloud.model;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-9-7 下午4:15
 */
public class User {
    private String sex;
    private String name;
    private String intro;
    private String[] skills;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
