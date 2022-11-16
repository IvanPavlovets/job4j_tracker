create table if not exists participates (
    id serial primary key,
    item_id INT NOT NULL REFERENCES items(id),
    user_id INT NOT NULL REFERENCES j_user(id)
);