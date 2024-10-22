CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   title TEXT not null unique,
   description TEXT not null,
   created TIMESTAMP,
   done BOOLEAN
);
