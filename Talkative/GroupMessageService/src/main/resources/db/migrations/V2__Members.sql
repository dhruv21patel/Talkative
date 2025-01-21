CREATE TABLE IF NOT EXISTS group_members (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chatid VARCHAR(255) NOT NULL,
    userid BIGINT NOT NULL,
    role VARCHAR(50) DEFAULT 'member',
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(chatid, userid),  -- ✅ Prevents duplicate memberships
    FOREIGN KEY (chatid) REFERENCES chats(chatid) ON DELETE CASCADE  -- ✅ Links members to a valid chat
);
