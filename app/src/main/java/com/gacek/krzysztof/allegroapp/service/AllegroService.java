package com.gacek.krzysztof.allegroapp.service;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface AllegroService <Request, Response> {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<Response> requestCall(@Body Request request);

}
