-- Добавление поля file_path для хранения ссылки на файл в MinIO
-- Поле строковое, необязательное (NULL), максимальная длина 255
ALTER TABLE batch_import_history ADD COLUMN file_path VARCHAR(255);
