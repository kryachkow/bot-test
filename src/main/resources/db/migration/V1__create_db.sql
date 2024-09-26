
CREATE TABLE degrees (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE lectors (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    salary INT NOT NULL,
    degree_id int NOT NULL REFERENCES degrees(id)
);

CREATE TABLE departments (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    head_id bigint NOT NULL REFERENCES lectors(id)
);

CREATE TABLE departments_lectors (
    department_id bigint REFERENCES departments(id),
    lector_id bigint REFERENCES lectors(id),
    PRIMARY KEY (department_id, lector_id)
);