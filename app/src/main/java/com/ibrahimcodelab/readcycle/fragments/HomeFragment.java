package com.ibrahimcodelab.readcycle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.adapters.CategoryAdapter;
import com.ibrahimcodelab.readcycle.models.CategoryResponse;
import com.ibrahimcodelab.readcycle.services.BookService;
import com.ibrahimcodelab.readcycle.services.BookServiceCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rvCategoryList);
        adapter = new CategoryAdapter(new ArrayList<>(), getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new BookService().fetchCategories(getContext(), new BookServiceCallback() {
            @Override
            public void onSuccess(List<CategoryResponse> categoryResponseList) {
                adapter.setCategories(categoryResponseList);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}