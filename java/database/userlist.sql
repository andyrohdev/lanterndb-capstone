BEGIN;

DROP TABLE IF EXISTS user_list;

CREATE TABLE user_list (
    user_list_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    list_type INT NOT NULL,
    title VARCHAR(255) NOT NULL,
	genre VARCHAR(255) NOT NULL,
    CONSTRAINT FK_user FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
);

COMMIT;

