package com.gacek.krzysztof.allegroapp.service;

import com.gacek.krzysztof.allegroapp.dto.DoGetCatsDataRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetCatsDataResponseEnvelope;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface AllegroCategoryService {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<DoGetCatsDataResponseEnvelope> requestCall(@Body DoGetCatsDataRequestEnvelope doGetCatsDataRequestEnvelope);
}
