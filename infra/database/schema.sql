CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cpf CHAR(11) NOT NULL,
    street VARCHAR(100),
    number VARCHAR(10),
    neighborhood VARCHAR(100),
    email VARCHAR(150),
    phone VARCHAR(20)
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unit_price DECIMAL(16, 2) NOT NULL
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unit_price DECIMAL(16, 2) NOT NULL
);


CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_key TEXT,
    notes TEXT,
    status VARCHAR(30) NOT NULL CHECK (
        status IN (
            'PLACED',
            'PAID',
            'INVOICED',
            'SHIPPED',
            'PAYMENT_ERROR',
            'PREPARING_SHIPMENT'
        )
    ),
    total DECIMAL(16, 2) NOT NULL,
    tracking_code VARCHAR(255),
    invoice_url TEXT
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(16, 2) NOT NULL,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders (id)
);
