# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table grr_vehicle (
  vehicle_id                    integer not null,
  vehicle_desc                  varchar(255),
  replacement_cost              integer,
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_vehicle primary key (vehicle_id)
);
create sequence vehicle_id_seq;


# --- !Downs

drop table if exists grr_vehicle cascade;
drop sequence if exists vehicle_id_seq;

