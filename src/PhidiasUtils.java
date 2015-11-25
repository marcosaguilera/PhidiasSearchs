
import java.sql.*;

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

                //PreparedStatement st = conn.prepareStatement("SELECT COUNT(*) as total_semana FROM PERSONA INNER JOIN Eventualidad_Personas ON PERSONA.Id_Persona = Eventualidad_Personas.Persona_Id_Persona INNER JOIN Eventualidades ON Eventualidades.Id_Eventualidad = Eventualidad_Personas.Eventualidades_Id_Eventualidad INNER JOIN Subtipo_Eventualidad ON Subtipo_Eventualidad.Id_Subtipo_Eventualidad = Eventualidad_Personas.subtipo_eventualidad INNER JOIN Tipo_Eventualidad ON Tipo_Eventualidad.Id_Tipo_Eventualidad = Subtipo_Eventualidad.Tipo_Eventualidad_Id_Tipo_Eventualidad   WHERE PERSONA.Id_Persona=? AND eventualidades.Fecha BETWEEN '2014-07-01' AND '2015-07-01' AND Tipo_Eventualidad.Id_Tipo_Eventualidad = ? GROUP BY PERSONA.Id_Persona ORDER BY Eventualidades.fecha_ingreso desc");
                //PreparedStatement st = conn.prepareStatement("SELECT PERSONA.Codigo, PERSONA.email FROM PERSONA WHERE PERSONA.Codigo = ?");
                //PreparedStatement st = conn.prepareStatement("SELECT sophia_years.id, sophia_years.name, sophia_years.start_date, sophia_years.end_date FROM sophia_years WHERE sophia_years.id = ?");
                PreparedStatement st = conn.prepareStatement("SELECT yr.id, yr.name, "
                                                            + "sec.name, "
                                                            + "TRIM(std.code),"
                                                            + "ppl.firstname, "
                                                            + "ppl.lastname, "
                                                            + "ppl.document, "
                                                            + "sec.name, "
                                                            + "pc.blood "
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
                    String name = rs.getString("yr.name");
                    result = name;
                    System.out.println(result);
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
        phidiasUtils.phidiasResponse(15032);
    }  
}
