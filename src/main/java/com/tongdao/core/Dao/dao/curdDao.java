package com.tongdao.core.Dao.dao;

import com.tongdao.core.Dao.beans.Po;
import com.tongdao.core.Dao.beans.WherePrams;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
  * Dao层
  * @auther tuweichao
  * @time 2018/2/8
  * @parm<T> 实体
  * @parm<pk> 主键
 */
@Repository
public interface curdDao<T extends Po,PK extends Serializable> {

    /*
    添加不为空的记录（只将不为空的字段入库，效率高）
    @return 受改变的记录数
     */
    public int addLocal(T po);

    /*
    记录添加入库（所有字段入库）
    @return 受改变的记录数
     */
    public int add(T po);

    /*
    通过主键获取某条记录
    @return po
     */
    public T get(PK id);

    /*
    通过主键获取某个字段的值
    @return 值
     */
    public Serializable getField(PK id, String fileName);

    /*
    条件获取某条记录
    @return po
     */
    public T get(WherePrams where);

    /*
    条件获取某条记录的字段
    @return
     */
    public Serializable getFile(WherePrams where, String fileName);

    /*
    条件查询多条
    @return
     */
    List<T> list(WherePrams where);

    /*
     * 查询某个字段的列表
     * @return 集合
     */
    public Serializable[] listFile(WherePrams where, String fileName);

    /*
     * 查询某些字段
     * @return po字段列表
     */
    public List<Map<String, Serializable>> listFiles(WherePrams where, String[] files);

    /*
    * 更新不为空的字段
    * @return 受影响的行数
    */
    public int updateLoacl(T po);

    /*
    * 更新po所有字段
    * @return 受影响的行数
    */
    public int update(T po);

    /*
    * 条件更新不为空的行数
    * @return 受影响的行数
    */
    public int updateLocal(T po, WherePrams where);

    /*
    * 条件更新所有字段
    * @return 受影响的行数
    */
    public int update(T po, WherePrams where);

    /*
    * 根据主键删除某个记录
    * @return 受影响的行数
    */
    public int delete(PK id);

    /*
    * 条件删除某一行的记录
    * @return 受影响的行数
    */
    public int delete(WherePrams where);

    /**
     * 自定义sql查询
     * @param sql 用于执行查询的Sql
     * @param
     * @return 结果集合
     */
    public List<Map<String, Object>> listBySql(String sql);

    /**
     * 执行自定义sql
     * @param sql 用于执行的Sql
     * @param
     * @return 受影响的行数
     */
    public int excuse(String sql);

    /**
     * 获取指定条件的记录数
     * @param where 条件表达式
     * @return 查询到的记录数
     */
    public long count(WherePrams where);

    /**
     * 获取对应表中的记录数
     * @return 表中的条数
     */
    public long size();

    /**
     * 是否存在字段相同的记录（ID以及不为空的字段除外）
     * @param po 参照实体
     * @return
     */
    public boolean isExist(T po);

    /**
     * 是否存在指定条件的记录
     * @param where 条件表达式
     * @return
     */
    public boolean isExist(WherePrams where);

    /**
     * 内查询
     * @param fileName 用于内查询的字段
     * @param values 字段的值
     * @return 查询到的结果集
     */
    public List<T> in(String fileName, Serializable[] values);

    /**
     * 获得下一个序列的值
     * @return
     */
    public long nextId();


}




