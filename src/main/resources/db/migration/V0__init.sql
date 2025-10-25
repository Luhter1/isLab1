-- Вспомогательный тип для ролей пользователей (enum Role)
CREATE TYPE user_role AS ENUM ('ROLE_USER', 'ROLE_ADMIN');

-- Таблица пользователей системы
CREATE TABLE users (
  id SERIAL PRIMARY KEY,                                        -- Уникальный идентификатор пользователя
  username VARCHAR(255) NOT NULL UNIQUE CHECK (username <> ''), -- Логин пользователя (уникальный)
  password_hash VARCHAR(128) NOT NULL,                          -- Хэш пароля (используется SHA-512)
  role user_role NOT NULL,                                      -- Роль пользователя: 'USER', 'ADMIN'
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()    -- Дата создания пользователя
);