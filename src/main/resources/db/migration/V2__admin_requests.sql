BEGIN;

-- Вспомогательный тип для статуса заявки на администратора
CREATE TYPE request_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

-- Таблица для запросов на получение прав администратора
CREATE TABLE admin_requests (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  status request_status NOT NULL DEFAULT 'PENDING',
  approved_by INTEGER REFERENCES users(id),
  approval_date TIMESTAMP WITH TIME ZONE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  request_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);


CREATE INDEX idx_admin_requests_user_id ON admin_requests(user_id);
CREATE INDEX idx_admin_requests_status ON admin_requests(status);

COMMIT;