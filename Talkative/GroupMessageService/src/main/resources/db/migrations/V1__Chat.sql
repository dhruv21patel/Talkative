CREATE TABLE IF NOT EXISTS chats (
    chatid VARCHAR(255) PRIMARY KEY,  -- ✅ This ensures `chatid` is unique
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
