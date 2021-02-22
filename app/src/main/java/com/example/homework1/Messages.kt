package com.example.homework1

import java.sql.Date

class Messages {
    var id_message : Int = 0
    var message : String = ""
    var location_x : String = ""
    var location_y : String = ""
    var reminder_time : String = ""
    var creation_time : String = ""
    var creator_id : String = ""
    var reminder_seen : Int  = 0

    constructor(message : String, location_x : String, location_y : String, reminder_time : String, creation_time : String, creator_id : String, reminder_seen : Int){
        //this.id_message = id_message
        this.message = message
        this.location_x = location_x
        this.location_y = location_y
        this.reminder_time = reminder_time
        this.creation_time = creation_time
        this.creator_id = creator_id
        this.reminder_seen = reminder_seen
    }

    constructor(){
    }
}