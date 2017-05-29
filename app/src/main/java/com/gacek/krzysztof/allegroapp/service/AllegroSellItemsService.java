package com.gacek.krzysztof.allegroapp.service;

import com.gacek.krzysztof.allegroapp.dto.DoGetMySellItemsRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetMySellItemsResponseEnvelope;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface AllegroSellItemsService {

        @Headers({
                "Content-Type: application/soap+xml",
                "Accept-Charset: utf-8"
        })
        @POST("service.php")
        Observable<DoGetMySellItemsResponseEnvelope>
        requestCall(@Body DoGetMySellItemsRequestEnvelope env);


}
