create table payment_methods (
    id bigint generated always as identity, 
    owner text,
    type text,
    sub_type text,
    last4 text,
    primary key(id)
);  