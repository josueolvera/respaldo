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
@Table(name = "C_MODULE_STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CModuleStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MODULE_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idModuleStatus;
	
	@Size(max = 45)
    @Column(name = "MODULE_STATUS_NAME")
    @JsonView(JsonViews.Root.class)
	private String moduleStatusName;
	
	@Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
	private int idAccessLevel;
	
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
	
	//Constructores
	public CModuleStatus(){
	}
	
	public CModuleStatus(Integer idModuleStatus){
		this.idModuleStatus = idModuleStatus;
	}

	//Getters and Setters
    public Integer getIdModuleStatus() {
        return idModuleStatus;
    }

    public void setIdModuleStatus(Integer idModuleStatus) {
        this.idModuleStatus = idModuleStatus;
    }

    public String getModuleStatusName() {
        return moduleStatusName;
    }

    public void setModuleStatusName(String moduleStatusName) {
        this.moduleStatusName = moduleStatusName;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CModuleStatus that = (CModuleStatus) o;

        if (idAccessLevel != that.idAccessLevel) return false;
        if (!idModuleStatus.equals(that.idModuleStatus)) return false;
        if (moduleStatusName != null ? !moduleStatusName.equals(that.moduleStatusName) : that.moduleStatusName != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = idModuleStatus.hashCode();
        result = 31 * result + (moduleStatusName != null ? moduleStatusName.hashCode() : 0);
        result = 31 * result + idAccessLevel;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CModuleStatus{" +
                "idModuleStatus=" + idModuleStatus +
                ", moduleStatusName='" + moduleStatusName + '\'' +
                ", idAccessLevel=" + idAccessLevel +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}