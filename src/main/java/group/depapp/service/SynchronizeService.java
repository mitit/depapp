package group.depapp.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для синхронизации файла с данными из таблицы
 */
public interface SynchronizeService {
    /**
     * Метод, проводящий синхронизацию, выполняется одной трнзакцией
     * @param pathname путь к файлу для синхронизации
     */
    @Transactional
    void synchronize(String pathname);
}
