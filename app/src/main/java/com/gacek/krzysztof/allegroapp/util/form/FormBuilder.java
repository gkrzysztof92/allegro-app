package com.gacek.krzysztof.allegroapp.util.form;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
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


public class FormBuilder {

        private Context context;

        public FormBuilder(Context context) {
            this.context = context;
        }

        public List<Form> buildCategoryForm(List<SellFormField> sellFormFields) {
            List<Form> formList = new ArrayList<>();
            for (SellFormField sellFormField : sellFormFields) {
                if (sellFormField.getSellFormCat() != 0) {
                    formList.add(buildForm(sellFormField));
                }
            }
            return formList;
        }

        private LinearLayout.LayoutParams getLayoutParams(int width, int height, int left,
                                                          int top, int right, int bottom) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            params.setMargins(left,top,right,bottom);
            params.gravity = Gravity.BOTTOM;
            return params;
        }

        private LinearLayout getLinearLayout(int orientation) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(orientation);
            return linearLayout;
        }

        private EditText getEditText(int inputType) {
            EditText editText = new EditText(context);
            editText.setRawInputType(inputType);
            return editText;
        }

        private TextView getLabel(String fieldName) {
            TextView textView = new TextView(context);
            textView.setTextAppearance(context, android.R.style.TextAppearance_Material_Medium);
            textView.setText(fieldName);
            return textView;
        }

        private Map<String, Integer> getValuesMap(String names, String values) {
            String[] name = names.split("\\|");
            String[] value = values.split("\\|");
            Map<String, Integer> valuesMap = new LinkedHashMap<>();
            if (name.length == value.length) {
                for (int i = 0; i < name.length; i++) {
                    valuesMap.put(name[i], Integer.valueOf(value[i].trim()));
                }
            }
            return valuesMap;
        }

        private Form buildForm(SellFormField sellFormField) {
            Form form = new Form();
            form.setId(sellFormField.getSellFormId());
            form.setTitle(sellFormField.getSellFormTitle());
            form.setFormType(sellFormField.getSellFormType());
            form.setReturnValueType(sellFormField.getSellFormResType());

            LinearLayout linearLayout = getLinearLayout(LinearLayout.HORIZONTAL);
            TextView textView = getLabel(sellFormField.getSellFormTitle());
            textView.setLayoutParams(getLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0,0,0,0));

            if(sellFormField.getSellFormType() == Forms.FormType.COMBOBOX ||
                    sellFormField.getSellFormType() == Forms.FormType.RADIOBUTTON) {
                sellFormField.setSellFormType(Forms.FormType.CHECKBOX);
            }

            switch (sellFormField.getSellFormType()) {
                case Forms.FormType.STRING:
                    EditText editText = getEditText(InputType.TYPE_CLASS_TEXT);
                    editText.setLayoutParams(getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 10,0,0,0));
                    linearLayout.addView(textView);
                    linearLayout.addView(editText);
                    form.setView(linearLayout);
                    break;
                case Forms.FormType.INTEGER:
                    EditText editTextNbr = getEditText(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editTextNbr.setLayoutParams(getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 10,0,0,0));
                    editTextNbr.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                    linearLayout.addView(textView);
                    linearLayout.addView(editTextNbr);
                    form.setView(linearLayout);
                    break;
                case Forms.FormType.FLOAT:
                    EditText editTextFloat = getEditText(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editTextFloat.setLayoutParams(getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 10,0,0,0));
                    editTextFloat.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                    linearLayout.addView(textView);
                    linearLayout.addView(editTextFloat);
                    form.setView(linearLayout);
                    break;
                case Forms.FormType.CHECKBOX:
                    form.setValueList(getValuesMap(sellFormField.getSellFormDesc(),
                            sellFormField.getSellFormOptsValues()));
                    Spinner spinner = getSpinner(form.getValuesDesc());
                    linearLayout.addView(textView);
                    linearLayout.addView(spinner);
                    form.setView(linearLayout);
                    break;
            }
            return form;
        }

        private Spinner getSpinner(List<String> values) {
            Spinner spinner = new Spinner(context);
            spinner.setLayoutParams(getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 10,8,0,0));
            ArrayAdapter<String> spinnerArrayAdapter =
                    new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, values);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
            return spinner;
        }

}
