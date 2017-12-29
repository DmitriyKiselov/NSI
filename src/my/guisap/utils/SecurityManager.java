/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author DonchenkoUA
 */
public class SecurityManager {

    public static final Integer ADMINS = 0;
    public static final Integer UNLOADING = 3;
    public static final Integer TEHNOLOGES = 1;
    public static final Integer OPP = 2;
    public static final Integer PEO = 4;
    // Сюда передаем идентификатор группы и проверяем можно ли отобразить
    PreparedStatement ps;
    SqlOperations sql = new SqlOperations();
    public static int idGroup;
    public static String userName;
    private int privilege;
    private static SecurityManager SecurityManager;

    private SecurityManager() {
        getPrivilege();
    }

    public static SecurityManager getInstance() {
        if (SecurityManager == null) {
            SecurityManager = new SecurityManager();
        }
        return SecurityManager;
    }

    private void getPrivilege() {
        try {
            ps = sql.preparingTableFill(SqlOperations.SELECT_SECURITY_GROUP_KEY);
            if (ps != null) {
                ps.setString(1, String.valueOf(idGroup));
                String temp = sql.psStrFill(ps);
                privilege = Integer.parseInt(temp);
                ps.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasPremition(ArrayList<Integer> priv) {
        boolean flagPriv = false;
        ArrayList<Integer> allowedPrivileges;
        allowedPrivileges = priv;
        for (Integer ap : allowedPrivileges) {
            if (ap == privilege) {
                flagPriv = true;
            }
        }
        return flagPriv;
    }
}
