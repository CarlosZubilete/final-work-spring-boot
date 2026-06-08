package com.final_work_spring_boot.service;

import java.util.List;

public interface IGenericService<T> {

    List<T> getAll();

    T getById(Long id);

    T save(T dto);

    T update(T dto, Long id);

    boolean delete(Long id);
}
