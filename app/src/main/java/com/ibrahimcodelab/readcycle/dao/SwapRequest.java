package com.ibrahimcodelab.readcycle.dao;

import com.google.gson.annotations.SerializedName;

public class SwapRequest {

    @SerializedName("requester_id")
    private int requesterId;

    @SerializedName("requested_book_id")
    private int requestedBookId;

    @SerializedName("offered_book_id")
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