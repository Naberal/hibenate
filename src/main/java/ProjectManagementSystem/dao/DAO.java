package ProjectManagementSystem.dao;

import java.util.List;

public interface DAO<T, ID> {
    void save(T t);

    void update(T t);

    void delete(ID id);

    T findByID(ID id);

    T findByName(String name);

    List<T> getAll( );

    int getCount( );
}
