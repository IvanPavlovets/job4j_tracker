create table if not exists items(
  id serial primary key,
  name varchar(50),
  description varchar(255)
);