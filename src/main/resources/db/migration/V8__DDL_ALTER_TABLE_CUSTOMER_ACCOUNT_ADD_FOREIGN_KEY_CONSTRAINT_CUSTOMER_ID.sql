alter table customer_account
    add constraint foreign_key_constraint_customer_id foreign key (customer_id) references customer;
