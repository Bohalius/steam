create table developers
(
    id          integer generated by default as identity
        constraint developers_pkey
            primary key,
    first_name  varchar(255),
    info        varchar(255),
    last_name   varchar(255),
    middle_name varchar(255)
);

alter table developers
    owner to postgres;

create table games
(
    id               integer generated by default as identity
        constraint games_pkey
            primary key,
    name             varchar(255),
    price            numeric(19, 2),
    publishing varchar(255)
);

alter table games
    owner to postgres;

create table game_has_developer
(
    game_id   integer not null
        constraint fkq7t4b1gg1eqpjo0kom4t8vktl
            references games
            on update cascade on delete cascade,
    developer_id integer not null
        constraint fk8oktfipod4dby36a7wj285yn8
            references developers
            on update cascade on delete cascade
);

alter table game_has_developer
    owner to postgres;

create table categories
(
    id   integer generated by default as identity
        constraint categories_pkey
            primary key,
    name varchar(255)
);

alter table categories
    owner to postgres;

create table game_has_category
(
    game_id     integer not null
        constraint fktoynsl1vx9orlcuol20u48si8
            references games
            on update cascade on delete cascade,
    category_id integer not null
        constraint fk5cs1uc6w5jhddv9p4e6kro5hd
            references categories
            on update cascade on delete cascade
);

alter table game_has_category
    owner to postgres;

create table clients
(
    id          integer generated by default as identity
        constraint clients_pkey
            primary key,
    address     varchar(255),
    email       varchar(255),
    first_name  varchar(255),
    last_name   varchar(255),
    middle_name varchar(255),
    phone       varchar(255)
);

alter table clients
    owner to postgres;

create table roles
(
    id   integer generated by default as identity
        constraint roles_pkey
            primary key,
    name varchar(20)
);

alter table roles
    owner to postgres;

create table users
(
    id       integer generated by default as identity
        constraint users_pkey
            primary key,
    password varchar(120),
    username varchar(20)
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique
);

alter table users
    owner to postgres;

create table accounts
(
    id        integer generated by default as identity
        constraint accounts_pkey
            primary key,
    balance   numeric(19, 2),
    is_active boolean not null,
    client_id integer
        constraint fkgymog7firrf8bnoiig61666ob
            references clients
            on update cascade on delete cascade,
    user_id   integer
        constraint fknjuop33mo69pd79ctplkck40n
            references users
            on update cascade on delete cascade
);

alter table accounts
    owner to postgres;

create table orders
(
    id         integer generated by default as identity
        constraint orders_pkey
            primary key,
    address    varchar(255),
    games      integer[],
    order_date timestamp,
    total_sum  numeric(19, 2),
    account_id integer
        constraint fkagh5svlor3slbay6tq5wqor1o
            references accounts
);

alter table orders
    owner to postgres;

create table user_roles
(
    user_id integer not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users
            on update cascade on delete cascade,
    role_id integer not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles
            on update cascade on delete cascade,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

alter table user_roles
    owner to postgres;