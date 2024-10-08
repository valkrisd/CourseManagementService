CREATE TABLE course
(
    id          INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    author      VARCHAR(100),
    duration    INT,                          -- длительность курса в часах или днях
    status      VARCHAR(20) DEFAULT 'ACTIVE', -- статус: активный, архивный, ожидающий и т.д.
    price       DECIMAL(10, 2),               -- цена курса
    start_date  DATE,
    end_date    DATE,
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lesson
(
    id          INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    course_id   INT          NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    order_index INT, -- порядок следования урока в рамках курса
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES Course (id)
);

CREATE TABLE enrollment
(
    id              INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    course_id       INT NOT NULL,
    user_id         INT NOT NULL,
    status          VARCHAR(20) DEFAULT 'ACTIVE', -- статус записи: активный, завершенный и т.д.
    enrollment_date DATE,
    completed       BOOLEAN     DEFAULT FALSE,    -- флаг завершения курса
    created_at      TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES Course (id)
);

CREATE TABLE lesson_material
(
    id          INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    lesson_id   INT         NOT NULL,
    type        VARCHAR(50) NOT NULL, -- тип материала (видео, текст, задание и т.д.)
    title       VARCHAR(255),
    description TEXT,
    file_url    VARCHAR(255),         -- ссылка на файл
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lesson_id) REFERENCES Lesson (id)
);