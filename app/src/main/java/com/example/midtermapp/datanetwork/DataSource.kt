package com.example.midtermapp.datanetwork

import android.content.Context
import android.util.Log
import com.example.midtermapp.R
import com.google.android.gms.maps.model.LatLng

object DataSource {
    fun loadShoes(context : Context) : List<Shoes>{
        val reader = ShoesReader(context).read()

        return reader.toListOfShoes()
    }
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