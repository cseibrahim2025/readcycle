package com.ibrahimcodelab.readcycle.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
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
        SimpleDraweeView imgBookOffered, imgBookRequested;
        TextView txtTitleBookOffered, txtTitleBookRequested;
        TextView txtNameOfferedBy, txtNameRequestedTo, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBookOffered = itemView.findViewById(R.id.imgOfferedBook);
            imgBookRequested = itemView.findViewById(R.id.imgRequestedBook);

            txtTitleBookOffered = itemView.findViewById(R.id.txtTitleOfferedBook);
            txtTitleBookRequested = itemView.findViewById(R.id.txtTitleRequestedBook);

            txtNameOfferedBy = itemView.findViewById(R.id.txtOfferedBy);
            txtNameRequestedTo = itemView.findViewById(R.id.txtRequestedTo);

            status = itemView.findViewById(R.id.txt_status);
        }

        public void bind(SwapResponse.SwapRequestData item, OnItemClickListener listener) {

            imgBookOffered.setImageURI(Uri.parse(item.getBookOffered().getPhotoPath()));
            imgBookRequested.setImageURI(Uri.parse(item.getBookRequested().getPhotoPath()));

            txtTitleBookOffered.setText(item.getBookOffered().getTitle());
            txtTitleBookRequested.setText(item.getBookRequested().getTitle());

            txtNameOfferedBy.setText(item.getRequester().getName());
            txtNameRequestedTo.setText(item.getBookRequested().getUser().getName());

            status.setText("Status: " + item.getStatus());
            status.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), item.getStatus().equals("accepted") ? R.color.green_200 : item.getStatus().equals("declined") ? R.color.red_200 : R.color.orange_200));

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}