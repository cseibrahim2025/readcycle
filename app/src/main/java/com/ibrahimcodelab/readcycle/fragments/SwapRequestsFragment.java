package com.ibrahimcodelab.readcycle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.adapters.SwapRequestAdapter;
import com.ibrahimcodelab.readcycle.dao.SwapStatusUpdateRequest;
import com.ibrahimcodelab.readcycle.models.SwapResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiResponse;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwapRequestsFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swap_requests, container, false);

        recyclerView = view.findViewById(R.id.recycler_swap_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fetchSwapRequests();

        return view;
    }

    private void fetchSwapRequests() {
        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
        Call<ApiResponse<List<SwapResponse.SwapRequestData>>> call = apiService.getAllSwapRequests();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<SwapResponse.SwapRequestData>>> call,
                                   @NonNull Response<ApiResponse<List<SwapResponse.SwapRequestData>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<SwapResponse.SwapRequestData> swapRequests = response.body().getData();
                    recyclerView.setAdapter(new SwapRequestAdapter(swapRequests, swapItem -> showStatusUpdateDialog(swapItem)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<SwapResponse.SwapRequestData>>> call,
                                  @NonNull Throwable t) {
            }
        });
    }

    private void showStatusUpdateDialog(SwapResponse.SwapRequestData swapItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Update Swap Status");

        String[] statuses = {"pending","accepted","declined"};

        builder.setItems(statuses, (dialog, which) -> {
            String selectedStatus = statuses[which];
            updateSwapStatus(swapItem.getId(), selectedStatus);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateSwapStatus(int id, String status) {
        ApiService apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
        SwapStatusUpdateRequest swapStatusUpdateRequest = new SwapStatusUpdateRequest(id, status);

        Call<ApiResponse<Void>> call = apiService.updateSwapStatus(swapStatusUpdateRequest);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Void>> call, @NonNull Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    fetchSwapRequests();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Void>> call, @NonNull Throwable t) {
            }
        });
    }

}
