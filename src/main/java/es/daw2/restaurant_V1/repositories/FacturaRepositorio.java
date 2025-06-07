package es.daw2.restaurant_V1.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.dtos.stadistics.clientes.GastoMedioClienteDTO;
import es.daw2.restaurant_V1.models.Factura;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Long>{

    @Query("""
             SELECT new es.daw2.restaurant_V1.dtos.stadistics.clientes.GastoMedioClienteDTO(f.cliente.id, AVG(f.facturaPrecioTotal))
             FROM Factura f 
             GROUP BY f.cliente.id
    """)
    List<GastoMedioClienteDTO> GastoMedioPorCliente();

    @Query("""
            SELECT AVG(f.facturaPrecioTotal) 
            FROM Factura f
    """)
    BigDecimal GastoMedioGlobal();

    @Query("""
            SELECT AVG(f.facturaPrecioTotal)
            FROM Factura f
            WHERE f.cliente.id = :clienteId  
    """)
    BigDecimal GastoMedioPorClienteId(@Param("clienteId") Long clienteId);

    @Query("""
            SELECT SUM(f.facturaPrecioTotal)   
            FROM Factura f
    """)
    BigDecimal IngresoTotal();

    @Query("""
             SELECT SUM(f.facturaPrecioTotal)
             FROM Factura f
             WHERE f.facturaFecha BETWEEN :fechaInicio AND :fechaFin
    """)
    BigDecimal calcularIngresosEntreFechas(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin
    );
}