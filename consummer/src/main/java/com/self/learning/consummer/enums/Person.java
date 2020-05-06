package com.self.learning.consummer.enums;

public enum Person implements PersonInterface{
    PERSON_A("无名氏A",22,"男"),
    PERSON_B("无名氏B",21,"女");

    String name;

    int age;

    String sex;

    Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getAge() {
        return null;
    }

    @Override
    public String getSex() {
        return null;
    }
}
