package com.gacek.krzysztof.allegroapp.util;

import java.util.Arrays;
import java.util.List;


public enum QtyType {

    PIECE_TYPE("Sztuki", 0),
    KIT_TYPE("Komplety", 1),
    PAIR_TYPE("Pary", 2);

    private final String name;
    private final int id;

    QtyType(String typeName, int qtyId) {
        this.name = typeName;
        this.id = qtyId;
    }

    public String getTypeName() {
        return name;
    }

    public int getQtyId() {
        return id;
    }

    public static List<String> getTypeNamesList() {
        return Arrays.asList(PIECE_TYPE.getTypeName(), KIT_TYPE.getTypeName(),
                PAIR_TYPE.getTypeName());
    }

    public static int getIdFromName(String typeName) {
        for (QtyType qtyType: QtyType.values()) {
            if (qtyType.getTypeName().equals(typeName)) {
                return qtyType.getQtyId();
            }
        }
        return -1;
    }

}
