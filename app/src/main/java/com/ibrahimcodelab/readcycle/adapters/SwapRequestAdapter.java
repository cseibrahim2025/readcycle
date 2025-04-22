package com.ibrahimcodelab.readcycle.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.models.SwapResponse;

import java.util.List;

public class SwapRequestAdapter extends RecyclerView.Adapter<SwapRequestAdapter.ViewHolder> {

    private final List<SwapResponse.SwapRequestData> swapRequests;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SwapResponse.SwapRequestData item);
    }

    public SwapRequestAdapter(List<SwapResponse.SwapRequestData> swapRequests, OnItemClickListener listener) {
        this.swapRequests = swapRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swap_request, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SwapResponse.SwapRequestData request = swapRequests.get(position);
        holder.bind(request, listener);
    }

    @Override
    public int getItemCount() {
        return swapRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title1, title2, status, user, offeredBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.txt_book_title_1);
            title2 = itemView.findViewById(R.id.txt_book_title_2);
            user = itemView.findViewById(R.id.txt_requested_by);
            offeredBy = itemView.findViewById(R.id.txt_offered_by);
            status = itemView.findViewById(R.id.txt_status);
        }

        public void bind(SwapResponse.SwapRequestData item, OnItemClickListener listener) {
            title1.setText(item.getBookOffered().getTitle());
            title2.setText(item.getBookRequested().getTitle());
            user.setText("Requested by: " + item.getRequester().getName());
            offeredBy.setText("Offered to: " + item.getBookRequested().getUser().getName());
            status.setText("Status: " + item.getStatus());

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}