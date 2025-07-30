package com.example.auxi.Saved;

import com.example.auxi.db.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class increase {
    public static void increasepoints(int points) throws SQLException {
        Connection con1= dbConnection.getCOnnection();
        points=points+currUser.getPoints();
        currUser.setPoints(points);
        String sql="update login set credit=? where email=?";
        PreparedStatement statement=con1.prepareStatement(sql);
        statement.setInt(1,points);
        statement.setString(2,currUser.getEmail());
        statement.executeUpdate();
    }
}
