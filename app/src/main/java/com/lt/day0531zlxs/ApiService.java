package com.lt.day0531zlxs;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String Base_Url = "https://a.zhulong.com/";

    @GET("openapi/ad/getAd?positions_id=APP_QD_01&is_show=0&w=1080&h=2160&specialty_id=21")
    Observable<Bean> getData();
}
