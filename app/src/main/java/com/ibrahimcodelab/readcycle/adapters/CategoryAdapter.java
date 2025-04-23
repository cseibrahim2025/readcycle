package com.ibrahimcodelab.readcycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.models.CategoryResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<CategoryResponse> categories;
    private final Context context;

    public CategoryAdapter(List<CategoryResponse> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView bookRecycler;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvCategoryName);
            bookRecycler = itemView.findViewById(R.id.rvBooks);
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_with_books, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryResponse category = categories.get(position);
        holder.categoryName.setText(category.name);

        BookAdapter adapter = new BookAdapter(category.books, context);
        holder.bookRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.bookRecycler.setAdapter(adapter);
    }

    public void setCategories(List<CategoryResponse> categories){
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}