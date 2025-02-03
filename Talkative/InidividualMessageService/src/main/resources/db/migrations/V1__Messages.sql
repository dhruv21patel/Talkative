
CREATE TABLE IF NOT EXISTS messages1 (
    messageid UUID PRIMARY KEY,
    chatid VARCHAR(255) NOT NULL,
    senderid BIGINT NOT NULL,
    message TEXT NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    sendTime TIMESTAMP NOT NULL  -- ✅ Use quotes if using camelCase
);

