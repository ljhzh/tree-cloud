package sdu.wocl.web.dao;

public interface BaseDao<T> {
    
    void save(T t);
    
    T delete(T t);
    
}
