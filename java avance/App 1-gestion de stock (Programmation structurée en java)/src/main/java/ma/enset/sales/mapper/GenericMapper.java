package ma.enset.sales.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default Page<D> mapEntityPageIntoDtoPage(Page<E> entities) {
        return entities.map(this::toDto);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(D source, @MappingTarget E destination);
}
