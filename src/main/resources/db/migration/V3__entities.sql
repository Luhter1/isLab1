BEGIN;

-- Вспомогательный тип для цветов (enum Color)
CREATE TYPE color AS ENUM ('GREEN', 'RED', 'BLACK', 'YELLOW', 'BROWN');

-- Вспомогательный тип для типов драконов (enum DragonType)
CREATE TYPE dragon_type AS ENUM ('WATER', 'UNDERGROUND', 'AIR', 'FIRE');

-- Вспомогательный тип для характеров драконов (enum DragonCharacter)
CREATE TYPE dragon_character AS ENUM ('EVIL', 'CHAOTIC_EVIL', 'FICKLE');

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
  depth INTEGER NOT NULL,                                          -- Глубина пещеры
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
  y INTEGER,                                                       -- Координата Y
  z DOUBLE PRECISION NOT NULL,                                     -- Координата Z
  name VARCHAR(240) NOT NULL,                                      -- Имя локации
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего местоположение
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего местоположение
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица персон
CREATE TABLE people (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор персонажа
  name VARCHAR(255) NOT NULL CHECK (name <> ''),                   -- Имя персонажа (не может быть пустым)
  eye_color color,                                                 -- Цвет глаз
  hair_color color NOT NULL,                                       -- Цвет волос
  location_id INTEGER REFERENCES locations(id) ON DELETE CASCADE, -- Идентификатор местоположения (ссылка на таблицу location)
  birthday TIMESTAMP,                                               -- Дата рождения персонажа
  height REAL NOT NULL CHECK (height > 0),                         -- Рост персонажа (больше 0)
  weight INTEGER CHECK (weight > 0),                               -- Вес персонажа (больше 0)
  passport_id VARCHAR(23) NOT NULL CHECK (passport_id <> ''),      -- Паспорт персонажа
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего персону
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего персону
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица драконов
CREATE TABLE dragons (
  id SERIAL PRIMARY KEY,                                          -- Уникальный идентификатор дракона (генерируется автоматически)
  name VARCHAR(255) NOT NULL CHECK (name <> ''),                  -- Имя дракона (не может быть пустым)
  coordinates_id INTEGER NOT NULL
    REFERENCES coordinates(id) ON DELETE CASCADE,                -- Идентификатор координат
  cave_id INTEGER REFERENCES dragon_caves(id) ON DELETE CASCADE, -- Идентификатор пещеры дракона
  killer_id INTEGER REFERENCES people(id) ON DELETE CASCADE,     -- Идентификатор убийцы дракона
  age INTEGER CHECK (age IS NULL OR age > 0),                     -- Возраст дракона (больше 0), может быть NULL
  color color,                                                    -- Цвет дракона
  type dragon_type,                                               -- Тип дракона
  character dragon_character NOT NULL,                            -- Характер дракона
  head_id INTEGER REFERENCES dragon_heads(id) ON DELETE CASCADE, -- Идентификатор головы дракона
  created_by INTEGER NOT NULL REFERENCES users(id),               -- Идентификатор пользователя, создавшего дракона
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),     -- Время создания дракона
  updated_by INTEGER REFERENCES users(id),                        -- Идентификатор пользователя, последнего обновившего дракона
  updated_at TIMESTAMP WITH TIME ZONE                             -- Время последнего обновления
);


-- Индексы для оптимизации поиска и выборок
CREATE INDEX idx_dragon_name ON dragons(name);
CREATE INDEX idx_dragon_age ON dragons(age);
CREATE INDEX idx_dragon_type ON dragons(type);
CREATE INDEX idx_person_name ON people(name);

-- Ограничение внешних ключей для таблиц
ALTER TABLE dragons ADD CONSTRAINT fk_coordinates FOREIGN KEY (coordinates_id) REFERENCES coordinates(id);
ALTER TABLE dragons ADD CONSTRAINT fk_cave FOREIGN KEY (cave_id) REFERENCES dragon_caves(id);
ALTER TABLE dragons ADD CONSTRAINT fk_killer FOREIGN KEY (killer_id) REFERENCES people(id);
ALTER TABLE dragons ADD CONSTRAINT fk_head FOREIGN KEY (head_id) REFERENCES dragon_heads(id);
ALTER TABLE people ADD CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES locations(id);

COMMIT;