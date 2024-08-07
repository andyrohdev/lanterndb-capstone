BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('demoadmin','$2a$10$AElBp1FoERum/I7CWY.9MeG8v7M5i2o4oiMbYw3dqivwUgrFkG516','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('demouser','$2a$10$M.oh0H0qnXya8AHnz.BOr.hP4RfaFHFrubEL/p78ZJ7o22o4GLMR2','ROLE_USER');
INSERT INTO collections (collection_name) VALUES ('Wish List'), ('Currently Playing'), ('Played');
/*INSERT INTO collection_list (user_id, collection_id, title, genre) VALUES (3, 1, 'Border Lands', 'Looter Shooter');
*/

COMMIT TRANSACTION;
