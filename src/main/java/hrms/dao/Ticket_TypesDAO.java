package hrms.dao;

import java.util.ArrayList;
import java.util.List;

import hrms.model.Ticket_Types;
import hrms.utils.DBContext;

public class Ticket_TypesDAO extends DBContext {
    public Ticket_Types getTicketTypeById(int id) {
        String sql = "select * from Ticket_Types where ticket_Type_ID = ?";
        try {
            var st = connection.prepareStatement(sql);
            st.setInt(1, id);
            var rs = st.executeQuery();
            if (rs.next()) {
                return new hrms.model.Ticket_Types(
                        rs.getInt("ticket_Type_ID"),
                        rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Ticket_Types> getAllTicketTypes() {
        List<Ticket_Types> list = new ArrayList<>();
        String sql = "select * from Ticket_Types";
        try {
            var st = connection.prepareStatement(sql);
            var rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Ticket_Types(
                        rs.getInt("ticket_Type_ID"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

}
