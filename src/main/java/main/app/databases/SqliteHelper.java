package main.app.databases;

import main.app.models.UserModel;
import org.sqlite.JDBC;
import org.telegram.telegrambots.api.objects.Update;

import java.sql.*;

public class SqliteHelper {

    private Connection connection;

    private static SqliteHelper helper;

    public static SqliteHelper getHelper() throws SQLException {
        if(helper==null){
            helper=new SqliteHelper();
        }
        return helper;
    }

    private SqliteHelper() throws SQLException {
        init();
    }
    public void init() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection=DriverManager.getConnection("jdbc:sqlite:user.db");
        initFirstTime();
    }

    private void initFirstTime() throws SQLException {
        String createTable="CREATE TABLE IF NOT EXISTS users(" +
                " userId integer primary key," +
                " userFirstName varchar(128)," +
                " userLastName varchar(128), " +
                " userUserName varchar (128)," +
                " accept tinyint);";
        PreparedStatement preparedStatement=connection.prepareStatement(createTable);
        preparedStatement.execute();
    }

    public UserModel getUserModelById(Long userId) throws SQLException {
        String selectSQL="SELECT *FROM users WHERE userId=?";
        PreparedStatement statement=connection.prepareStatement(selectSQL);
        statement.setLong(1,userId);
        ResultSet resultSet=statement.executeQuery();
        UserModel model=null;
        if(resultSet.next()){
            System.out.println(userId);
            model=new UserModel(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
            model.setAccept(resultSet.getBoolean(5));
        }
        return model;
    }

    public void save(UserModel usersModel) throws SQLException {
        String insertSQL="INSERT INTO users VALUES(?,?,?,?,?);";
        PreparedStatement statement=connection.prepareStatement(insertSQL);
        statement.setLong(1,usersModel.getUserId());
        statement.setString(2,usersModel.getUserFirstName());
        statement.setString(3,usersModel.getUserLastName());
        statement.setString(4,usersModel.getUserUserName());
        statement.setString(5,usersModel.isAccept()? "1" : "0");
        statement.executeUpdate();
    }

    public static void registration(Update update) throws SQLException {
        if(update.getMessage()!=null&&update.getMessage().hasText()){
            if(helper==null){
                helper=new SqliteHelper();
            }

            UserModel model=helper.getUserModelById(update.getMessage().getChat().getId());
            if(model==null){
                UserModel newUser=new UserModel();
                newUser.setUserId(update.getMessage().getChat().getId());
                newUser.setUserFirstName(update.getMessage().getChat().getFirstName());
                newUser.setUserLastName(update.getMessage().getChat().getLastName());
                newUser.setUserUserName(update.getMessage().getChat().getUserName());
                newUser.setAccept(true);
                helper.save(newUser);
            }

        }
    }




}