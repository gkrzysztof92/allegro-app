package com.gacek.krzysztof.allegroapp.service;

import com.gacek.krzysztof.allegroapp.dto.DoGetMySellItemsRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetMySellItemsResponseEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetStatesInfoRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetStatesInfoResponseEnvelope;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface AllegroStatesService {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<DoGetStatesInfoResponseEnvelope>
    requestCall(@Body DoGetStatesInfoRequestEnvelope env);

}
