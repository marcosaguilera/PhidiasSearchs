
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.JSONObject;


public class PhidiasUtilsArrayList {
     private String myDriver = "org.gjt.mm.mysql.Driver";
     private String myUrl = "jdbc:mysql://54.82.28.227/phidias_rochester";
    
     public ArrayList<PhidiasModel> phidiasResponse(int codigo){ 
            System.out.println(codigo);
            String result  = "";
            ArrayList<PhidiasModel> MyData = new ArrayList();
            PhidiasModel dataModel = new PhidiasModel();
           
            try{    
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "rochester", "BS3u9HAh");
                PreparedStatement st = conn.prepareStatement( "SELECT yr.id, yr.name, "
                                                            + "sec.course, "
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

                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    
                    String idyear       = rs.getString("yr.id");
                    String year         = rs.getString("yr.name");
                    String idcurso      = rs.getString("sec.course");
                    String curso        = rs.getString("sec.name");
                    String codigo_std   = rs.getString("TRIM(std.code)");
                    String name         = rs.getString("ppl.firstname");
                    String apellido     = rs.getString("ppl.lastname");
                    String doc          = rs.getString("ppl.document");
                    Integer al          = rs.getInt("AL");
                    Integer sa          = rs.getInt("SA");
                    Integer mn          = rs.getInt("AL");
                    
                    dataModel.setIdyear(idyear);
                    dataModel.setYear(year);
                    dataModel.setIdcurso(idcurso);
                    dataModel.setCurso(curso);
                    dataModel.setCodigo_std(codigo_std);
                    dataModel.setName(name);
                    dataModel.setApellido(apellido);
                    dataModel.setDoc(doc);
                    dataModel.setAl(al);
                    dataModel.setSa(sa);
                    dataModel.setMn(mn);
                    MyData.add(dataModel);
                    
                    System.out.format("%s", result);
                }
                st.close();

            }catch(Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
            
        return MyData;
    }
     
    public ArrayList<FatherDataModel> getFatherData(int codigo){
            System.out.println(codigo);
            String result  = "";
            ArrayList<FatherDataModel> MyDataFather = new ArrayList();
            FatherDataModel dataModel = new FatherDataModel();
            
            try{
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "rochester", "BS3u9HAh");
                PreparedStatement st = conn.prepareStatement( "SELECT TRIM(std.code), "
                                                            + "estudiante.firstname, "
                                                            + "estudiante.lastname, "
                                                            + "papa.document, "
                                                            + "papa.firstname, "
                                                            + "papa.lastname, "
                                                            + "papa.gender, "
                                                            + "papa.email "
                                                            + "FROM sophia_people estudiante INNER JOIN sophia_people_relations ON estudiante.id = sophia_people_relations.person "
                                                            + "INNER JOIN sophia_people mama ON mama.id = sophia_people_relations.relative "
                                                            + "INNER JOIN sophia_people papa ON papa.id = sophia_people_relations.relative "
                                                            + "INNER JOIN sophia_people_students std ON estudiante.id = std.id "
                                                            + "WHERE TRIM(std.code) = ? AND papa.gender = 1 ");
                
                st.setInt(1, codigo);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    
                    String codigo_std       = rs.getString("TRIM(std.code)");
                    String nombre           = rs.getString("estudiante.firstname");
                    String apellido         = rs.getString("estudiante.lastname");
                    String papa_doc         = rs.getString("papa.document");
                    String papa_nombre      = rs.getString("papa.firstname");
                    String papa_apellido    = rs.getString("papa.lastname");
                    String papa_gender      = rs.getString("papa.gender");
                    String papa_email       = rs.getString("papa.email");
                    
                    dataModel.setCodigo_std_2(codigo_std);
                    dataModel.setName_2(nombre);
                    dataModel.setApellido_2(apellido);
                    dataModel.setPapa_doc(papa_doc);
                    dataModel.setPapa_nombre(papa_nombre);
                    dataModel.setApellido_2(papa_apellido);
                    dataModel.setPapa_gender(papa_gender);
                    dataModel.setPapa_email(papa_email);
                    MyDataFather.add(dataModel);
                    
                    System.out.format("%s", result);
                }
                st.close();

            }catch(Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        return MyDataFather;
    } 
     
    public ArrayList<MotherDataModel> getMotherData(int codigo){
            System.out.println(codigo);
            String result  = "";
            ArrayList<MotherDataModel> MyDataFather = new ArrayList();
            MotherDataModel dataModel = new MotherDataModel();
            
            try{
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "rochester", "BS3u9HAh");
                PreparedStatement st = conn.prepareStatement( "SELECT TRIM(std.code), "
                                                            + "estudiante.firstname, "
                                                            + "estudiante.lastname, "
                                                            + "papa.document, "
                                                            + "papa.firstname, "
                                                            + "papa.lastname, "
                                                            + "papa.gender, "
                                                            + "papa.email "
                                                            + "FROM sophia_people estudiante INNER JOIN sophia_people_relations ON estudiante.id = sophia_people_relations.person "
                                                            + "INNER JOIN sophia_people mama ON mama.id = sophia_people_relations.relative "
                                                            + "INNER JOIN sophia_people papa ON papa.id = sophia_people_relations.relative "
                                                            + "INNER JOIN sophia_people_students std ON estudiante.id = std.id "
                                                            + "WHERE TRIM(std.code) = ? AND papa.gender = 0 ");
                
                st.setInt(1, codigo);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    
                    String codigo_std       = rs.getString("TRIM(std.code)");
                    String nombre           = rs.getString("estudiante.firstname");
                    String apellido         = rs.getString("estudiante.lastname");
                    String papa_doc         = rs.getString("papa.document");
                    String papa_nombre      = rs.getString("papa.firstname");
                    String papa_apellido    = rs.getString("papa.lastname");
                    String papa_gender      = rs.getString("papa.gender");
                    String papa_email       = rs.getString("papa.email");
                    
                    dataModel.setCodigo_std_2(codigo_std);
                    dataModel.setName_2(nombre);
                    dataModel.setApellido_2(apellido);
                    dataModel.setPapa_doc(papa_doc);
                    dataModel.setPapa_nombre(papa_nombre);
                    dataModel.setApellido_2(papa_apellido);
                    dataModel.setPapa_gender(papa_gender);
                    dataModel.setPapa_email(papa_email);
                    MyDataFather.add(dataModel);
                    
                    System.out.format("%s", result);
                }
                st.close();

            }catch(Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        return MyDataFather;
    } 
    
    public static void main(String[]arg){
        PhidiasUtilsArrayList phidiasUtilsArrayList = new PhidiasUtilsArrayList();
        phidiasUtilsArrayList.phidiasResponse(15031);
        phidiasUtilsArrayList.getFatherData(15031);
        phidiasUtilsArrayList.getMotherData(15031);
    }
}
