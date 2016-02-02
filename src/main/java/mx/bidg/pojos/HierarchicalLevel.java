package mx.bidg.pojos;

import mx.bidg.model.DwEnterprises;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 29/01/16.
 */
public class HierarchicalLevel {
    private Integer id;
    private String name;
    private String alias;
    private DwEnterprises dwEnterprise;
    private List<HierarchicalLevel> subLevels;

    public HierarchicalLevel() {
        this.subLevels = new ArrayList<>();
    }

    public HierarchicalLevel(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.subLevels = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<HierarchicalLevel> getSubLevels() {
        return subLevels;
    }

    public void setSubLevels(List<HierarchicalLevel> subLevels) {
        this.subLevels = subLevels;
    }

    public void addSubLevel(HierarchicalLevel subLevel) {
        this.subLevels.add(subLevel);
    }

    public DwEnterprises getDwEnterprise() {
        return dwEnterprise;
    }

    public void setDwEnterprise(DwEnterprises dwEnterprise) {
        this.dwEnterprise = dwEnterprise;
    }
}
