BEGIN;

-- Таблица координат
CREATE TABLE coordinates (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор координат
  x INTEGER NOT NULL CHECK (x > -999),                             -- Координата X (минимум -998)
  y DOUBLE PRECISION NOT NULL CHECK (x <= 844),                    -- Координата Y (максимум 844)
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего координату
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего координату
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица пещер драконов
CREATE TABLE dragon_caves (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор пещеры
  depth INTEGER,                                                   -- Глубина пещеры
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего пещеру дракона
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего пещеру дракона
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица голов драконов
CREATE TABLE dragon_heads (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор головы дракона
  size REAL,                                                       -- Размер головы
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего голову дракона
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего голову дракона
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица локаций
CREATE TABLE locations (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор местоположения
  x BIGINT NOT NULL,                                               -- Координата X
  y INTEGER ,                                                      -- Координата Y
  z DOUBLE PRECISION NOT NULL,                                     -- Координата Z
  name VARCHAR(240) NOT NULL,                                      -- Имя локации (не может быть пустым)
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего местоположение
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего местоположение
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);
COMMIT;