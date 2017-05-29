package com.gacek.krzysztof.allegroapp.service;

import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryResponseEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoNewAuctionExtRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoNewAuctionExtResponseEnvelope;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface AllegroNewAuctionService {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("service.php")
    Observable<DoNewAuctionExtResponseEnvelope>
        requestCall(@Body DoNewAuctionExtRequestEnvelope env);

}
