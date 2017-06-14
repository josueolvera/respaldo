package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by desarrollador on 11/06/17.
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_ORDER_DETAIL")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_ORDER_DETAIL")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestOrderDetail;

    @Column(name = "ID_REQUEST_ORDER_DOCUMENT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idRequestOrderDocument;

    @Size(max = 150)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;

    @Column(name = "QUANTITY")
    @JsonView(JsonViews.Root.class)
    private Integer quantity;

    @Column(name = "PRICE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal price;

    @Size(max = 30)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    //Relaciones
    @JoinColumn(name = "ID_REQUEST_ORDER_DOCUMENT", referencedColumnName = "ID_REQUEST_ORDER_DOCUMENT")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private RequestOrderDocuments requestOrderDocuments;

    public RequestOrderDetail() {
    }

    public RequestOrderDetail(Integer idRequestOrderDetail) {
        this.idRequestOrderDetail = idRequestOrderDetail;
    }

    public Integer getIdRequestOrderDetail() {
        return idRequestOrderDetail;
    }

    public void setIdRequestOrderDetail(Integer idRequestOrderDetail) {
        this.idRequestOrderDetail = idRequestOrderDetail;
    }

    public int getIdRequestOrderDocument() {
        return idRequestOrderDocument;
    }

    public void setIdRequestOrderDocument(int idRequestOrderDocument) {
        this.idRequestOrderDocument = idRequestOrderDocument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public RequestOrderDocuments getRequestOrderDocuments() {
        return requestOrderDocuments;
    }

    public void setRequestOrderDocuments(RequestOrderDocuments requestOrderDocuments) {
        this.requestOrderDocuments = requestOrderDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestOrderDetail)) return false;

        RequestOrderDetail that = (RequestOrderDetail) o;

        if (idRequestOrderDocument != that.idRequestOrderDocument) return false;
        return idRequestOrderDetail != null ? idRequestOrderDetail.equals(that.idRequestOrderDetail) : that.idRequestOrderDetail == null;
    }

    @Override
    public int hashCode() {
        int result = idRequestOrderDetail != null ? idRequestOrderDetail.hashCode() : 0;
        result = 31 * result + idRequestOrderDocument;
        return result;
    }

    @Override
    public String toString() {
        return "RequestOrderDetail{" +
                "idRequestOrderDetail=" + idRequestOrderDetail +
                ", idRequestOrderDocument=" + idRequestOrderDocument +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", requestOrderDocuments=" + requestOrderDocuments +
                '}';
    }
}
