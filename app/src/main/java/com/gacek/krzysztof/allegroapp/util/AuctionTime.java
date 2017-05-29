package com.gacek.krzysztof.allegroapp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof on 29/05/2017.
 */

public enum AuctionTime {

    DAYS_3("3 dni", 0),
    DAYS_5("5 dni", 1),
    DAYS_7("7 dni", 2),
    DAYS_10("10 dni", 3),
    DAYS_14("14 dni", 4),
    DAYS_30("30 dni", 5),
    OUT_STOCK("Do wyczerpania zapasow", 99);

    private final String name;
    private final int id;

    AuctionTime(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static List<String> getNamesList() {
        List<String> list = new ArrayList<>();
        for (AuctionTime auctionLength: AuctionTime.values()) {
            list.add(auctionLength.getName());
        }
        return list;
    }

    public static int getIdFromName(String typeName) {
        for (AuctionTime auctionLength: AuctionTime.values()) {
            if (auctionLength.getName().equals(typeName)) {
                return auctionLength.getId();
            }
        }
        return -1;
    }

}
