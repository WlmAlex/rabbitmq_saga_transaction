# rabbitmq_saga_transaction

this is a demo of distributed transaction in saga pattern.

here are the sql statements that used to create database

sql statement

```
create DATABASE saga_room

create table room(
room_id int primary key auto_increment,
seats_available int 
)

create DATABASE saga_account

create table account(
account_id int(11) primary key auto_increment not null,
deposit double,
user_id int(11)
)

create DATABASE saga_ticket

create table ticket(
ticket_id int(11) primary key auto_increment not null,
issued_date datetime,
room_id int(11),
user_id int(11)
)
