BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO collections (collection_name) VALUES ('Wish List'), ('Currently Playing'), ('Played');
/*INSERT INTO collection_list (user_id, collection_id, title, genre) VALUES (3, 1, 'Border Lands', 'Looter Shooter');
*/

COMMIT TRANSACTION;
