package com.example.message.model

class Message {
    var msg : String? = null
    var senderId : String? = null

    constructor(){}
    constructor(msg:String? , senderId : String?){
        this.msg = msg
        this.senderId = senderId
    }

}