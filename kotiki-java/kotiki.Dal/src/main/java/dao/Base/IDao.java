package dao.Base;

import java.util.List;

public interface IDao<T>
{
    T findById(int id);
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> findAll();
}
