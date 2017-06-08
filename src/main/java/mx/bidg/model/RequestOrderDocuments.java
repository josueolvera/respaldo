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
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Table(name="REQUEST_ORDER_DOCUMENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestOrderDocuments implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_ORDER_DOCUMENT")
    @JsonView(JsonViews.Root.class)
	private Integer idRequestOrderDocument;
	
	@Column(name = "ID_PRICE_ESTIMATIONS", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idPriceEstimation;
	
	@Size(max = 150)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
	private String filePath;
	
	@Size(max = 100)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
	private String fileName;
	
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
	@JoinColumn(name = "ID_PRICE_ESTIMATION", referencedColumnName = "ID_PRICE_ESTIMATION")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private PriceEstimations priceEstimations;
	
	//Constructores
	public RequestOrderDocuments(){
	}
	
	public RequestOrderDocuments(Integer idRequestOrderDocument){
		this.idRequestOrderDocument = idRequestOrderDocument;
	}

	//Getters and Setters
	public Integer getIdRequestOrderDocument() {
		return idRequestOrderDocument;
	}

	public void setIdRequestOrderDocument(Integer idRequestOrderDocument) {
		this.idRequestOrderDocument = idRequestOrderDocument;
	}

	public int getIdPriceEstimation() {
		return idPriceEstimation;
	}

	public void setIdPriceEstimation(int idPriceEstimation) {
		this.idPriceEstimation = idPriceEstimation;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public PriceEstimations getPriceEstimations() {
		return priceEstimations;
	}

	public void setPriceEstimations(PriceEstimations priceEstimations) {
		this.priceEstimations = priceEstimations;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RequestOrderDocuments that = (RequestOrderDocuments) o;

		if (idPriceEstimation != that.idPriceEstimation) return false;
		if (!idRequestOrderDocument.equals(that.idRequestOrderDocument)) return false;
		if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
		if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
		if (username != null ? !username.equals(that.username) : that.username != null) return false;
		if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
		return priceEstimations != null ? priceEstimations.equals(that.priceEstimations) : that.priceEstimations == null;
	}

	@Override
	public int hashCode() {
		int result = idRequestOrderDocument.hashCode();
		result = 31 * result + idPriceEstimation;
		result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
		result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
		result = 31 * result + (priceEstimations != null ? priceEstimations.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "RequestOrderDocuments{" +
				"idRequestOrderDocument=" + idRequestOrderDocument +
				", idPriceEstimation=" + idPriceEstimation +
				", filePath='" + filePath + '\'' +
				", fileName='" + fileName + '\'' +
				", username='" + username + '\'' +
				", creationDate=" + creationDate +
				", priceEstimations=" + priceEstimations +
				'}';
	}
}