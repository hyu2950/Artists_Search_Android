package com.example.application01.favorite;

public class FavoriteModel {
    String name;
    String birthday;
    String nationality;
    String id;

    public FavoriteModel(String name, String birthday, String nationality,String id) {
        this.name = name;
        this.birthday = birthday;
        this.nationality = nationality;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
