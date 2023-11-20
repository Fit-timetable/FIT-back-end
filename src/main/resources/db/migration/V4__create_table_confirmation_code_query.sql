CREATE TABLE confirmation_code_query (
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR NOT NULL,
    code            VARCHAR NOT NULL,
    expiration_date TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION delete_expired_codes()
RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM confirmation_code_query
    WHERE expiration_date < NOW();
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_expired_codes_trigger
AFTER INSERT ON confirmation_code_query
FOR EACH ROW
EXECUTE FUNCTION delete_expired_codes();
