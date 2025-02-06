CREATE TABLE departments(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE employees(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    salary DECIMAL(5,2),
    department VARCHAR(64) NOT NULL,
    is_manager BOOLEAN,
    FOREIGN KEY (department) REFERENCES departments(name)
);
