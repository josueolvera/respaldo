package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHORIZATION_TREE_RULES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AuthorizationTreeRules implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RULE")
    @JsonView(JsonViews.Root.class)
    private Integer idRule;

    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "RULE_NAME")
    @JsonView(JsonViews.Root.class)
    private String ruleName;

    @Lob
    @Basic(optional = false)
    @NotNull
    @Column(name = "RULE_CODE")
    @JsonView(JsonViews.Root.class)
    private String ruleCode;

    public AuthorizationTreeRules() {}

    public AuthorizationTreeRules(Integer idRule) {
        this.idRule = idRule;
    }

    public AuthorizationTreeRules(String ruleName, String ruleCode) {
        this.ruleName = ruleName;
        this.ruleCode = ruleCode;
    }

    public Integer getIdRule() {
        return idRule;
    }

    public void setIdRule(Integer idRule) {
        this.idRule = idRule;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationTreeRules that = (AuthorizationTreeRules) o;

        return idRule.equals(that.idRule);

    }

    @Override
    public int hashCode() {
        return idRule.hashCode();
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AuthorizationTreeRules{" +
                "idRule=" + idRule +
                '}';
    }
}
