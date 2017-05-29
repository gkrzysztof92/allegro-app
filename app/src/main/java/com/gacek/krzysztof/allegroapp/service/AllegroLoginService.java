package com.gacek.krzysztof.allegroapp.service;


import com.gacek.krzysztof.allegroapp.dto.DoLoginRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoLoginResponseEnvelope;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface AllegroLoginService {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<DoLoginResponseEnvelope> requestCall(@Body DoLoginRequestEnvelope doLoginRequestEnvelope);

}
