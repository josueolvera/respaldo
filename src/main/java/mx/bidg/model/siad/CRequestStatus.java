package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_REQUEST_STATUS", schema = "BIDG_DBA", catalog = "")
public class CRequestStatus {
    private int idRequestStatus;
    private String requestStatusName;
    private int idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<Requests> requestsByIdRequestStatus;

    @Id
    @Column(name = "ID_REQUEST_STATUS", nullable = false)
    public int getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(int idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    @Basic
    @Column(name = "REQUEST_STATUS_NAME", nullable = false, length = 100)
    public String getRequestStatusName() {
        return requestStatusName;
    }

    public void setRequestStatusName(String requestStatusName) {
        this.requestStatusName = requestStatusName;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = false)
    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
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

        CRequestStatus that = (CRequestStatus) o;

        if (idRequestStatus != that.idRequestStatus) return false;
        if (idAccessLevel != that.idAccessLevel) return false;
        if (requestStatusName != null ? !requestStatusName.equals(that.requestStatusName) : that.requestStatusName != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequestStatus;
        result = 31 * result + (requestStatusName != null ? requestStatusName.hashCode() : 0);
        result = 31 * result + idAccessLevel;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cRequestStatusByIdRequestStatus")
    public Collection<Requests> getRequestsByIdRequestStatus() {
        return requestsByIdRequestStatus;
    }

    public void setRequestsByIdRequestStatus(Collection<Requests> requestsByIdRequestStatus) {
        this.requestsByIdRequestStatus = requestsByIdRequestStatus;
    }
}
