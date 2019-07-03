-- add tables for running sync commands asynchronously
--
-- Migration SQL that makes the change goes here.

CREATE SEQUENCE IF NOT EXISTS account_id_seq START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE IF NOT EXISTS account
(
  id bigint default nextval('account_id_seq'::regclass) not null
    constraint account_pkey
      primary key,
  accountid varchar(255)
);

CREATE UNIQUE INDEX IF NOT EXISTS account_id_idx
  ON account (id);

CREATE INDEX IF NOT EXISTS account_accountid_idx
  ON account (accountid);

CREATE SEQUENCE IF NOT EXISTS syncoperation_id_seq START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE IF NOT EXISTS syncoperation
(
  id bigint default nextval('syncoperation_id_seq'::regclass) not null
    constraint syncoperation_pkey
      primary key,
  account_id bigint not null
    constraint fk_syncoperation_account_id
        references account,
  template text,
  requested bigint default (date_part('epoch'::text, now()) * (1000)::double precision),
  completed bigint,
  status varchar(255)
);

CREATE UNIQUE INDEX IF NOT EXISTS syncoperation_id_idx
  ON syncoperation (id);

CREATE INDEX IF NOT EXISTS syncoperation_accountid_idx
  ON syncoperation (account_id);

CREATE SEQUENCE IF NOT EXISTS stacksyncoperation_id_seq START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE IF NOT EXISTS stacksyncoperation
(
  id bigint default nextval('stacksyncoperation_id_seq'::regclass) not null
    constraint stacksyncoperation_pkey
      primary key,
  syncoperation_id bigint not null
    constraint fk_stacksyncoperation_syncoperation_id
        references syncoperation,
  stack_id bigint not null
    constraint fk_stacksyncoperation_stack_id
        references stack,
  requested bigint default (date_part('epoch'::text, now()) * (1000)::double precision),
  completed bigint,
  status varchar(255),
  details text
);

CREATE UNIQUE INDEX IF NOT EXISTS stacksyncoperation_id_idx
  ON stacksyncoperation (id);

CREATE INDEX IF NOT EXISTS stacksyncoperation_syncoperation_idx
  ON stacksyncoperation (syncoperation_id);

CREATE INDEX IF NOT EXISTS stacksyncoperation_stack_idx
  ON stacksyncoperation (stack_id);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE accountsync;

DROP SEQUENCE accountsync_id_seq;

DROP TABLE syncoperation;

DROP SEQUENCE syncoperation_id_seq;

DROP TABLE stacksyncoperation;

DROP SEQUENCE stacksyncoperation_id_seq;