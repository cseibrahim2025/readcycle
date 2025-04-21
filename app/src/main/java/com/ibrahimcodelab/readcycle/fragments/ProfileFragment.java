package com.ibrahimcodelab.readcycle.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.dao.BookRequest;
import com.ibrahimcodelab.readcycle.models.Category;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiResponse;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private List<Category> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        view.findViewById(R.id.btn_add_book).setOnClickListener(v -> openAddBookDialog());

        return view;
    }

    private void openAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_book, null);
        builder.setView(dialogView);

        EditText edtTitle = dialogView.findViewById(R.id.edt_title);
        EditText editDescription = dialogView.findViewById(R.id.edt_description);
        EditText edtImageUrl = dialogView.findViewById(R.id.edt_image);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinner_category);
        Button btnSubmit = dialogView.findViewById(R.id.btn_submit);

        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        spinnerCategory.setAdapter(categoryAdapter);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        fetchCategories();

        btnSubmit.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String description = editDescription.getText().toString().trim();
            String image = edtImageUrl.getText().toString().trim();
            int selectedPosition = spinnerCategory.getSelectedItemPosition();

            if (title.isEmpty() || selectedPosition < 0 || selectedPosition >= categories.size()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int categoryId = categories.get(selectedPosition).getId();
            BookRequest bookRequest = new BookRequest(
                    title,
                    null,
                    categoryId,
                    1,
                    description
            );
            addBookToServer(bookRequest);
            dialog.dismiss();
        });
    }

    private void fetchCategories() {
        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
        Call<ApiResponse<List<Category>>> call = apiService.getCategories();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body().getData();
                    List<String> categoryNames = new ArrayList<>();
                    for (Category category : categories) {
                        categoryNames.add(category.getName());
                    }
                    categoryAdapter.clear();
                    categoryAdapter.addAll(categoryNames);
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBookToServer(BookRequest bookRequest) {

        Log.d("addBook", "addBookToServer: " + bookRequest);

        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);

        Call<ApiResponse<Object>> call = apiService.createBook(bookRequest);
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(requireContext(), "Book added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                t.printStackTrace();
                // Network error
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}