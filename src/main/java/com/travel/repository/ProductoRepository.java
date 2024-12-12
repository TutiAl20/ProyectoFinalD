package com.travel.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.travel.entity.Producto;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

  Producto findById(long id);

  Producto findByNombre(String nombre);
  List<Producto> findByCategoriaId(Long categoriaId);

  boolean existsByNombre(String nombre);
  @Query("SELECT p FROM Producto p JOIN p.fechasDisponibles f " +
          "WHERE f.fecha BETWEEN :fechaInicio AND :fechaFinal " +
          "AND (LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :query, '%'))) " +
          "GROUP BY p " +
          "ORDER BY COUNT(f.fecha) DESC, MIN(f.fecha) ASC")
  List<Producto> findProductosDisponiblesPorRangoDeFechasConContadorYFechaCercana(
          @Param("fechaInicio") LocalDate fechaInicio,
          @Param("fechaFinal") LocalDate fechaFinal,
          @Param("query") String query);

}