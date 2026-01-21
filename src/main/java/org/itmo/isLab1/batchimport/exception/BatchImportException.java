package org.itmo.isLab1.batchimport.exception;

/**
 * Исключение, выбрасываемое при ошибках пакетного импорта данных
 */
public class BatchImportException extends RuntimeException {

    /**
     * Создает исключение с указанным сообщением
     *
     * @param message сообщение об ошибке
     */
    public BatchImportException(String message) {
        super(message);
    }

    /**
     * Создает исключение с указанным сообщением и причиной
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public BatchImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
