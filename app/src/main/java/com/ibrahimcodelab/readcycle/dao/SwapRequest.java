package com.ibrahimcodelab.readcycle.dao;

import com.google.gson.annotations.SerializedName;

public class SwapRequest {

    @SerializedName("requester_id")
    private int requesterId;

    @SerializedName("book_requested_id")
    private int requestedBookId;

    @SerializedName("book_offered_id")
    private int offeredBookId;

    public SwapRequest(int requesterId, int requestedBookId, int offeredBookId) {
        this.requesterId = requesterId;
        this.requestedBookId = requestedBookId;
        this.offeredBookId = offeredBookId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public int getRequestedBookId() {
        return requestedBookId;
    }

    public int getOfferedBookId() {
        return offeredBookId;
    }
}