package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gerardo8 on 22/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "CHECKS")
public class Checks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK")
    @JsonView(JsonViews.Root.class)
    private Integer idCheck;

    @Column(name = "ID_TRAVEL_EXPENSE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpense;

    @JoinColumn(name = "ID_TRAVEL_EXPENSE", referencedColumnName = "ID_TRAVEL_EXPENSE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private TravelExpenses travelExpense;
}
