package com.gacek.krzysztof.allegroapp;

import android.util.Log;
import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryRequestEnvelope;
import com.gacek.krzysztof.allegroapp.service.AllegroCategoryService;
import com.gacek.krzysztof.allegroapp.service.AllegroSellFormFieldsService;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Krzysztof on 02/05/2017.
 */
public class ItemSellFields {

    @Test
    public void test() {

        DoGetSellFormFieldsForCategoryRequestEnvelope env
                = new DoGetSellFormFieldsForCategoryRequestEnvelope();
        env.setCountryId(1);
        env.setCategoryId(256097);
        env.setWebapiKey("s00b86fc");

        AllegroSellFormFieldsService serv =
                AllegroServiceFactory.createService(AllegroSellFormFieldsService.class);
        serv.requestCall(env)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("Form", "Success");
                });
    }

}
