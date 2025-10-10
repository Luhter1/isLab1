BEGIN;

-- Вспомогательный тип для ролей пользователей (enum Role)
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

-- Таблица пользователей системы
CREATE TABLE users (
  id SERIAL PRIMARY KEY,                                      -- Уникальный идентификатор пользователя
  username VARCHAR(255) NOT NULL UNIQUE,                      -- Логин пользователя (уникальный)
  password_hash VARCHAR(128) NOT NULL UNIQUE,                 -- Хэш пароля (используется SHA-512)
  role user_role NOT NULL,                                    -- Роль пользователя: 'USER', 'ADMIN'
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()  -- Дата создания пользователя
);

-- Таблица для запросов на получение прав администратора
CREATE TABLE admin_requests (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  request_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  approved_by INTEGER REFERENCES users(id),
  approval_date TIMESTAMP WITH TIME ZONE
);

COMMIT;