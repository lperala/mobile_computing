package com.example.homework1

class User{

    //var id : Int = 0
    var userName : String = ""
    var passWord : String = ""
    var location_x : Double = 0.0
    var location_y : Double = 0.0


    constructor(userName:String, passWord:String, location_x : Double, location_y : Double){
        this.userName = userName
        this.passWord = passWord
        this.location_x = location_x
        this.location_y = location_y
    }

    constructor(){
    }
}