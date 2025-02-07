CREATE TABLE departments(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE employees(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    salary DECIMAL,
    department_id BIGINT,
    is_manager BOOLEAN,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);
