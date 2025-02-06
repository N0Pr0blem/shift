package by.koronatech.office.mapper.base;

import java.util.List;

public interface Mappable<E,D> {
    E toEntity(D d);
    D toDto(E e);

    List<E> toEntities(Iterable<D> list);
    List<D> toDtos(Iterable<E> list);
}
