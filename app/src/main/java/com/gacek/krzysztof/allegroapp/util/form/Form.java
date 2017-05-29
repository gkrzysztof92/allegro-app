package com.gacek.krzysztof.allegroapp.util.form;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gacek.krzysztof.allegroapp.util.form.Forms.FormType.*;


public class Form {

    private int id;
    private int formType;
    private int returnValueType;
    private String title;
    private View view;
    @Nullable private Map<String, Integer> valueList;

    public Form() {
    }

    public Form(int id, int formType, int returnValueType, String title, View view, Map<String, Integer> valueList) {
        this.id = id;
        this.formType = formType;
        this.returnValueType = returnValueType;
        this.title = title;
        this.view = view;
        this.valueList = valueList;
    }

    public List<String> getValuesDesc() {
        List<String> valuesDesc = new ArrayList<>();
        if (valueList != null) {
            for (Map.Entry<String, Integer> entry : valueList.entrySet()) {
                valuesDesc.add(entry.getKey());
            }
        }
        return valuesDesc;
    }

    public String getValue() {
        LinearLayout layout = (LinearLayout) view;
        if (formType == STRING || formType == INTEGER || formType == FLOAT) {
            EditText editText = (EditText) layout.getChildAt(1);
            return editText.getText().toString();
        } else if (formType == CHECKBOX || formType == COMBOBOX || formType == RADIOBUTTON) {
            Spinner spinner = (Spinner) layout.getChildAt(1);
            String key = spinner.getSelectedItem().toString();
            return String.valueOf(valueList.get(key));
        }
        return null;
    }

    @Nullable
    public String getValueStr() {
        LinearLayout layout = (LinearLayout) view;
        if (formType == CHECKBOX || formType == COMBOBOX || formType == RADIOBUTTON) {
            Spinner spinner = (Spinner) layout.getChildAt(1);
            return spinner.getSelectedItem().toString();
        }
        return null;
    }

    public void setValue(String value, @Nullable String valueText) {
        LinearLayout layout = (LinearLayout) view;
        if (formType == STRING || formType == INTEGER || formType == FLOAT) {
            EditText editText = (EditText) layout.getChildAt(1);
            editText.setText(value);
        } else if (formType == CHECKBOX || formType == COMBOBOX || formType == RADIOBUTTON) {
            Spinner spinner = (Spinner) layout.getChildAt(1);
            int position = ((ArrayAdapter<String>)spinner.getAdapter()).getPosition(valueText);
            spinner.setSelection(position);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFormType() {
        return formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Nullable public Map<String, Integer> getValueList() {
        return valueList;
    }

    public void setValueList(Map<String, Integer> valueList) {
        this.valueList = valueList;
    }

    public int getReturnValueType() {
        return returnValueType;
    }

    public void setReturnValueType(int returnValueType) {
        this.returnValueType = returnValueType;
    }

}
