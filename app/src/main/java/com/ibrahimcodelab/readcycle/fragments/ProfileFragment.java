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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.dao.BookRequest;
import com.ibrahimcodelab.readcycle.models.Category;
import com.ibrahimcodelab.readcycle.models.UserResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiResponse;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;
import com.ibrahimcodelab.readcycle.utils.UserSession;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private List<Category> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;

    private UserResponse.User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.btn_add_book).setOnClickListener(v -> openAddBookDialog());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = new UserSession(getContext()).getUser().getUser();

        TextView txtUserName = view.findViewById(R.id.txt_name);
        txtUserName.setText(user.getName());
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    image,
                    categoryId,
                    user.getId(),
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
            public void onResponse(@NonNull Call<ApiResponse<List<Category>>> call, @NonNull Response<ApiResponse<List<Category>>> response) {
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
            public void onFailure(@NonNull Call<ApiResponse<List<Category>>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBookToServer(BookRequest bookRequest) {

        Log.d("addBook", "addBookToServer: " + bookRequest);

        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);

        Call<ApiResponse<Object>> call = apiService.createBook(bookRequest);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Object>> call, @NonNull Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    CookieBar.build(requireActivity())
                            .setDuration(1500)
                            .setTitle("Success")
                            .setMessage("Book added successfully")
                            .setBackgroundColor(R.color.color_theme)
                            .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                            .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                            .show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}