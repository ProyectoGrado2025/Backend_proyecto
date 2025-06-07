package es.daw2.restaurant_V1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClientePuntosDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.DistribucionClientesPorRangoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.MediaPuntosPorRangoDTO;
import es.daw2.restaurant_V1.models.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.clientes.DistribucionClientesPorRangoDTO(
            c.rango.nombreRango, COUNT(c))
        FROM Cliente c
        GROUP BY c.rango.nombreRango
    """)
    List<DistribucionClientesPorRangoDTO> contarClientesPorRango();

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.clientes.MediaPuntosPorRangoDTO(
            c.rango.nombreRango, AVG(c.puntosFidelizacion))
        FROM Cliente c
        GROUP BY c.rango.nombreRango
    """)
    List<MediaPuntosPorRangoDTO> mediaPuntosPorRango();

    @Query("""
        SELECT SUM(c.puntosFidelizacion)
        FROM Cliente c
    """)
    Long totalPuntosFidelizacion();

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.clientes.ClientePuntosDTO(
            c.id, c.clienteNombre, c.puntosFidelizacion, c.rango.nombreRango)
        FROM Cliente c
        ORDER BY c.puntosFidelizacion DESC
    """)
    List<ClientePuntosDTO> topClientesPorPuntos(Pageable pageable);
}