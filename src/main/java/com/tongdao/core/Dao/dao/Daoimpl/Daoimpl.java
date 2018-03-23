package com.tongdao.core.Dao.dao.Daoimpl;


import com.tongdao.core.Dao.annotation.po.FieldName;
import com.tongdao.core.Dao.beans.Method;
import com.tongdao.core.Dao.beans.Po;
import com.tongdao.core.Dao.beans.Pram;
import com.tongdao.core.Dao.beans.Pram;
import com.tongdao.core.Dao.beans.WherePrams;
import com.tongdao.core.Dao.dao.curdDao;
import com.tongdao.core.Dao.sql.where.C;
import com.tongdao.core.Dao.sql.where.SqlUtil;
import com.tongdao.core.Dao.util.GenericsUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SuppressWarnings("unused")
@Repository
public class Daoimpl<T extends Po, PK extends Serializable> implements curdDao<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource(name = "sqlSessionTemplateASS")
    private SqlSessionTemplate sqlSessionTemplateASS;

    private Class<T> entityClass;

    private String pkName;                  //主键字段

    private String idName;                  //对应id名称

    private String seq;                     //当前主键

    private String tableName;

    private List<Pram> sqlParms;


    private List<Pram> selectSqlParms;

    private SqlUtil<T> sqlUtil;

    @SuppressWarnings("unchecked")
    public Daoimpl() {
        super();

        this.sqlUtil = new SqlUtil<T>();

        this.entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());

        this.sqlParms = this.sqlUtil.getPramList(this.entityClass);

        this.selectSqlParms = this.sqlUtil.getPramListOfSelect(this.entityClass);

        this.tableName = this.sqlUtil.getTableName(this.entityClass);

        //习惯统一用‘id’做约束了，所以这里我给固定死了，不想固定的话可以修改这里
        this.pkName = "id";

        this.idName = "id";

        this.seq = "id";

    }


    @Override
    public int addLocal(T po) {
;
        String sql = "insert into " + tableName + " (";
        String prams = "";
        String values = "";

        List<Pram> pramList = SqlUtil.getPramListofStatic(po);

        int index = 0;
        for (int i = 0; i < pramList.size(); i++) {
            if (pramList.get(i).getValue() == null || (pramList.get(i).getValue() + "").equals("0")) {
                continue;
            } else {
                if (index > 0) {
                    prams += ",";
                    values += ",";
                }
                prams += pramList.get(i).getFile();
                Object value = pramList.get(i).getValue();
                if (value instanceof byte[]) {
                    values += "'" + new String((byte[]) value) + "'";
                } else if (value instanceof Boolean) {
                    values += "'" + ( value = true ? 1 : 0) + "'";
                } else {
                    values += "'" + value + "'";
                }

                index++;
            }
        }
        sql += prams + ") value (" + values + ");";

        SqlUtil.setFileValue(po, "id", nextId());

        logger.debug(sql);
        return sqlSessionTemplateASS.insert("addLocal", sql);
    }

    @Override
    public int add(T po) {
        // TODO Auto-generated method stub
        String sql = "insert into " + tableName + " (";
        String prams = "";
        String values = "";

        List<Pram> pramList = SqlUtil.getPramListofStatic(po);

        for (int i = 0; i < pramList.size(); i++) {
            prams += pramList.get(i).getFile();
            if (pramList.get(i).getValue() == null) {
                values += "null";
            }else if(pramList.get(i).getValue() instanceof Boolean){
                values += "'" + ((boolean)pramList.get(i).getValue() == true ? 1 : 0) + "'";
            }else{
                values += "'" + pramList.get(i).getValue() + "'";
            }

            if(i < pramList.size() -1){
                prams += ",";
                values += ",";
            }
        }
        sql += prams + ") value (" + values +");";

        SqlUtil.setFileValue(po, "id", nextId());

        return sqlSessionTemplateASS.insert("add", sql);
    }

    @Override
    public T get(PK id) {
        String sql = "select ";
        for (int i = 0; i < selectSqlParms.size(); i++) {
            sql += selectSqlParms.get(i).getFile();
            if (i < selectSqlParms.size() - 1) {
                sql += ",";
            } else {
                sql += " ";
            }
        }
        sql += " from " + tableName + " where id='" + id + "';";
        Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne(
                "getById", sql);

        return handleResult(resultMap, this.entityClass);

    }


    @Override
    public Serializable getField(PK id, String fileName) {
        String field = fileName;
        String tabField = "";
        Field f = sqlUtil.getField(this.entityClass, fileName);
        if (null == f) {
            logger.error("查询字段失败(无法找到" + this.entityClass + "中的" + fileName + "字段)");
        }
        FieldName annotation = f.getAnnotation(FieldName.class);
        if (null == annotation) {
            tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
        } else {
            tabField = annotation.name() + " as " + fileName;
        }

        String sql = "select ";
        sql += tabField + " from " + tableName + " where id='" + id + "';";
        Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne(
                "getFieldById", sql);
        return (Serializable) resultMap.get(fileName);
    }

    @Override
    public T get(WherePrams where) {
        String sql = "select ";
        for (int i = 0; i < selectSqlParms.size(); i++) {
            sql += selectSqlParms.get(i).getFile();
            if (i < selectSqlParms.size() - 1) {
                sql += ",";
            } else {
                sql += " ";
            }
        }
        sql += "from " + tableName + where.getWherePrams() + ";";

        Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne(
                "getByParm", sql);

        return handleResult(resultMap, this.entityClass);
    }

    @Override
    public Serializable getFile(WherePrams where, String fileName) {
        String field = fileName;
        String tabField = "";
        Field f = sqlUtil.getField(this.entityClass, fileName);
        if (null == f) {
            logger.error("查询字段失败(无法找到" + this.entityClass + "中的" + fileName + "字段)");
        }
        FieldName annotation = f.getAnnotation(FieldName.class);
        if (null == annotation) {
            tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
        } else {
            tabField = annotation.name() + " as " + fileName;
        }

        String sql = "select ";
        sql += tabField + " from " + tableName + where.getWherePrams() + ";";
        Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne(
                "getFieldByParm", sql);
        return (Serializable) resultMap.get(fileName);
    }

    @Override
    public List<T> list(WherePrams where) {
        String sql = "select ";
        for (int i = 0; i < selectSqlParms.size(); i++) {
            sql += selectSqlParms.get(i).getFile();
            if (i < selectSqlParms.size() - 1) {
                sql += ",";
            } else {
                sql += " ";
            }
        }
        sql += "from " + tableName + where.getWherePrams() + ";";

        List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectList", sql);

        List<T> list = new ArrayList<T>();
        for (Map<String, Object> map : selectList) {
            T t = handleResult(map, this.entityClass);
            list.add(t);
        }

        return list;
    }

    @Override
    public Serializable[] listFile(WherePrams where, String fileName) {
        String field = fileName;
        String tabField = "";
        Field f = sqlUtil.getField(this.entityClass, fileName);
        if (null == f) {
//            logger.error("查询制定字段集失败(无法序列化"+this.entityClass+"中的"+fileName+"字段)");
        }
        FieldName annotation = f.getAnnotation(FieldName.class);
        if (null == annotation) {
            tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
        } else {
            tabField = annotation.name() + " as " + fileName;
        }

        String sql = "select ";
        sql += tabField + " from " + tableName + where.getWherePrams() + ";";
        List<Map<String, Object>> resultMap = sqlSessionTemplateASS.selectList("selectListField", sql);

        Serializable[] fields = new Serializable[resultMap.size()];

        for (int i = 0; i < resultMap.size(); i++) {
            if (null != resultMap.get(i)) {
                fields[i] = (Serializable) resultMap.get(i).get(fileName);
            }
        }

        return fields;
    }

    @Override
    public List<Map<String, Serializable>> listFiles(WherePrams where, String[] files) {
        String tabField = "";
        int index = 1;
        for (String field : files) {
            Field f = sqlUtil.getField(this.entityClass, field);
            if (null == f) {
//                logger.error("查询制定字段集失败(无法序列化"+this.entityClass+"中的"+field+"字段)");
            }
            FieldName annotation = f.getAnnotation(FieldName.class);
            if (null == annotation) {
                tabField += sqlUtil.toTableString(field) + " as " + field;
            } else {
                tabField += annotation.name() + " as " + field;
            }
            if (index < files.length) {
                tabField += ",";
            }

            index++;
        }

        String sql = "select ";
        sql += tabField + " from " + tableName + where.getWherePrams() + ";";
        List<Map<String, Serializable>> resultMap = sqlSessionTemplateASS.selectList("selectListField", sql);

        return resultMap;
    }

    @Override
    public int updateLoacl(T po) {
        Serializable id = sqlUtil.getFileValue(po, "id");

        if (null == id) {
            return 0;
        }
        String sql = "update " + tableName + " set ";
        List<Pram> prams = sqlUtil.getPramList(po);
        for (int i = 0; i < prams.size(); i++) {
            if (null != prams.get(i).getValue()) {
                sql += prams.get(i).getFile() + "=";
                Object value = prams.get(i).getValue();
                if (value instanceof byte[]) {
                    sql += "'" + new String((byte[]) value) + "'";
                } else if (value instanceof Boolean) {
                    sql += "'" + (value = true ? 1 : 0) + "'";
                } else {
                    sql += "'" + value + "'";
                }

				sql += prams.get(i).getFile() + "='" + prams.get(i).getValue() + "'";
                if (i < prams.size() - 1) {
                    sql += ",";
                }
            }
        }
        sql += " where id='" + id + "';";

        return sqlSessionTemplateASS.update("updateLocal", sql);
    }

    @Override
    public int update(T po) {
        Serializable id = sqlUtil.getFileValue(po, "id");

        if (null == id) {
            return 0;
        }
        String sql = "update " + tableName + " set ";

        List<Pram> prams = sqlUtil.getPramList(po);

        for (int i = 0; i < prams.size(); i++) {
            if (null != prams.get(i).getValue()) {
                sql += prams.get(i).getFile() + "=";
                Object value = prams.get(i).getValue();
                if (value instanceof byte[]) {
                    sql += "'" + new String((byte[]) value) + "'";
                } else if (value instanceof Boolean) {
                    sql += "'" + ( value = true ? 1 : 0) + "'";
                } else {
                    sql += "'" + value + "'";
                }
				sql += prams.get(i).getFile() + "='" + prams.get(i).getValue() + "'";
                if (i < prams.size() - 1) {
                    sql += ",";
                }
            } else {
                sql += prams.get(i).getFile() + "=null";
                if (i < prams.size() - 1) {
                    sql += ",";
                }
            }
        }
        sql += " where id='" + id + "';";

        return sqlSessionTemplateASS.update("update", sql);
    }

    @Override
    public int updateLocal(T po, WherePrams where) {
        String sql = "update " + tableName + " set ";
        List<Pram> prams = sqlUtil.getPramList(po);
        for (int i = 0; i < prams.size(); i++) {
            if (null != prams.get(i).getValue()) {
                sql += prams.get(i).getFile() + "=";
                Object value = prams.get(i).getValue();
                if (value instanceof byte[]) {
                    sql += "'" + new String((byte[]) value) + "'";
                } else if (value instanceof Boolean) {
                    sql += "'" + ( value = true ? 1 : 0) + "'";
                } else {
                    sql += "'" + value + "'";
                }
				sql += prams.get(i).getFile() + "='" + prams.get(i).getValue() + "'";
                if (i < prams.size() - 1) {
                    sql += ",";
                }
            }
        }
        sql += where.getWherePrams() + "';";

        return sqlSessionTemplateASS.update("updateLocalByPram", sql);
    }

    @Override
    public int update(T po, WherePrams where) {
        String sql = "update " + tableName + " set ";
        Object[] o = new Object[sqlParms.size()];
        for (int i = 0; i < sqlParms.size(); i++) {
            if (null != sqlParms.get(i).getValue()) {
                if (sqlParms.get(i).getValue() instanceof Boolean) {
                    sql += sqlParms.get(i).getFile() + "='" + ((boolean)(sqlParms.get(i).getValue()) == true ? 1 : 0) + "'";
                } else {
                    sql += sqlParms.get(i).getFile() + "='" + sqlParms.get(i).getValue() + "'";
                }


                if (i < sqlParms.size() - 1) {
                    sql += ",";
                }
            } else {
                sql += sqlParms.get(i).getFile() + "=null";
                if (i < sqlParms.size() - 1) {
                    sql += ",";
                }
            }
        }
        sql += where.getWherePrams() + "';";

        return sqlSessionTemplateASS.update("updateByPram", sql);
    }

    @Override
    public int delete(PK id) {
        String sql = "delete from " + tableName + " where id=" + id;

        return sqlSessionTemplateASS.delete("deleteById", sql);
    }

    @Override
    public int delete(WherePrams where) {
        String sql = "delete from " + tableName + where.getWherePrams();

        return sqlSessionTemplateASS.delete("deleteByparm", sql);
    }

    @Override
    public List<Map<String, Object>> listBySql(String sql) {
        List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectBySql", sql);
        return selectList;
    }

    @Override
    public int excuse(String sql) {
        return sqlSessionTemplateASS.update("excuse", sql);
    }

    @Override
    public long count(WherePrams where) {
        String sql = "select count(*) from ";

        sql += tableName + where.getWherePrams();

        long count = sqlSessionTemplateASS.selectOne("selectCountByParm", sql);

        return count;
    }

    @Override
    public long size() {
        String sql = "select count(*) from " + tableName;
        long count = sqlSessionTemplateASS.selectOne("selectCount", sql);
        return count;
    }

    @Override
    public boolean isExist(T po) {
        WherePrams wherePrams = Method.createDefault();

        List<Pram> list = SqlUtil.getPramListofStatic(po);

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                wherePrams = Method.where(list.get(i).getFile(), C.EQ, (Serializable) list.get(i).getValue());
            } else {
                wherePrams.and(list.get(i).getFile(), C.EQ, (Serializable) list.get(i).getValue());
            }
        }


        return count(wherePrams) > 0;
    }

    @Override
    public boolean isExist(WherePrams where) {
        return count(where) > 0;
    }

    @Override
    public List<T> in(String fileName, Serializable[] values) {
        String sql = "select ";
        for (int i = 0; i < sqlParms.size(); i++) {
            sql += selectSqlParms.get(i).getFile();
            if (i < selectSqlParms.size() - 1) {
                sql += ",";
            } else {
                sql += " ";
            }
        }
        sql += "from " + tableName + " where " + fileName + " in ";
        String value = "(";
        for (int i = 0; i < values.length; i++) {
            if (i < values.length - 1) {
                value += values[i] + ",";
            } else {
                value += values[i] + ");";
            }
        }
        sql += value;

        List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectIn", sql);

        List<T> list = new ArrayList<T>();
        for (Map<String, Object> map : selectList) {
            T t = handleResult(map, this.entityClass);
            list.add(t);
        }

        return list;
    }

    @Override
    public long nextId() {
        String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_NAME='" + tableName + "' AND TABLE_SCHEMA=(select database())";
        Long idVal = sqlSessionTemplateASS.selectOne("fetchSeqNextval", sql);
        if (null == idVal) {
            logger.error("/********************************");
//            logger.error("/��ȡ" + tableName + "�����һ������ֵʧ��");
            logger.error("/********************************");
            return 0;
        }
        return idVal;
    }

    private T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
        if (null == resultMap) {
            return null;
        }
        T t = null;
        try {
            t = tClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("/********************************");
            logger.error("实例化Bean失败(" + this.entityClass + ")!"
                    + e.getMessage());
            logger.error("/********************************");
        } catch (IllegalAccessException e) {
            logger.error("/********************************");
            logger.error("实例化Bean失败(" + this.entityClass + ")!"
                    + e.getMessage());
            logger.error("/********************************");
        }
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            Serializable val = (Serializable) entry.getValue();
            try {
                SqlUtil.setFileValue(t, key, val);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("/********************************");
                logger.error("/ʵ�л�����(" + this.entityClass + ")ʱ���ֶθ�ֵ�쳣(" + key + "):"
                        + e.getMessage());
                logger.error("/********************************");
            }
        }
        return t;
    }

    /**
     * 是否为SQL表达符号
     *
     * @param c
     * @return
     */
    private boolean isC(String c) {
        if ("=".equals(c)) {
            return true;
        } else if ("<".equals(c)) {
            return true;
        } else if (">".equals(c)) {
            return true;
        } else if ("<>".equals(c)) {
            return true;
        } else if ("like".equals(c)) {
            return true;
        } else if ("in".equals(c)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从List<String>集合中检查是否有存在的元素
     *
     * @param list
     * @param tabName
     * @return
     */
    private boolean isExcTab(List<String> list, String tabName) {
        for (String string : list) {
            if (tabName.equals(string)) {
                return true;
            }
        }
        return false;
    }
}
