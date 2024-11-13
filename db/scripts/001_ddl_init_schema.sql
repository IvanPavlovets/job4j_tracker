create table if not exists items (
  id serial primary key not null,
  name varchar(50),
  description varchar(255)
);