-- // Create Changelog

-- Default DDL for changelog table that will keep
-- a record of the migrations that have been run.

-- You can modify this to suit your database before
-- running your first migration.

-- Be sure that ID and DESCRIPTION fields exist in
-- BigInteger and String compatible fields respectively.

CREATE SEQUENCE credential_id_seq START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

create table if not exists credential
(
  id bigint default nextval('credential_id_seq'::regclass) not null
    constraint credential_pkey
      primary key,
  stack_id bigint
    constraint fk_stackidincredential
      references stack,
  attributes text
);

create unique index if not exists credential_id_idx
  on credential (id);

create index if not exists credential_stack_id
  on credential (stack_id);

-- //@UNDO

DROP TABLE credential;

DROP SEQUENCE credential_id_seq;