
CREATE TABLE IF NOT EXISTS messages (
    chatid VARCHAR(255) PRIMARY KEY, -- ✅ Now `chatid` is the primary key
    senderid BIGINT NOT NULL,
    message TEXT NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    send_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);