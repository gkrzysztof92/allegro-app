package com.gacek.krzysztof.allegroapp;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.adapter.CategoryAdapter;
import com.gacek.krzysztof.allegroapp.data.repo.CategoriesRepository;
import com.gacek.krzysztof.allegroapp.model.Category;

import java.util.List;
import java.util.Stack;


public class CategoryActivity extends AppCompatActivity {

    public static final String CATEGORY_PATH_TAG = "categoryPath";
    public static final String CATEGORY_ID_TAG = "categoryId";

    private List<Category> categoryList;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private Toolbar toolbar;

    CategoriesRepository categoriesRepository;
    TextView categoryPathText;
    Stack<Category> categoryStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoriesRepository.initInstance(getContentResolver());
        categoriesRepository = CategoriesRepository.getInstance();
        categoryList = categoriesRepository.findByParentId(0);
        recyclerView = (RecyclerView) findViewById(R.id.category_recyclerView);
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryPathText = (TextView) findViewById(R.id.category_catPath);
        categoryPathText.setText("");
        categoryStack = new Stack<>();

        setUpCategoriesObservable();
        setUpRecyclerView();
        setUpToolbar();
    }

    // TODO: Extract lambda body to method
    public void setUpCategoriesObservable() {
        categoryAdapter.getSelectedCategory()
                .subscribe(data -> {
                    categoryStack.push(data);
                    categoryPathText.setText(renderCurrentPath(categoryStack));
                    List<Category> categories = categoriesRepository.findByParentId(data.getId());
                    if (categories.isEmpty()) {
                        onCategorySelect();
                    } else {
                        categoryAdapter.setCategoryList(categories);
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void onCategorySelect() {
        Intent intent = new Intent();
        intent.putExtra(CATEGORY_PATH_TAG, renderCurrentPath(categoryStack));
        intent.putExtra(CATEGORY_ID_TAG, categoryStack.peek().getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);
    }

    public void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wybierz Kategorie");

        toolbar.setNavigationOnClickListener( l -> {
            if (!categoryStack.isEmpty()) {
                categoryList = categoriesRepository
                        .findByParentId(categoryStack.pop().getCategoryParent());
                categoryPathText.setText(renderCurrentPath(categoryStack));
                categoryAdapter.setCategoryList(categoryList);
                categoryAdapter.notifyDataSetChanged();
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public String renderCurrentPath(Stack<Category> categoryStack) {
        String path = "";
        for(Category cat: categoryStack) {
            if (path.isEmpty()) {
                path = cat.getCategoryName();
            } else {
                path += " -> " + cat.getCategoryName();
            }
        }
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.categories_toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);
        /*SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Category> filterdCategories = new ArrayList<>();
                for (Category cat: categoryList) {
                    if(newText != null && cat.getCategoryName().toLowerCase().contains(newText.toLowerCase())) {
                        filterdCategories.add(cat);
                    }
                }
                categoryAdapter.setCategoryList(filterdCategories);
                categoryAdapter.notifyDataSetChanged();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
