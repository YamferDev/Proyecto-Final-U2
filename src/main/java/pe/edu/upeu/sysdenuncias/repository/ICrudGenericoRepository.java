package pe.edu.upeu.sysdenuncias.repository;

import java.util.List;
import java.util.Optional;

/**
 * Principio POO → Abstracción:
 * Interfaz genérica para operaciones CRUD (Create, Read, Update, Delete).
 */
public interface ICrudGenericoRepository<T, ID> {
    T save(T entity);
    T update(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
}
