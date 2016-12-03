# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table grr_pickup_type (
  type_id                       integer not null,
  name                          varchar(255),
  constraint pk_grr_pickup_type primary key (type_id)
);
create sequence pickup_type_id_seq;

create table grr_pickup_quantity_type (
  id                            serial not null,
  quantity_name                 varchar(255),
  constraint pk_grr_pickup_quantity_type primary key (id)
);

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

create table grr_route_collection (
  route_coll_id                 integer not null,
  user_id                       varchar(255),
  route_begin_time              timestamp,
  route_end_time                timestamp,
  vehicle_id                    integer,
  route_id                      integer,
  constraint uq_grr_route_collection_vehicle_id unique (vehicle_id),
  constraint pk_grr_route_collection primary key (route_coll_id)
);
create sequence route_coll_id_seq;

create table grr_route_coll_action (
  route_coll_action_id          integer not null,
  route_stop_order              integer,
  route_action_comment          varchar(255),
  route_action_lat              float,
  route_action_lon              float,
  arrival_time                  timestamp,
  departure_time                timestamp,
  route_action_exception        varchar(255),
  pickup_item_1                 integer,
  pickup_item_1_measurement     integer,
  pickup_item_1_quantity        float,
  pickup_item_2                 integer,
  pickup_item_2_measurement     integer,
  pickup_item_2_quantity        float,
  pickup_item_3                 integer,
  pickup_item_3_measurement     integer,
  pickup_item_3_quantity        float,
  route_coll_id                 integer,
  constraint uq_grr_route_coll_action_route_action_exception unique (route_action_exception),
  constraint uq_grr_route_coll_action_pickup_item_1 unique (pickup_item_1),
  constraint uq_grr_route_coll_action_pickup_item_1_measurement unique (pickup_item_1_measurement),
  constraint uq_grr_route_coll_action_pickup_item_2 unique (pickup_item_2),
  constraint uq_grr_route_coll_action_pickup_item_2_measurement unique (pickup_item_2_measurement),
  constraint uq_grr_route_coll_action_pickup_item_3 unique (pickup_item_3),
  constraint uq_grr_route_coll_action_pickup_item_3_measurement unique (pickup_item_3_measurement),
  constraint pk_grr_route_coll_action primary key (route_coll_action_id)
);
create sequence route_coll_action_id_seq;

create table grr_route_exceptions (
  route_action_exception        varchar(255) not null,
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_route_exceptions primary key (route_action_exception)
);

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
  pickup_item_1                 integer,
  pickup_item_1_measurement     integer,
  pickup_item_2                 integer,
  pickup_item_2_measurement     integer,
  pickup_item_3                 integer,
  pickup_item_3_measurement     integer,
  route_id                      integer,
  constraint uq_grr_route_stop_pickup_item_1 unique (pickup_item_1),
  constraint uq_grr_route_stop_pickup_item_1_measurement unique (pickup_item_1_measurement),
  constraint uq_grr_route_stop_pickup_item_2 unique (pickup_item_2),
  constraint uq_grr_route_stop_pickup_item_2_measurement unique (pickup_item_2_measurement),
  constraint uq_grr_route_stop_pickup_item_3 unique (pickup_item_3),
  constraint uq_grr_route_stop_pickup_item_3_measurement unique (pickup_item_3_measurement),
  constraint pk_grr_route_stop primary key (route_stop_id)
);
create sequence route_stop_id_seq;

create table grr_vehicle (
  vehicle_id                    integer not null,
  vehicle_desc                  varchar(255),
  replacement_cost              integer,
  enter_date                    timestamp,
  modify_date                   timestamp,
  constraint pk_grr_vehicle primary key (vehicle_id)
);
create sequence vehicle_id_seq;

alter table grr_route_collection add constraint fk_grr_route_collection_vehicle_id foreign key (vehicle_id) references grr_vehicle (vehicle_id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_route_action_exception foreign key (route_action_exception) references grr_route_exceptions (route_action_exception) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_1 foreign key (pickup_item_1) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_1_measurement foreign key (pickup_item_1_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_2 foreign key (pickup_item_2) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_2_measurement foreign key (pickup_item_2_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_3 foreign key (pickup_item_3) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_pickup_item_3_measurement foreign key (pickup_item_3_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_coll_action add constraint fk_grr_route_coll_action_route_coll_id foreign key (route_coll_id) references grr_route_collection (route_coll_id) on delete restrict on update restrict;
create index ix_grr_route_coll_action_route_coll_id on grr_route_coll_action (route_coll_id);

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_1 foreign key (pickup_item_1) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_1_measurement foreign key (pickup_item_1_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_2 foreign key (pickup_item_2) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_2_measurement foreign key (pickup_item_2_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_3 foreign key (pickup_item_3) references grr_pickup_type (type_id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_pickup_item_3_measurement foreign key (pickup_item_3_measurement) references grr_pickup_quantity_type (id) on delete restrict on update restrict;

alter table grr_route_stop add constraint fk_grr_route_stop_route_id foreign key (route_id) references grr_route (route_id) on delete restrict on update restrict;
create index ix_grr_route_stop_route_id on grr_route_stop (route_id);


# --- !Downs

alter table if exists grr_route_collection drop constraint if exists fk_grr_route_collection_vehicle_id;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_route_action_exception;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_1;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_1_measurement;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_2;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_2_measurement;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_3;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_pickup_item_3_measurement;

alter table if exists grr_route_coll_action drop constraint if exists fk_grr_route_coll_action_route_coll_id;
drop index if exists ix_grr_route_coll_action_route_coll_id;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_1;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_1_measurement;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_2;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_2_measurement;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_3;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_pickup_item_3_measurement;

alter table if exists grr_route_stop drop constraint if exists fk_grr_route_stop_route_id;
drop index if exists ix_grr_route_stop_route_id;

drop table if exists grr_pickup_type cascade;
drop sequence if exists pickup_type_id_seq;

drop table if exists grr_pickup_quantity_type cascade;

drop table if exists grr_route cascade;
drop sequence if exists route_id_seq;

drop table if exists grr_route_collection cascade;
drop sequence if exists route_coll_id_seq;

drop table if exists grr_route_coll_action cascade;
drop sequence if exists route_coll_action_id_seq;

drop table if exists grr_route_exceptions cascade;

drop table if exists grr_route_stop cascade;
drop sequence if exists route_stop_id_seq;

drop table if exists grr_vehicle cascade;
drop sequence if exists vehicle_id_seq;

