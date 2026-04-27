package com.haritonov.config;

import org.aeonbits.owner.Config;

/**
 * Интерфейс конфигурации для автотестов.
 * <p>
 * Использует библиотеку Owner для чтения значений из файла {@code config.properties},
 */
@Config.Sources("classpath:configurations/config.properties")
public interface Configuration extends Config{

    /**
     * Базовый URL тестируемого API (http://localhost:8080).
     */
    String baseUrl();

    /**
     * URL эндпоинта для создания сущности (/api/create).
     */
    String createUrl();

    /**
     * Базовый URL эндпоинта для получения сущности по ID (/api/get).
     * Полный путь формируется как {@code getUrl + "/{id}"}.
     */
    String getUrl();

    /**
     * URL эндпоинта для получения списка всех сущностей (/api/getAll).
     */
    String getAllUrl();

    /**
     * URL эндпоинта для обновления сущности (/api/patch).
     */
    String patchUrl();

    /**
     * URL эндпоинта для удаления сущности (/api/delete).
     */
    String deleteUrl();

    /**
     * Нижняя граница для генерации случайного количества важных чисел.
     * Используется при создании {@code important_numbers}.
     */
    int lowerLimitSizeImportantNumbers();

    /**
     * Верхняя граница для генерации случайного количества важных чисел.
     * Используется при создании {@code important_numbers}.
     */
    int upperLimitSizeImportantNumbers();

    /**
     * Минимальное возможное значение для генерации случайных чисел.
     */
    int lowerNumber();

    /**
     * Максимальное возможное значение для генерации случайных чисел.
     */
    int upperNumber();
}
