package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
	
	@Column(name = "ID_PRICE_ESTIMATION", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idPriceEstimation;

	@Column(name = "ID_PROVIDER", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idProvider;

	@Column(name = "ID_REQUEST", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idRequest;

	@Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idDistributor;
	
	@Size(max = 150)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
	private String filePath;
	
	@Size(max = 100)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
	private String fileName;

	@Size(max = 100)
	@Column(name = "CONTACT_NAME_TRANSMITTER")
	@JsonView(JsonViews.Root.class)
	private String contactNameTransmitter;

	@Size(max = 100)
	@Column(name = "CONTACT_ROLE_TRANSMITTER")
	@JsonView(JsonViews.Root.class)
	private String contactRoleTransmitter;

	@Size(max = 100)
	@Column(name = "CONTACT_TEL_TRANSMITTER")
	@JsonView(JsonViews.Root.class)
	private String contactTelTransmitter;

	@Column(name = "AMOUNT")
	@JsonView(JsonViews.Root.class)
	private BigDecimal amount;

	@Column(name = "IVA")
	@JsonView(JsonViews.Root.class)
	private BigDecimal iva;

	@Column(name = "TOTAL_AMOUNT")
	@JsonView(JsonViews.Root.class)
	private BigDecimal totalAmount;
	
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

	@JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
	@ManyToOne(optional = true)
	@JsonView(JsonViews.Embedded.class)
	private Providers provider;

	@JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
	@ManyToOne(optional = true)
	@JsonView(JsonViews.Embedded.class)
	private Requests request;

	@JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
	@ManyToOne(optional = true)
	@JsonView(JsonViews.Embedded.class)
	private CDistributors cDistributor;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requestOrderDocuments")
	@JsonView(JsonViews.Embedded.class)
	private List<RequestOrderDetail> requestOrderDetails;
	
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

	public int getIdProvider() {
		return idProvider;
	}

	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}

	public int getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}

	public int getIdDistributor() {
		return idDistributor;
	}

	public void setIdDistributor(int idDistributor) {
		this.idDistributor = idDistributor;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public Requests getRequest() {
		return request;
	}

	public void setRequest(Requests request) {
		this.request = request;
	}

	public CDistributors getcDistributor() {
		return cDistributor;
	}

	public void setcDistributor(CDistributors cDistributor) {
		this.cDistributor = cDistributor;
	}

	public String getContactNameTransmitter() {
		return contactNameTransmitter;
	}

	public void setContactNameTransmitter(String contactNameTransmitter) {
		this.contactNameTransmitter = contactNameTransmitter;
	}

	public String getContactRoleTransmitter() {
		return contactRoleTransmitter;
	}

	public void setContactRoleTransmitter(String contactRoleTransmitter) {
		this.contactRoleTransmitter = contactRoleTransmitter;
	}

	public String getContactTelTransmitter() {
		return contactTelTransmitter;
	}

	public void setContactTelTransmitter(String contactTelTransmitter) {
		this.contactTelTransmitter = contactTelTransmitter;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<RequestOrderDetail> getRequestOrderDetails() {
		return requestOrderDetails;
	}

	public void setRequestOrderDetails(List<RequestOrderDetail> requestOrderDetails) {
		this.requestOrderDetails = requestOrderDetails;
	}

	public DateFormatsPojo getCreationDateFormats() {
		return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RequestOrderDocuments)) return false;

		RequestOrderDocuments that = (RequestOrderDocuments) o;

		if (idPriceEstimation != that.idPriceEstimation) return false;
		if (idProvider != that.idProvider) return false;
		if (idRequest != that.idRequest) return false;
		if (idDistributor != that.idDistributor) return false;
		return idRequestOrderDocument != null ? idRequestOrderDocument.equals(that.idRequestOrderDocument) : that.idRequestOrderDocument == null;
	}

	@Override
	public int hashCode() {
		int result = idRequestOrderDocument != null ? idRequestOrderDocument.hashCode() : 0;
		result = 31 * result + idPriceEstimation;
		result = 31 * result + idProvider;
		result = 31 * result + idRequest;
		result = 31 * result + idDistributor;
		return result;
	}

	@Override
	public String toString() {
		return "RequestOrderDocuments{" +
				"idRequestOrderDocument=" + idRequestOrderDocument +
				", idPriceEstimation=" + idPriceEstimation +
				", idProvider=" + idProvider +
				", idRequest=" + idRequest +
				", idDistributor=" + idDistributor +
				", filePath='" + filePath + '\'' +
				", fileName='" + fileName + '\'' +
				", contactNameTransmitter='" + contactNameTransmitter + '\'' +
				", contactRoleTransmitter='" + contactRoleTransmitter + '\'' +
				", contactTelTransmitter='" + contactTelTransmitter + '\'' +
				", amount=" + amount +
				", iva=" + iva +
				", totalAmount=" + totalAmount +
				", username='" + username + '\'' +
				", creationDate=" + creationDate +
				", priceEstimations=" + priceEstimations +
				", provider=" + provider +
				", request=" + request +
				", cDistributor=" + cDistributor +
				'}';
	}
}