INSERT INTO degrees (name) VALUES
('ASSISTANT'),
('ASSOCIATE_PROFESSOR'),
('PROFESSOR');

INSERT INTO lectors (name, surname, salary, degree_id) VALUES
('John', 'Doe', 1000, 1),
('Jane', 'Smith', 2000, 2),
('Robert', 'Johnson', 3000, 3),
('Michael', 'Williams', 1500, 1),
('Sarah', 'Jones', 2500, 2),
('Jessica', 'Brown', 3500, 3),
('Jacob', 'Davis', 1800, 1),
('Emily', 'Miller', 2800, 2),
('James', 'Wilson', 3800, 3),
('Elizabeth', 'Moore', 1100, 1),
('William', 'Taylor', 2100, 2),
('Amanda', 'Anderson', 3100, 3),
('David', 'Thomas', 1200, 1),
('Jennifer', 'Jackson', 2200, 2),
('Christopher', 'White', 3200, 3);

INSERT INTO departments (name, head_id) VALUES
('Mathematics', 3),
('Physics', 6),
('Chemistry', 9),
('Biology', 12),
('Computer Science', 15);

INSERT INTO departments_lectors (department_id, lector_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 2), (2, 3), (2, 4),
(3, 3), (3, 4), (3, 5), (3, 6),
(4, 4), (4, 5),
(5, 5), (5, 1), (5, 2), (5, 3);