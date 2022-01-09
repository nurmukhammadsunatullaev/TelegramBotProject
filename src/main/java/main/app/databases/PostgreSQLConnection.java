package main.app.databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PostgreSQLConnection {

    private   static PostgreSQLConnection sqlConnection;

    public static PostgreSQLConnection getSqlConnection() {
        if(sqlConnection==null){
            sqlConnection=new PostgreSQLConnection();
        }

        return sqlConnection;
    }

    private PostgreSQLConnection(){
       connect();
    }

    private Connection connection;
    private final String url = "jdbc:postgresql://localhost/esud";
    private final String user = "postgres";
    private final String password = "test123";

    private void  connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private ResultSet getResultSet(String sql,String ... items) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        for (int i=0;i<items.length;i++){
            preparedStatement.setString(i+1,items[i]);
        }
        return preparedStatement.executeQuery();

    }

    public List<String> getByID(String inn) throws SQLException {
            String sql_cmd="SELECT caset.id_, dateadd_, dateregis_, courtt.name_, givername_, answername_, ans_inn "+
                           "FROM public.caset inner join courtt on courtt.id_=caset.courtid_  where ans_inn=? LIMIT 2;";
            ResultSet result=getResultSet(sql_cmd,inn);
            return formatedString(result);

    }

    public List<String> getByPassportID(String pId) throws SQLException {
            String sql_cmd= "SELECT caset.id_, dateadd_, dateregis_, courtt.name_, givername_, answername_, ans_inn "+
                            "FROM public.caset inner join courtt on courtt.id_=caset.courtid_  where ans_inn=?  LIMIT 2;";
            ResultSet result=getResultSet(sql_cmd,pId);
            return formatedString(result);
    }

    public List<String> getByFullName(String fullName) throws SQLException {
            String sql_cmd="SELECT caset.id_, dateadd_, dateregis_, courtt.name_, givername_, answername_, ans_inn "+
                           "FROM public.caset inner join courtt on courtt.id_=caset.courtid_ WHERE Lower(answername_)= Lower(?)  LIMIT 2;";
            ResultSet result=getResultSet(sql_cmd,fullName);//РУЗИЕВ БОТИР ШОНАЗАРОВИЧ
            return  formatedString(result);
    }
    public String getDefinationId(String value) throws SQLException {
        String sqlId="SELECT cs_get_last_definition(CAST(? as INTEGER));";
        ResultSet resultSet=getResultSet(sqlId,value);
         if(resultSet.next()){
             return resultSet.getString(1);
         }
         return null;

    }

    private List<String> formatedString(ResultSet result) throws SQLException {
        List<String> messages=new ArrayList<>();
        StringBuilder builder=new StringBuilder();
            while(result.next()){

                String url=getDefinationId(result.getString(1));

                if(url!=null) {
                    url = new String(Base64.getEncoder().encodeToString(url
                            .replaceAll("1", "a")
                            .replaceAll("9", "f").getBytes()));
                }
                builder.append("\uD83D\uDCC6  Юборилган сана: ").append(result.getDate(2)).append("\n\n");
                builder.append("\uD83D\uDCC6  Рўйхат санаси: ").append(result.getDate(3)).append("\n\n");
                builder.append("\uD83C\uDFE2  Суд номи: ").append(result.getString(4)).append("\n\n");
                builder.append("\uD83D\uDD75\uD83C\uDFFB  Даъвогар: ").append(result.getString(5)).append("\n\n");
                builder.append("\uD83D\uDD75\uD83C\uDFFB  Жавобгар: ").append(result.getString(6)).append("\n\n");
                builder.append("\uD83D\uDCC3  Жавобгар ИИН: ").append(result.getString(7)).append("\n\n");
              if(url!=null) {
                  builder.append("\uD83D\uDCC4  Суд хужжати ").append("http://v3.esud.uz/crm/download?t=").append(url);
              }
              messages.add(builder.toString());
                builder.setLength(0);
            }
        return messages;
    }





}
