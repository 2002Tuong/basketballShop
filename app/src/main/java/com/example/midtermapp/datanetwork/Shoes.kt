package com.example.midtermapp.datanetwork




data class Shoes(val id: Int,
                 val name: String,
                 val price: Double,
                 val quantity: Int,
                 val imgUrl: String)




data class ShoesFirebase(
    val id: String = "0",
    val imgUrl: String= "",
    val name: String= "",
    val price: String= "",
    val quantity: Int = 0
)
fun List<ShoesFirebase>.toListOfShoes() : List<Shoes> {

    return map {
        val price = it.price.replace(".","")
        Shoes(it.id.toInt(),it.name,price.toDouble(),it.quantity,it.imgUrl)
    }
}
fun ShoesFirebase.toShoes() : Shoes {
    val price = this.price.replace(".","")
    return Shoes(id.toInt(),name,price.toDouble(),quantity,imgUrl)
}
