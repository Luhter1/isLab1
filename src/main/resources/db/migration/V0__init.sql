BEGIN;

-- Вспомогательный тип для типов драконов (enum DragonType)
CREATE TYPE dragon_type AS ENUM ('WATER', 'UNDERGROUND', 'AIR', 'FIRE');

-- Вспомогательный тип для цветов (enum Color)
CREATE TYPE color AS ENUM ('RED', 'BLUE', 'YELLOW', 'ORANGE');

-- Вспомогательный тип для ролей пользователей (enum Role)
CREATE TYPE user_role AS ENUM ('ROLE_USER', 'ROLE_ADMIN');


-- Таблица драконов
CREATE TABLE dragons (
  id SERIAL PRIMARY KEY,                                          -- Уникальный идентификатор дракона (генерируется автоматически)
  name VARCHAR(255) NOT NULL CHECK (name <> ''),                  -- Имя дракона (не может быть пустым)
--   coordinates_id INTEGER NOT NULL
--     REFERENCES coordinates(id) ON DELETE RESTRICT,                -- Идентификатор координат
--   cave_id INTEGER REFERENCES dragon_caves(id) ON DELETE SET NULL, -- Идентификатор пещеры дракона
--   killer_id INTEGER REFERENCES people(id) ON DELETE SET NULL,     -- Идентификатор убийцы дракона
  age INTEGER CHECK (age IS NULL OR age > 0),                     -- Возраст дракона (больше 0), может быть NULL
  color color,                                                    -- Цвет дракона
  type dragon_type,                                               -- Тип дракона
--   head_id INTEGER REFERENCES dragon_heads(id) ON DELETE CASCADE,  -- Идентификатор головы дракона

--   created_by INTEGER NOT NULL REFERENCES users(id),               -- Идентификатор пользователя, создавшего дракона
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()     -- Время создания дракона
--   updated_by INTEGER REFERENCES users(id),                        -- Идентификатор пользователя, последнего обновившего дракона
--   updated_at TIMESTAMP WITH TIME ZONE                             -- Время последнего обновления
);

-- Индексы для оптимизации поиска и выборок
CREATE INDEX idx_dragon_name ON dragons(name);
CREATE INDEX idx_dragon_age ON dragons(age);
CREATE INDEX idx_dragon_type ON dragons(type);
-- CREATE INDEX idx_person_name ON people(name);

-- -- Ограничение внешних ключей для таблиц
-- ALTER TABLE dragons ADD CONSTRAINT fk_coordinates FOREIGN KEY (coordinates_id) REFERENCES coordinates(id);
-- ALTER TABLE dragons ADD CONSTRAINT fk_cave FOREIGN KEY (cave_id) REFERENCES dragon_caves(id);
-- ALTER TABLE dragons ADD CONSTRAINT fk_killer FOREIGN KEY (killer_id) REFERENCES people(id);
-- ALTER TABLE dragons ADD CONSTRAINT fk_head FOREIGN KEY (head_id) REFERENCES dragon_heads(id);
-- ALTER TABLE people ADD CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES locations(id);

COMMIT;