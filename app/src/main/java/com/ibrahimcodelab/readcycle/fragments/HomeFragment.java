package com.ibrahimcodelab.readcycle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.adapters.CategoryAdapter;
import com.ibrahimcodelab.readcycle.models.CategoryResponse;
import com.ibrahimcodelab.readcycle.services.BookService;
import com.ibrahimcodelab.readcycle.services.BookServiceCallback;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rvCategoryList);
        new BookService().fetchCategories(getContext(), new BookServiceCallback() {
            @Override
            public void onSuccess(List<CategoryResponse> categoryResponseList) {
                CategoryAdapter adapter = new CategoryAdapter(categoryResponseList, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });

        return view;
    }
}