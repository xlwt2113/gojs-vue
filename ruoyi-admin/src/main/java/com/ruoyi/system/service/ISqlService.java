package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

/**
 * 实现自定义sql处理的服务类
 */
public interface ISqlService {
    /**
     * 执行插入的sql语句
     *
     * @param statement
     * @return
     */
    Integer insert(String statement);

    /**
     * 执行删除的sql语句
     *
     * @param statement
     * @return
     */
    Integer delete(String statement);

    /**
     * 执行更新的sql语句
     *
     * @param statement
     * @return
     */
    Integer update(String statement);

    /**
     * 执行查询的sql语句
     *
     * @param statement
     * @return
     */
    List<Map<String, Object>> selectList(String statement);

    /**
     * 获取单个记录的
     *
     * @param statement
     * @return
     */
    String selectOne(String statement);

    /**
     * 获取记录总条数
     * @param statement
     * @return
     */
    int selectCount(String statement);
}
