package com.ibrahimcodelab.readcycle.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivBookImage;
        TextView tvTitle, tvPostedBy, tvCategory;

        public BookViewHolder(View itemView) {
            super(itemView);
            ivBookImage = itemView.findViewById(R.id.ivBookImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
            tvCategory = itemView.findViewById(R.id.tvCategory);
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
        holder.tvPostedBy.setText("Posted by: " + book.user.name);

        // Load image with Fresco
        Uri imageUri = Uri.parse(book.getPhotoPath());
        holder.ivBookImage.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}