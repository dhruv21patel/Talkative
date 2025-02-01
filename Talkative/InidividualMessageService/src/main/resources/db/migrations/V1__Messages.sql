
CREATE TABLE IF NOT EXISTS messages1 (
    chatid VARCHAR(255) PRIMARY KEY, -- ✅ Now `chatid` is the primary key
    senderid BIGINT NOT NULL,
    message TEXT NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    sendTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

