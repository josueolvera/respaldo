/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.config;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

/**
 *
 * @author sistemask
 */
@Component
public class ActiveSessionsList {
    
    private HashMap<String, HttpSession> listSessions;
    
    public  ActiveSessionsList() {
        this.listSessions = new HashMap<>();
    }
    
    public void addSession(HttpSession session) {
        listSessions.put(session.getId(), session);
    }
    
    public HttpSession getSession(String idSession) {
        return (listSessions.containsKey(idSession))? listSessions.get(idSession) : null;
    }
    
    public void removeSession(String idSession) {
        listSessions.remove(idSession);
    }

    public HashMap<String, HttpSession> getSessionList() {
        return listSessions;
    }
    
}
