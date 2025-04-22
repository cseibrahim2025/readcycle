package com.ibrahimcodelab.readcycle.dao;

import com.google.gson.annotations.SerializedName;

public class SwapStatusUpdateRequest {
    @SerializedName("swap_id")
    private int swapId;

    @SerializedName("status")
    private String status;

    public SwapStatusUpdateRequest(int swapId, String status) {
        this.swapId = swapId;
        this.status = status;
    }

    public int getSwapId() {
        return swapId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SwapStatusUpdateRequest{" +
                "swapId=" + swapId +
                ", status='" + status + '\'' +
                '}';
    }
}