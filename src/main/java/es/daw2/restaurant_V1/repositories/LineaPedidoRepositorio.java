package es.daw2.restaurant_V1.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO;
import es.daw2.restaurant_V1.models.LineaPedido;

@Repository
public interface LineaPedidoRepositorio extends JpaRepository<LineaPedido, Long> {

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO(
            lp.plato.platoId,
            lp.plato.platoNombre,
            SUM(lp.cantidad))
        FROM LineaPedido lp
        GROUP BY lp.plato.platoId, lp.plato.platoNombre
        ORDER BY SUM(lp.cantidad) DESC
    """)
    List<PlatoVentasDTO> findTop3PlatosMasVendidos(Pageable pageable);

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO(
            lp.plato.platoId,
            lp.plato.platoNombre,
            SUM(lp.cantidad))
        FROM LineaPedido lp
        GROUP BY lp.plato.platoId, lp.plato.platoNombre
        ORDER BY SUM(lp.cantidad) ASC
    """)
    List<PlatoVentasDTO> findTop3PlatosMenosVendidos(Pageable pageable);
}