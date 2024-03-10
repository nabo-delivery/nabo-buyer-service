create extension if not exists "uuid-ossp";

create table if not exists users
(
    id                uuid default uuid_generate_v4() primary key,
    firstname         varchar(256),
    lastname          varchar(256),
    nickname          varchar(256),
    phone             varchar(256),
    delivery_address  varchar(256),
    email             varchar(256),
    password          varchar(256),
    latitude          varchar(256),
    longitude         varchar(256),
    neighborhood_name varchar(256),
    postal_code       varchar(256),
    place_id          varchar(256),
    is_test_user      varchar(256),
    created_on        timestamp with time zone,
    updated_on        timestamp with time zone,
    unique (email)
    );