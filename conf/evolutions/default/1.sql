# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table grr_route (
  route_id                      integer not null,
  route_desc                    varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip                           varchar(255),
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_route primary key (route_id)
);
create sequence route_id_seq;

create table grr_route_stop (
  route_stop_id                 integer not null,
  route_stop_order              integer,
  route_stop_name               varchar(255),
  street_address1               varchar(255),
  street_address2               varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip                           varchar(255),
  contact_name                  varchar(255),
  contact_email                 varchar(255),
  notify_contact_on_next        boolean,
  notify_contact_on_exception   boolean,
  route_stop_lat                float,
  route_stop_lon                float,
  enter_date                    timestamp,
  modify_date                   timestamp,
  route_id                      integer,
  constraint pk_grr_route_stop primary key (route_stop_id)
);
create sequence route_stop_id_seq;

create table grr_route_stop_pu_items (
  pickup_item_id                integer not null,
  route_stop_id                 integer,
  pu_item                       varchar(255),
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_route_stop_pu_items primary key (pickup_item_id)
);
create sequence route_pickup_item_seq;

create table grr_vehicle (
  vehicle_id                    integer not null,
  vehicle_desc                  varchar(255),
  replacement_cost              integer,
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_vehicle primary key (vehicle_id)
);
create sequence vehicle_id_seq;

alter table grr_route_stop add constraint fk_grr_route_stop_route_id foreign key (route_id) references grr_route (route_id) on delete restrict on update restrict;
create index ix_grr_route_stop_route_id on grr_route_stop (route_id);

alter table grr_route_stop_pu_items add constraint fk_grr_route_stop_pu_items_route_stop_id foreign key (route_stop_id) references grr_route_stop (route_stop_id) on delete restrict on update restrict;
create index ix_grr_route_stop_pu_items_route_stop_id on grr_route_stop_pu_items (route_stop_id);


# --- !Downs

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_route_id;
drop index if exists ix_grr_route_stop_route_id;

alter table if exists grr_route_stop_pu_items drop constraint if exists fk_grr_route_stop_pu_items_route_stop_id;
drop index if exists ix_grr_route_stop_pu_items_route_stop_id;

drop table if exists grr_route cascade;
drop sequence if exists route_id_seq;

drop table if exists grr_route_stop cascade;
drop sequence if exists route_stop_id_seq;

drop table if exists grr_route_stop_pu_items cascade;
drop sequence if exists route_pickup_item_seq;

drop table if exists grr_vehicle cascade;
drop sequence if exists vehicle_id_seq;

