package com.ruoyi.system.service.impl;


import com.ruoyi.system.mapper.SqlMapper;
import com.ruoyi.system.service.ISqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SqlServiceImpl implements ISqlService {

    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public Integer insert(String statement) {
        return sqlMapper.insert(statement);
    }

    @Override
    public Integer delete(String statement) {
        return sqlMapper.delete(statement);
    }

    @Override
    public Integer update(String statement) {
        return sqlMapper.update(statement);
    }

    @Override
    public List<Map<String, Object>> selectList(String statement) {
        return sqlMapper.selectList(statement);
    }

    @Override
    public String selectOne(String statement) {
        return sqlMapper.selectOne(statement);
    }

    @Override
    public int selectCount(String statement) {
        return Integer.parseInt(sqlMapper.selectOne("select count(*) as cnt from ("+statement+") as tt"));
    }
}
