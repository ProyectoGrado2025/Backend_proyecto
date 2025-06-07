package es.daw2.restaurant_V1.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Reservas")

@Data
public class Reserva {

    @Id 
    @GeneratedValue
    @Column(name = "reserva_id")
    private Long reservaId;

    @Column(name = "reserva_fecha", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime reservaFecha;

    @Column(name = "reserva_fin", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime reservaFin;

    @Column(name = "numero_personas", nullable = false, columnDefinition = "INTEGER")
    private Integer numeroPersonas;

    @Column(name = "reserva_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservaStatus reservaStatus;
    
    public enum ReservaStatus{
        ACTIVA,
        EXPIRADA,
        CANCELADA,
        FACTURADA
    }
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @OneToOne(mappedBy = "reserva")
    private Pedido pedido;

    @ManyToMany
    @JoinTable(
        name = "reserva_alergeno",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "alergeno_id")
    )
    private List<Alergeno> alergenos;

        // @ManyToMany
    // @JoinTable(
    //     name = "reserva_mesa",
    //     joinColumns = @JoinColumn(name = "reserva_id"),
    //     inverseJoinColumns = @JoinColumn(name = "mesa_id")
    // )
    // private List<Mesa> mesas;
}