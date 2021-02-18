package com.example.list_using_mvvm

class User(){
    private var Id  :String = ""
    private var age : String = ""
    private var city : String = ""
    private var email : String = ""
    private var gender : String = ""
    private var name : String = ""

    constructor(Id : String, age : String, city : String, email : String, gender : String, name : String) : this(){
        this.Id = Id
        this.age = age
        this.city = city
        this.email = email
        this.gender = gender
        this.name = name
    }

    fun getId() : String {
        return Id
    }

    fun getAge() : String {
        return age
    }

    fun getCity() : String {
        return city
    }

    fun getEmail() : String {
        return email
    }

    fun getGender() : String {
        return gender
    }

    fun getName() : String {
        return name
    }
}