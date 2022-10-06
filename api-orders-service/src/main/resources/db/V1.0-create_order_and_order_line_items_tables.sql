create table orders (
    id bigint not null auto_increment,
    order_number varchar(255) not null unique,
    PRIMARY KEY (id)
);

create table order_line_items (
    id bigint not null auto_increment,
    order_id bigint signed not null,
    sku_code varchar(255) not null,
    price smallint not null,
    quantity tinyint not null,
    primary key (id),
    foreign key (order_id) references orders(id)
);