
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
                String myUrl = "jdbc:mysql://yoda.rochester.edu.co/aprendoz_desarrollo";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "irc4Quag");

                //PreparedStatement st = conn.prepareStatement("SELECT COUNT(*) as total_semana FROM PERSONA INNER JOIN Eventualidad_Personas ON PERSONA.Id_Persona = Eventualidad_Personas.Persona_Id_Persona INNER JOIN Eventualidades ON Eventualidades.Id_Eventualidad = Eventualidad_Personas.Eventualidades_Id_Eventualidad INNER JOIN Subtipo_Eventualidad ON Subtipo_Eventualidad.Id_Subtipo_Eventualidad = Eventualidad_Personas.subtipo_eventualidad INNER JOIN Tipo_Eventualidad ON Tipo_Eventualidad.Id_Tipo_Eventualidad = Subtipo_Eventualidad.Tipo_Eventualidad_Id_Tipo_Eventualidad   WHERE PERSONA.Id_Persona=? AND eventualidades.Fecha BETWEEN '2014-07-01' AND '2015-07-01' AND Tipo_Eventualidad.Id_Tipo_Eventualidad = ? GROUP BY PERSONA.Id_Persona ORDER BY Eventualidades.fecha_ingreso desc");
                PreparedStatement st = conn.prepareStatement("SELECT PERSONA.Codigo, PERSONA.email FROM PERSONA WHERE PERSONA.Codigo = ?");
                
                st.setInt(1, codigo);
                //st.setDate(2, fecha);
                //st.setInt(2, tipo);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    String email = rs.getString("PERSONA.email");
                    result = email;
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
        phidiasUtils.phidiasResponse(80019669);
    }  
}
