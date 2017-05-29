package com.gacek.krzysztof.allegroapp.util.form;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.dto.SellFormField;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysztof on 03/05/2017.
 */

public class Forms {

    public class FormType {
        public static final int STRING = 1;
        public static final int INTEGER = 2;
        public static final int FLOAT = 3;
        public static final int COMBOBOX = 4;
        public static final int RADIOBUTTON = 5;
        public static final int CHECKBOX = 6;
        public static final int IMAGE = 7;
        public static final int TEXTAREA = 8;
        public static final int DATETIME = 9;
        public static final int DATE = 13;
    }

}
