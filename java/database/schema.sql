BEGIN TRANSACTION;

DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS collection_list;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
CREATE TABLE collections (
    collection_id SERIAL PRIMARY KEY,
    collection_name VARCHAR(255) NOT NULL
    
);
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
CREATE TABLE reviews (
	review_id SERIAL PRIMARY KEY,
	game_id INT NOT NULL,
	user_id INT NOT NULL,
	review_text VARCHAR(255) NOT NULL,
	CONSTRAINT FK_user FOREIGN KEY (user_id)
		REFERENCES users (user_id)
			ON DELETE CASCADE
);



COMMIT TRANSACTION;


