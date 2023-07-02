package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    void create(T t) throws SQLException;

    void update(T t);

    T get(Integer id);

    void delete(Integer id);

    List<T> listAll();
}
