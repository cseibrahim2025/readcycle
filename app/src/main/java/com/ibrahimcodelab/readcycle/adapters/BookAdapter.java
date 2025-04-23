package com.ibrahimcodelab.readcycle.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.dao.SwapRequest;
import com.ibrahimcodelab.readcycle.models.Book;
import com.ibrahimcodelab.readcycle.models.SwapResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiResponse;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;

import org.aviran.cookiebar2.CookieBar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> books;
    private final Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivBookImage;
        TextView tvTitle, tvPostedBy;

        public BookViewHolder(View itemView) {
            super(itemView);
            ivBookImage = itemView.findViewById(R.id.ivBookImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.tvTitle.setText(book.title);
        holder.tvPostedBy.setText(book.user.name);

        Uri imageUri = Uri.parse(book.getPhotoPath());
        holder.ivBookImage.setImageURI(imageUri);

        holder.itemView.setOnClickListener(v -> showSwapDialog(book));
    }

    private void showSwapDialog(Book requestedBook) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_swap, null);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        TextView tvBookInfo = dialogView.findViewById(R.id.tvBookInfo);
        Spinner spinnerOfferedBook = dialogView.findViewById(R.id.spinnerOfferedBook);
        TextView btnCancel = dialogView.findViewById(R.id.btnCancel);
        TextView btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        tvBookInfo.setText("Swap with \"" + requestedBook.title + "\"");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Book b : books) {
            adapter.add(b.title);
        }

        spinnerOfferedBook.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            int selectedIndex = spinnerOfferedBook.getSelectedItemPosition();
            if (selectedIndex >= 0 && selectedIndex < books.size()) {
                int offeredBookId = books.get(selectedIndex).id;
                int requesterId = 1;
                int requestedBookId = requestedBook.id;
                createSwapRequest(requesterId, requestedBookId, offeredBookId);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void createSwapRequest(int requesterId, int requestedBookId, int offeredBookId) {
        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
        SwapRequest swapRequest = new SwapRequest(requesterId, requestedBookId, offeredBookId);

        Call<ApiResponse<SwapResponse>> call = apiService.createSwapRequest(swapRequest);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<SwapResponse>> call, @NonNull Response<ApiResponse<SwapResponse>> response) {
                if (response.isSuccessful()) {
                    CookieBar.build((Activity) context)
                            .setDuration(700)
                            .setTitle("Success")
                            .setMessage("Swap request created successfully")
                            .setBackgroundColor(R.color.color_theme)
                            .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                            .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                            .show();
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        JSONObject json = new JSONObject(errorJson);
                        String message = json.optString("message", "Something went wrong");

                        if (json.has("errors")) {
                            JSONObject errors = json.getJSONObject("errors");
                            Iterator<String> keys = errors.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONArray messages = errors.getJSONArray(key);
                                message += "\n" + key + ": " + messages.getString(0);
                            }
                        }

                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        Log.d("swap-request", "onResponse: " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<SwapResponse>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return books.size();
    }
}