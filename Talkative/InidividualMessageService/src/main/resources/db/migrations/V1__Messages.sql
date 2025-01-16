CREATE TABLE IF NOT EXISTS Messages (
    MessageID UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Random UUID as primary key
    ChatID VARCHAR(255) NOT NULL,                        -- Chat ID as string
    SenderID BIGINT NOT NULL,                            -- Sender ID as long
    Message TEXT NOT NULL,                               -- Message content as string
    Seen BOOLEAN DEFAULT FALSE,                          -- Seen status as boolean
    Send_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP        -- Send timestamp
);
