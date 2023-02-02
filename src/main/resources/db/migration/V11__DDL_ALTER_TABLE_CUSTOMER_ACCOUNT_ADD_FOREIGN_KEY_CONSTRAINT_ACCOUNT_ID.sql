alter table customer_account
    add constraint foreign_key_constraint_account_id foreign key (account_id) references account;
