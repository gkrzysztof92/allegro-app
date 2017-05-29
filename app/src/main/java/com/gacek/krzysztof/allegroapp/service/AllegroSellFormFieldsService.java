package com.gacek.krzysztof.allegroapp.service;

import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryResponseEnvelope;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface AllegroSellFormFieldsService {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<DoGetSellFormFieldsForCategoryResponseEnvelope>
                requestCall(@Body DoGetSellFormFieldsForCategoryRequestEnvelope env);

}
