package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_REQUEST_TYPES", schema = "BIDG_DBA", catalog = "")
public class CRequestTypes {
    private int idRequestType;
    private String requestTypeName;
    private Integer idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<Requests> requestsByIdRequestType;

    @Id
    @Column(name = "ID_REQUEST_TYPE", nullable = false)
    public int getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(int idRequestType) {
        this.idRequestType = idRequestType;
    }

    @Basic
    @Column(name = "REQUEST_TYPE_NAME", nullable = true, length = 100)
    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = true)
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "CREATION_DATE", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CRequestTypes that = (CRequestTypes) o;

        if (idRequestType != that.idRequestType) return false;
        if (requestTypeName != null ? !requestTypeName.equals(that.requestTypeName) : that.requestTypeName != null)
            return false;
        if (idAccessLevel != null ? !idAccessLevel.equals(that.idAccessLevel) : that.idAccessLevel != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequestType;
        result = 31 * result + (requestTypeName != null ? requestTypeName.hashCode() : 0);
        result = 31 * result + (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cRequestTypesByIdRequestType")
    public Collection<Requests> getRequestsByIdRequestType() {
        return requestsByIdRequestType;
    }

    public void setRequestsByIdRequestType(Collection<Requests> requestsByIdRequestType) {
        this.requestsByIdRequestType = requestsByIdRequestType;
    }
}
