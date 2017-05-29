package com.gacek.krzysztof.allegroapp.factory;

import android.util.Log;

import com.gacek.krzysztof.allegroapp.dto.AuctionAttributeField;
import com.gacek.krzysztof.allegroapp.model.Item;
import com.gacek.krzysztof.allegroapp.model.ItemAttribute;
import com.gacek.krzysztof.allegroapp.util.SellFormCfg;
import com.gacek.krzysztof.allegroapp.util.form.Forms;

import java.util.ArrayList;
import java.util.List;


public class AuctionAttributeFieldFactory {

    public static AuctionAttributeField getInstance(ItemAttribute attribute) {

        return getInstance(attribute.getAttributeId(), attribute.getAttributeType(),
                    attribute.getAttributeValue());
    }

    public static AuctionAttributeField getInstance(int fieldId, int itemType, String value) {

            AuctionAttributeField auctionAttributeField = new AuctionAttributeField();
            auctionAttributeField.setFid(fieldId);

            switch (itemType) {
                case Forms.FormType.STRING:
                    auctionAttributeField.setFvalueString(value);
                    break;
                case Forms.FormType.INTEGER:
                    auctionAttributeField.setFvalueInt(Integer.parseInt(value));
                    break;
                case Forms.FormType.FLOAT:
                    auctionAttributeField.setFvalueFloat(Float.parseFloat(value));
                    break;
                case Forms.FormType.DATETIME:
                    break;
                case Forms.FormType.DATE:
                    break;
                case Forms.FormType.IMAGE:
                    auctionAttributeField.setFvalueImage(value);
                default:
                    Log.e("NEW_AUCTION", "UNKNOWN RETURNING VALUE");
            }
            return auctionAttributeField;
    }

    public static List<AuctionAttributeField> mapItemToAuctionAttributeList(Item item) {

        List<AuctionAttributeField> auctionAttributeFields = new ArrayList<>();
        auctionAttributeFields.add(getInstance(SellFormCfg.TITLE_FIELD.getFieldId(),
                SellFormCfg.TITLE_FIELD.getFieldType(), item.getName()));
        auctionAttributeFields.add(getInstance(SellFormCfg.CATEGORY_FIELD.getFieldId(),
                SellFormCfg.CATEGORY_FIELD.getFieldType(), String.valueOf(item.getCategoryId())));
        auctionAttributeFields.add(getInstance(SellFormCfg.DESCRIPTION_FIELD.getFieldId(),
                SellFormCfg.DESCRIPTION_FIELD.getFieldType(),item.getDescription()));
        auctionAttributeFields.add(getInstance(SellFormCfg.QUANTITY_FIELD.getFieldId(),
                SellFormCfg.QUANTITY_FIELD.getFieldType(),String.valueOf(item.getQuantity())));
        return auctionAttributeFields;
    }

}
