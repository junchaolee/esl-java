package com.xbstar.esl.common;

import java.util.List;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年9月1日上午12:03:56
 * @Version:1.0.0
 */
public interface BaseService<T> {
    List<T> findAll();

    int findCount(T t);

    T findById(Long id);

    T findOne(T t);

    int insert(T t);

    int update(T t);

    int delete(Long id);
}
