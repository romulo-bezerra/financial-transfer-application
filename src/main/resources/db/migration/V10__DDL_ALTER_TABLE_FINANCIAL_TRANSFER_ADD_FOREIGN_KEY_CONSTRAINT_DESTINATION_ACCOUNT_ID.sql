alter table financial_transfer
    add constraint foreign_key_constraint_destination_account_id foreign key (destination_account_id) references account;
