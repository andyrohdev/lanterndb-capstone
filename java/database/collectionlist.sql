BEGIN;

DROP TABLE IF EXISTS user_list;

CREATE TABLE collection_list (
    collection_list_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    collection_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
	genre VARCHAR(255) NOT NULL,
    CONSTRAINT FK_user FOREIGN KEY (user_id)
        REFERENCES users (user_id)
		ON DELETE CASCADE,
	CONSTRAINT FK_collection FOREIGN KEY (collection_id)
		REFERENCES collections (collection_id)
        ON DELETE CASCADE
);

COMMIT;