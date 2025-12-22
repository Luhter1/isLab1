-- Вспомогательный тип для статуса импорта
CREATE TYPE import_status AS ENUM ('SUCCESS', 'FAILED');

-- Таблица истории импорта
CREATE TABLE batch_import_history (
  id SERIAL PRIMARY KEY,
  created_by INTEGER NOT NULL REFERENCES users(id),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  status import_status NOT NULL,
  successful_operations INTEGER NOT NULL
);

-- Ограничение внешних ключей
ALTER TABLE batch_import_history ADD CONSTRAINT fk_batch_import_history_created_by FOREIGN KEY (created_by) REFERENCES users(id);

