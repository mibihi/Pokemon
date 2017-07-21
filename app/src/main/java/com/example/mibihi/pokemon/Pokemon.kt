package com.example.mibihi.pokemon

/**
 * Created by mibihi on 7/18/17.
 */
class Pokemon {
    var name:String?=null
    var des:String?=null
    var image:Int?=null
    var power:Double?=null
    var lat:Double?=null
    var long:Double?=null
    var IsCatch:Boolean?=false
    constructor(image:Int,name:String,des:String,power:Double,lat:Double,long:Double){
        this.image=image
        this.name=name
        this.des=des
        this.power=power
        this.lat=lat
        this.long=long
        this.IsCatch=false
    }

}