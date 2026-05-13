package com.radi.demo7;

public class Review {
    private int id;
    private int userId;
    private int productId;
    private String content;
    private String username;
    private String createdAt;

    public Review() {}

    public Review(int id, int userId, int productId, String content, String username, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public String getContent() { return content; }
    public String getUsername() { return username; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setContent(String content) { this.content = content; }
    public void setUsername(String username) { this.username = username; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}