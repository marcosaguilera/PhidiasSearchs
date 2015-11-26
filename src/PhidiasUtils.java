
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author rochester
 */
public class PhidiasUtils { 

    public String phidiasResponse(int codigo){
        System.out.println(codigo);
            String result  = "";
            try{
                String myDriver = "org.gjt.mm.mysql.Driver";
                String myUrl = "jdbc:mysql://54.82.28.227/phidias_rochester";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "rochester", "BS3u9HAh");
                PreparedStatement st = conn.prepareStatement("SELECT yr.id, yr.name, "
                                                            + "sec.name, "
                                                            + "TRIM(std.code), "
                                                            + "ppl.firstname, "
                                                            + "ppl.lastname, "
                                                            + "ppl.document, "
                                                            + "pc.blood, "
                                                            + "(GROUP_CONCAT(DISTINCT CONCAT(ps.name,ps.id)) REGEXP 'Seguro de accidentes14') as SA, "
                                                            + "(GROUP_CONCAT(DISTINCT CONCAT(ps.name,ps.id)) REGEXP 'Almuerzo4') AS AL, "
                                                            + "(GROUP_CONCAT(DISTINCT CONCAT(ps.name,ps.id)) REGEXP 'Medias Nueves5') AS MN "
                                                            + "FROM sophia_people ppl "
                                                            + "LEFT join sophia_people_students std ON ppl.id = std.id "
                                                            + "LEFT join sophia_person_clinics pc ON ppl.id = pc.person "
                                                            + "LEFT join sophia_people_set_memberships psm ON ppl.id = psm.person "
                                                            + "LEFT join sophia_people_sets ps ON psm.set = ps.id "
                                                            + "INNER JOIN sophia_course_section_enrollments enroll ON enroll.person = ppl.id "
                                                            + "INNER JOIN sophia_course_sections sec ON sec.id = enroll.section "
                                                            + "INNER JOIN sophia_years yr ON yr.id = sec.year "
                                                            + "WHERE ppl.type = 1 AND yr.id = 2 AND TRIM(std.code) = ? AND enroll.status = 2 "
                                                            + "GROUP BY ppl.id "
                                                            + "ORDER BY sec.course, sec.name");
                
                st.setInt(1, codigo);
                //st.setDate(2, fecha);
                //st.setInt(2, tipo);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    JSONObject obj = new JSONObject();
                    
                    String idyear       = rs.getString("yr.id");
                    String year         = rs.getString("yr.name");
                    String curso        = rs.getString("sec.name");
                    String codigo_std   = rs.getString("TRIM(std.code)");
                    String name         = rs.getString("ppl.firstname");
                    String apellido     = rs.getString("ppl.lastname");
                    String doc          = rs.getString("ppl.document");
                    Integer al          = rs.getInt("AL");
                    Integer sa          = rs.getInt("SA");
                    Integer mn          = rs.getInt("AL");
                    
                    //result = name;
                    obj.put("idyear", idyear);
                    obj.put("year", year);
                    obj.put("curso", curso);
                    obj.put("codigo", codigo_std);
                    obj.put("nombres", name);
                    obj.put("apellidos", apellido);
                    obj.put("doc", doc);
                    obj.put("almuerzo", al);
                    obj.put("seguro_accidentes", sa);
                    obj.put("medias_nueves", mn);
                    
                    System.out.println(obj);
                    System.out.format("%s", result);
                }
                st.close();

            }catch(Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        return result;
    }
    
    public static void main(String[]arg){
        PhidiasUtils phidiasUtils = new PhidiasUtils();
        phidiasUtils.phidiasResponse(15031);
    }  
}
