package mx.bidg.service.impl;

import mx.bidg.model.*;
import mx.bidg.service.ApplicationMenuService;
import mx.bidg.service.ViewsRolesService;
import mx.bidg.utils.GroupArrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rafael Viveros
 * Created on 1/12/15.
 */
@Service
@Transactional
public class ApplicationMenuServiceImpl implements ApplicationMenuService {

    @Autowired
    ViewsRolesService viewsRolesService;

    private final GroupArrays<CViews> viewsGroupArrays = new GroupArrays<>();
    private final GroupArrays<CModules> modulesGroupArrays = new GroupArrays<>();

    @Override
    public List<CSystems> buildMenuForRoles(List<UsersRole> usersRoles) {
        ArrayList<CViews> views = new ArrayList<>();
        for (ViewsRole viewRol : viewsRolesService.findViewsRolesFor(usersRoles)) {
            if (viewRol.getView().isShowInMenu()) {
                views.add(viewRol.getView());
            }
        }

        HashMap<String, ArrayList<CViews>> viewsGroups = viewsGroupArrays.groupInMap(views, new GroupArrays.Filter<CViews>() {
            @Override
            public String filter(CViews item) {
                return item.getIdModule().toString();
            }
        });

        ArrayList<CModules> modules = new ArrayList<>();
        for (Map.Entry<String, ArrayList<CViews>> entry : viewsGroups.entrySet()) {
            CModules module = entry.getValue().get(0).getModule();
            module.setCViewsList(viewsGroups.get(module.getIdModule().toString()));
            modules.add(module);
        }

        HashMap<String, ArrayList<CModules>> modulesGroups = modulesGroupArrays.groupInMap(modules, new GroupArrays.Filter<CModules>() {
            @Override
            public String filter(CModules item) {
                return item.getIdSystem().toString();
            }
        });

        ArrayList<CSystems> systems = new ArrayList<>();
        for (Map.Entry<String, ArrayList<CModules>> entry : modulesGroups.entrySet()) {
            CSystems system = entry.getValue().get(0).getSystem();
            system.setCModulesList(modulesGroups.get(system.getIdSystem().toString()));
            systems.add(system);
        }

        return systems;
    }
}
