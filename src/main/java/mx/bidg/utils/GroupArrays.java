package mx.bidg.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */

public class GroupArrays<T> {

    public ArrayList<ArrayList<T>> groupInArray(ArrayList<T> list, Filter<T> filter) {
        ArrayList<ArrayList<T>> result = new ArrayList<>();
        HashMap<String, ArrayList<T>> groups;

        groups = this.groupInMap(list, filter);

        for (Map.Entry<String, ArrayList<T>> entry : groups.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }

    public HashMap<String, ArrayList<T>> groupInMap(ArrayList<T> list, Filter<T> filter) {
        HashMap<String, ArrayList<T>> groups = new HashMap<>();

        for (T item : list) {
            String groupName = filter.filter(item);
            ArrayList<T> group = groups.get(groupName);

            if (group == null) {
                group = new ArrayList<>();
                groups.put(groupName, group);
            }

            group.add(item);
        }

        return groups;
    }

    public interface Filter<T> {
        public String filter(T item);
    }
}
