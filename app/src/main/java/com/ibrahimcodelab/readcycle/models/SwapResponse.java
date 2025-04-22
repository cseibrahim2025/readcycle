package com.ibrahimcodelab.readcycle.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SwapResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<SwapRequestData> data;

    public boolean isStatus() {
        return status;
    }

    public List<SwapRequestData> getData() {
        return data;
    }

    public static class SwapRequestData {
        @SerializedName("id")
        private int id;

        @SerializedName("book_requested_id")
        private int bookRequestedId;

        @SerializedName("book_offered_id")
        private int bookOfferedId;

        @SerializedName("requester_id")
        private int requesterId;

        @SerializedName("status")
        private String status;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("book_requested")
        private Book bookRequested;

        @SerializedName("book_offered")
        private Book bookOffered;

        @SerializedName("requester")
        private User requester;

        public int getId() {
            return id;
        }

        public int getBookRequestedId() {
            return bookRequestedId;
        }

        public int getBookOfferedId() {
            return bookOfferedId;
        }

        public int getRequesterId() {
            return requesterId;
        }

        public String getStatus() {
            return status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Book getBookRequested() {
            return bookRequested;
        }

        public Book getBookOffered() {
            return bookOffered;
        }

        public User getRequester() {
            return requester;
        }
    }
}