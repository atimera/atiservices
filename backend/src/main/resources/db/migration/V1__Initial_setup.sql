CREATE TABLE ville(
    id BIGSERIAL PRIMARY KEY,
    nom TEXT NOT NULL,
    code_postal INT NOT NULL UNIQUE,
    departement TEXT NOT NULL
);