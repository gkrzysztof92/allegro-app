package com.gacek.krzysztof.allegroapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.model.Category;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    Subject<Category> selectedCategory = PublishSubject.create();

    public CategoryAdapter() {
    }

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Subject<Category> getSelectedCategory() {
        return selectedCategory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryNameText.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryNameText;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryNameText = (TextView) itemView.findViewById(R.id.category_list_row_categoryName);
            itemView.setOnClickListener( l -> {
                Log.d("Cat", String.valueOf(categoryList.get(getAdapterPosition()).getId()));
                selectedCategory.onNext(categoryList.get(getAdapterPosition()));
            });
        }
    }

}
