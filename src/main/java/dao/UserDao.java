package dao;

public interface UserDao<T> extends GenericDao<T> {
    T get(String email);
}
