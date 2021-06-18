create table if not exists cars (
    id serial primary key,
    mark varchar(255),
    model varchar(255),
    body varchar(255),
    price double precision
);

create table photo (
    id serial primary key,
    path varchar(255)
);

create table users(
    id serial primary key,
    name varchar(255),
    email varchar(255) not null unique,
    password varchar(255)
);

create table ads (
    id serial primary key,
    description text,
    car_id int references cars(id),
    photo_id int references photo(id),
    active boolean,
    user_id int references users(id),
    created timestamp
);

