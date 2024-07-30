-- Добавление данных в таблицу курсов
INSERT INTO course (title, description, author, duration, status, price, start_date, end_date)
VALUES ('Java Basics', 'Course for Java beginners', 'John Doe', 30, 'ACTIVE', 99.99, '2023-01-01', '2023-03-01'),
       ('Spring Framework', 'Advance course about Spring', 'Jane Smith', 60, 'ACTIVE', 199.99, '2023-02-01',
        '2023-04-01');

-- Добавление данных в таблицу занятий
INSERT INTO lesson (course_id, title, description, order_index)
VALUES (1, 'Intro to Java', 'Introductory lesson for Java course', 1),
       (2, 'Spring Boot Basics', 'Introduction to Spring Boot', 2);

-- Добавление данных в таблицу материалов для занятия
INSERT INTO lesson_material (lesson_id, type, title, description, file_url)
VALUES (1, 'VIDEO', 'Java Introduction Video', 'A Basic intro to Java', 'https://example.com/java-basics'),
       (2, 'DOCUMENT', 'Spring Boot Guide', 'Guide for starting with Spring Boot',
        'https://example.com/spring-boot-guide');

-- Добавление данных в таблицу записей на курс
INSERT INTO enrollment (course_id, user_id, status, enrollment_date, completed)
VALUES (1, 1, 'ACTIVE', '2023-01-02', false),
       (1, 2, 'ACTIVE', '2023-02-05', false);