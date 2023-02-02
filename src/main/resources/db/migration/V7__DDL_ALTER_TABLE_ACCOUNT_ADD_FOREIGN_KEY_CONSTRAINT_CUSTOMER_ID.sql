alter table account
    add constraint fireign_key_constraint_customer_id foreign key (customer_id) references customer;
