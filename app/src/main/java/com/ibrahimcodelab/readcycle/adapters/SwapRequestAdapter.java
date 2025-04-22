package com.ibrahimcodelab.readcycle.adapters;

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

    public SwapRequestAdapter(List<SwapResponse.SwapRequestData> swapRequests) {
        this.swapRequests = swapRequests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swap_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SwapResponse.SwapRequestData request = swapRequests.get(position);

        String bookTitle = request.getBookRequested().getTitle();
        String requesterName = request.getRequester().getName();
        String status = request.getStatus();

        holder.titleOne.setText(bookTitle);
        holder.titleTwo.setText(request.getBookOffered().getTitle());
        holder.requester.setText("Requested by: " + requesterName);
        holder.offerer.setText("Offered by: " + request.getBookOffered().getUser().getName());
        holder.status.setText("Status: " + status);
    }

    @Override
    public int getItemCount() {
        return swapRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleOne, titleTwo, requester, offerer, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOne = itemView.findViewById(R.id.txt_book_title_1);
            titleTwo = itemView.findViewById(R.id.txt_book_title_2);
            requester = itemView.findViewById(R.id.txt_requested_by);
            offerer = itemView.findViewById(R.id.txt_offered_by);
            status = itemView.findViewById(R.id.txt_status);
        }
    }
}