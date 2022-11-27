package com.example.midtermapp.datanetwork

data class Shoes(val id: Int,
                 val name: String,
                 val price: Double,
                 val quantity: Int,
                 val imgUrl: String)


data class JsonShoes(
    val name: String,
    val imgUrl: String,
    val imgUrl_url: String,
    val price: String
)

data class ShoesNetWork(
    val name: String,
    val price: Double,
    val imgUrl: String
)

fun JsonShoes.toShoesNetWork() : ShoesNetWork {
    val priceInDouble = price.subSequence(0,price.length-2).toString().replace(".","")
    return ShoesNetWork(name,priceInDouble.toDouble(),imgUrl)
}

fun List<ShoesNetWork>.toListOfShoes() : List<Shoes> {
    var id = -1
    return map {
        id += 1
        Shoes(id,it.name,it.price,30,it.imgUrl)
    }
}