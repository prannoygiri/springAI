CREATE TABLE chat_memory (
                             id VARCHAR(255) PRIMARY KEY,
                             conversation_id VARCHAR(255),
                             role VARCHAR(50),
                             content TEXT,
                             timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
