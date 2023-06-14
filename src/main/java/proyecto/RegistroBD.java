package proyecto;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistroBD {



    public static ResultSet query(String query, Map<Integer, String> parameters) throws SQLException{

        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);


//        System.out.println(parameters.keySet());
        for(Integer key : parameters.keySet()){

//            System.out.println(key);

//            System.out.println(parameters.get(key));

            statement.setString(key, parameters.get(key));
        }

        ResultSet queryResult = statement.executeQuery();
        connection.close();
        statement.close();

        return queryResult;
    }

}


