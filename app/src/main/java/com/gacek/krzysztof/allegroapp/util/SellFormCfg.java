package com.gacek.krzysztof.allegroapp.util;

import com.gacek.krzysztof.allegroapp.util.form.Forms;


public enum SellFormCfg {

    TITLE_FIELD(1, Forms.FormType.STRING),
    CATEGORY_FIELD(2, Forms.FormType.INTEGER),
    DESCRIPTION_FIELD(24, Forms.FormType.STRING),
    QUANTITY_FIELD(5, Forms.FormType.INTEGER),
    QUANTITY_TYPE_FIELD(28, Forms.FormType.INTEGER),

    AUCTION_TYPE_FIELD(29, Forms.FormType.INTEGER),
    AUCTION_TIME_FIELD(4, Forms.FormType.INTEGER),
    BUY_NOW_PRICE_FIELD(8, Forms.FormType.FLOAT),
    START_PRICE_FIELD(6, Forms.FormType.FLOAT),

    COUNTRY_FIELD(9, Forms.FormType.INTEGER),
    DISTRICT_FIELD(10, Forms.FormType.INTEGER),
    TOWN_FIELD(11, Forms.FormType.STRING),
    POST_CODE_FIELD(32, Forms.FormType.STRING),

    BUYER_PAID_FIELD(12, Forms.FormType.INTEGER),
    VAT_INVOICE_FIELD(14, Forms.FormType.INTEGER),

    PHOTO_1_FIELD(16, Forms.FormType.IMAGE),
    PHOTO_2_FIELD(17, Forms.FormType.IMAGE),
    PHOTO_3_FIELD(18, Forms.FormType.IMAGE),

    ALLEGRO_MINIPACZKA_INPOST_FIRST_FIELD(50, Forms.FormType.FLOAT),
    ALLEGRO_MINIPACZKA_INPOST_NEXT_FIELD(150, Forms.FormType.FLOAT),
    ALLEGRO_MINIPACZKA_INPOST_QTY_FIELD(250, Forms.FormType.INTEGER),

    ALLEGRO_PACZKOMATY_INPOST_FIRST_FIELD(59, Forms.FormType.FLOAT),
    ALLEGRO_PACZKOMATY_INPOST_NEXT_FIELD(159, Forms.FormType.FLOAT),
    ALLEGRO_PACZKOMATY_INPOST_QTY_FIELD(259, Forms.FormType.INTEGER),

    ALLEGRO_KURIER_INPOST_FIRST_FIELD(61, Forms.FormType.FLOAT),
    ALLEGRO_KURIER_INPOST_NEXT_FIELD(161, Forms.FormType.FLOAT),
    ALLEGRO_KURIER_INPOST_QTY_FIELD(261, Forms.FormType.INTEGER);


    private final int fieldId;
    private final int fieldType;

    SellFormCfg(int fieldId, int fieldType) {
        this.fieldId = fieldId;
        this.fieldType = fieldType;
    }

    public int getFieldId() {
        return fieldId;
    }

    public int getFieldType() {
        return fieldType;
    }

}
