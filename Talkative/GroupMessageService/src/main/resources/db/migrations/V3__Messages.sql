CREATE TABLE IF NOT EXISTS messages (
    messageid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chatid VARCHAR(255) NOT NULL,
    senderid BIGINT NOT NULL,
    message TEXT NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    send_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chatid) REFERENCES chats(chatid) ON DELETE CASCADE  -- ✅ Links messages to a valid chat
);
