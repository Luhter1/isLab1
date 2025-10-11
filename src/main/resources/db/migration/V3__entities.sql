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

COMMIT;