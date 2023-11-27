create table blacklisted_token
(
    id         uuid primary key,
    keep_until timestamp with time zone not null check ( keep_until > now() )
);

CREATE FUNCTION delete_expired_tokens_from_blacklist() RETURNS trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE FROM blacklisted_token WHERE keep_until < NOW();
    RETURN NEW;
END;
$$;

CREATE TRIGGER delete_expired_tokens_from_blacklist_trigger
    AFTER INSERT
    ON blacklisted_token
EXECUTE PROCEDURE delete_expired_tokens_from_blacklist();