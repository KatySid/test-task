BEGIN;
DROP TABLE IF EXISTS clients CASCADE;
create table clients (
    id                      bigserial primary key,
    name                    varchar(30) not null,
    last_name               varchar(30) not null,
    patronymic              varchar(30),
    email                   varchar(80) unique,
    passport                varchar(30) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);
DROP TABLE IF EXISTS banks CASCADE;
create table banks (
    id                      bigserial primary key,
    title                    varchar(50) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

DROP TABLE IF EXISTS clients_banks CASCADE;
CREATE TABLE clients_banks (
          client_id               bigint not null references clients (id) on DELETE CASCADE,
          bank_id                 bigint references banks (id) on DELETE CASCADE,
          primary key (client_id, bank_id)
);
DROP TABLE IF EXISTS credits CASCADE;
create table credits (
    id                      bigserial primary key,
    limitation              numeric(16,2),
    percent                 numeric(8,2),
    bank_id                 bigint REFERENCES banks (id) on DELETE CASCADE,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);
DROP TABLE IF EXISTS credit_offers CASCADE;
CREATE TABLE credit_offers (
    id                      bigserial primary key,
    amount                  numeric(16,2),
    duration                numeric(8,2),
    client_id               bigint not null references clients (id) on DELETE CASCADE,
    credit_id               bigint not null references credits (id) on DELETE CASCADE,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

DROP TABLE IF EXISTS payments CASCADE;
CREATE TABLE payments (
    id                      bigserial primary key,
    localdate               timestamp,
    amount_payment          numeric(16,2),
    percent_payment         numeric(16,2),
    body_credit_payment     numeric(16,2),
    credit_offer_id          bigint not null references credit_offers (id),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

INSERT INTO banks (title) VALUES
('СБЕР'),
('ВТБ');

INSERT INTO clients (last_name, name, patronymic, email, passport) VALUES
('Ivanov1', 'Ivan','Ivanovich', '1ivanov@mail.ru', '6413 789951'),
('Petrov2', 'Ivan','Ivanovich', '2petrov@mail.ru', '6413 789152'),
('Ivanov3', 'Ivan','Ivanovich', '3ivanov@mail.ru', '6413 789953'),
('Petrov4', 'Ivan','Ivanovich', '4petrov@mail.ru', '6413 789154'),
('Ivanov5', 'Ivan','Ivanovich', '5ivanov@mail.ru', '6413 789955'),
('Petrov6', 'Ivan','Ivanovich', '6petrov@mail.ru', '6413 789156'),
('Ivanov7', 'Ivan','Ivanovich', '7ivanov@mail.ru', '6413 789957'),
('Petrov8', 'Ivan','Ivanovich', '8petrov@mail.ru', '6413 789158'),
('Ivanov9', 'Ivan','Ivanovich', '9ivanov@mail.ru', '6413 789959'),
('Petrov10', 'Ivan','Ivanovich', '10petrov@mail.ru', '6413 789150'),
('Ivanov11', 'Ivan','Ivanovich', '11ivanov@mail.ru', '6413 789960'),
('Petrov12', 'Ivan','Ivanovich', '12petrov@mail.ru', '6413 789161');

insert into clients_banks (client_id, bank_id)
values
(1, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 1),
(5, 1),
(6, 2),
(7, 1),
(8, 2),
(9, 1),
(10, 1),
(11, 2),
(12, 1);

INSERT INTO credits (limitation, percent, bank_id) VALUES
(10000, 20,1),
(100000, 15,1);

COMMIT;