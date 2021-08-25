create table if not exists fact
(
    id bigint not null constraint fact_pkey primary key,
    fact varchar(1024),
    timestamp timestamp
);