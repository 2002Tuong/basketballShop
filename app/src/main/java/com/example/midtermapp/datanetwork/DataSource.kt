package com.example.midtermapp.datanetwork


import android.util.Log



import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.tasks.await


object DataSource {

//    fun loadShoes(context : Context) : LiveData<List<Shoes>>{
//        val job = Job()
//        var data = MutableLiveData<List<Shoes>>()
//         CoroutineScope(job + Dispatchers.IO).launch {
//             data.postValue(FirebaseDatabase().getListShoes().toListOfShoes())
//        }
//        return data
//    }
    fun loadLocations(): List<ShopLocation> {
        return listOf<ShopLocation>(
            ShopLocation(LatLng(10.765706, 106.665205),"phụ kiện bóng rổ", "289 Nguyễn Tiểu La, Phường 8, Quận 10, Thành phố Hồ Chí Minh 70000"),
            ShopLocation(LatLng(10.975378, 106.666411),"phụ kiện bóng rổ", " 69 Đường D4, Phú Thọ, Thủ Dầu Một, Tỉnh Bình Dương"),
            ShopLocation(LatLng(10.834102, 106.664143),"phụ kiện bóng rổ", " 149 Thống Nhất, Phường 10, Gò Vấp, Thành phố Hồ Chí Minh"),
            ShopLocation(LatLng(16.031825824423564, 108.22202156826673),"phụ kiện bóng rổ", " 17 Hồ Nguyên Trừng, Hoà Cường Nam, Hải Châu, Đà Nẵng 550000"),
            ShopLocation(LatLng(16.056397387986955, 108.24119261856619),"phụ kiện bóng rổ", "  93A Nguyễn Duy Hiệu, An Hải Đông, Sơn Trà, Đà Nẵng 550000"),
            ShopLocation(LatLng(21.006703, 105.816044),"phụ kiện bóng rổ", "  Số 208 Đ. Láng, Thượng Đình, Thanh Xuân, Hà Nội")
        )
    }
}


class FirebaseDatabase {
    private val db = Firebase.firestore
    val shoesCollection = db.collection("shoes");

    suspend fun getListShoes() : List<ShoesFirebase> {
        return try {
            shoesCollection.get().await().toObjects(ShoesFirebase::class.java)
        }catch (e : Exception) {
            Log.d("Firebase", e.message ?: "error")
            emptyList()
        }
    }
    suspend fun retrieveShoes(id: Int): ShoesFirebase {

        return try {
            val data =  shoesCollection.whereEqualTo("id", id.toString())
                .get()
                .await().toObjects(ShoesFirebase::class.java)
            Log.d("Firebase","data retrieve is ${data.toString()}")
            data[0]
        }catch (e : Exception) {
            ShoesFirebase(price = "0")
        }
    }

    suspend fun updateShoes(id:Int, newShoes: Map<String, Any>) {
        val snapshot = shoesCollection.whereEqualTo("id", id.toString())
            .get()
            .await()
        if(snapshot.documents.isNotEmpty()) {
            for(document in snapshot.documents) {
                try {
                    shoesCollection.document(document.id).set(
                        newShoes,
                        SetOptions.merge()
                    ).await()
                } catch (e:Exception) {
                    Log.d("Firebase",e.message ?: "cannot update")
                }

            }
        }
    }
}