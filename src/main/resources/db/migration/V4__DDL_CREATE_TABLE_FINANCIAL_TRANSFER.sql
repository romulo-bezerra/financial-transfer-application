create table financial_transfer
(
    id                     bigint generated by default as identity,
    created_at             timestamp,
    success                boolean,
    type                   varchar(255),
    value                  decimal(19, 2) not null,
    destination_account_id bigint,
    source_account_id      bigint,
    primary key (id)
);