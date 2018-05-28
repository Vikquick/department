create sequence department_id_pk_seq;

create sequence departmenthistory_id_seq;

create sequence employer_id_pk_seq;

create sequence salary_id_seq;

create table if not exists department
(
	id integer default nextval('department_id_pk_seq'::regclass) not null
		constraint department_id_pk
			primary key,
	name varchar not null
		constraint unique_department_name_constraint
			unique,
	creationdate date not null,
	departmentid integer
		constraint department_parent_fk
			references department
);

create index if not exists fki_department_parent_fk
	on department (departmentid);

create table if not exists departmenthistory
(
	id integer default nextval('departmenthistory_id_seq'::regclass) not null
		constraint departmenthistory_id_pk
			primary key,
	departmentid integer not null,
	action varchar,
	date date
);

create table if not exists employer
(
	id integer default nextval('employer_id_pk_seq'::regclass) not null
		constraint employer_id_pk
			primary key,
	firstname varchar not null,
	name varchar not null,
	lastname varchar,
	gender varchar(32) not null,
	dateofbirth date not null,
	phone varchar not null,
	email varchar not null,
	dateofemployment date not null,
	dateofunemployment date,
	post varchar(32) not null,
	salary integer not null,
	head boolean not null,
	departmentid integer not null
		constraint employer_id_fk
			references department,
	fired boolean default false not null
);

create index if not exists fki_employer_id_fk
	on employer (departmentid);

create table if not exists salary
(
	id integer default nextval('salary_id_seq'::regclass) not null
		constraint salary_id_pk
			primary key,
	departmentid integer not null
		constraint salary_fk
			references department,
	salarysum integer,
	date timestamp(6) not null
);
