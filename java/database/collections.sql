BEGIN;

DROP TABLE IF EXISTS collections;

CREATE TABLE collections (
    collection_id SERIAL PRIMARY KEY,
    collection_name VARCHAR(255) NOT NULL
    
);

COMMIT;

