package com.gacek.krzysztof.allegroapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.dto.SellFormField;
import com.gacek.krzysztof.allegroapp.model.ItemAttribute;
import com.gacek.krzysztof.allegroapp.util.form.Form;
import com.gacek.krzysztof.allegroapp.util.form.FormBuilder;

import java.util.ArrayList;
import java.util.List;


public class CategoryFormFragment extends Fragment {

    private List<SellFormField> formData;
    private List<Form> formList;
    List<ItemAttribute> initialItemAttributes;

    public CategoryFormFragment() {

    }

    public static CategoryFormFragment newInstance(List<SellFormField> formData, List<ItemAttribute> initialItemAttributes) {
        CategoryFormFragment fragment = new CategoryFormFragment();
        fragment.setFormData(formData);
        fragment.setInitialItemAttributes(initialItemAttributes);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_form, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.category_form);
        FormBuilder formBuilder = new FormBuilder(getContext());
        formList = formBuilder.buildCategoryForm(formData);
        for (Form form : formList) {
            if(form.getView() != null) {
                linearLayout.addView(form.getView());
            }
        }
        if (initialItemAttributes != null) {
            setFormValues();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<ItemAttribute> getSelectedItemsAttributes() {
        List<ItemAttribute> itemsAttributes = new ArrayList<>();
        for (Form form : formList) {
            itemsAttributes.add(new ItemAttribute(0, 0, form.getId(), form.getTitle(),
                    form.getReturnValueType(), form.getValue(), form.getValueStr()));
        }
        return itemsAttributes;
    }

    public void setInitialItemAttributes(List<ItemAttribute> initialItemAttributes) {
        this.initialItemAttributes = initialItemAttributes;
    }

    public List<Form> getFormList() {
        return formList;
    }

    public void setFormList(List<Form> formList) {
        this.formList = formList;
    }

    public List<SellFormField> getFormData() {
        return formData;
    }

    public void setFormData(List<SellFormField> formData) {
        this.formData = formData;
    }

    private void setFormValues() {
        for (Form form : formList) {
            ItemAttribute itemAttribute = getItemAttributeByFieldId(form.getId());
            if (itemAttribute != null) {
                Log.d("FORM", itemAttribute.toString());
                form.setValue(itemAttribute.getAttributeValue(), itemAttribute.getAttributeValueStr());
            }
        }
    }

    private ItemAttribute getItemAttributeByFieldId(int fieldId) {
        for (ItemAttribute itemAttribute : initialItemAttributes) {
            if (itemAttribute.getAttributeId() == fieldId)
                return itemAttribute;
        }
        return null;
    }

}
