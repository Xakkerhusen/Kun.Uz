CREATE
OR REPLACE FUNCTION article_status_count_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    if
TG_OP = 'INSERT'  then
        if NEW.status = 'LIKE'  then
update article
set like_count = like_count + 1
where id = NEW.article_id;
return NEW;
else
update article
set dislike_count = dislike_count + 1
where id = NEW.article_id;
return NEW;
end if;
    elseif
TG_OP = 'DELETE' then
        if OLD.status = 'LIKE'  then
update article
set like_count = like_count - 1
where id = OLD.article_id;
return OLD;
else
update article
set dislike_count = dislike_count - 1
where id = OLD.article_id;
return OLD;
end if;
    elseif
TG_OP = 'UPDATE' then
        -- OLD
        if OLD.status = 'LIKE'  then
update article
set like_count = like_count - 1
where id = OLD.article_id;
else
update article
set dislike_count = dislike_count - 1
where id = OLD.article_id;
end if;
        -- NEW
        if
NEW.status = 'LIKE'  then
update article
set like_count = like_count + 1
where id = NEW.article_id;
else
update article
set dislike_count = dislike_count + 1
where id = NEW.article_id;
end if;
return NEW;
end if;
END;$$;

CREATE TRIGGER article_status_count_trigger
    BEFORE INSERT OR
UPDATE OR
DELETE
ON article_like
    FOR EACH ROW
    EXECUTE PROCEDURE article_status_count_trigger_function();