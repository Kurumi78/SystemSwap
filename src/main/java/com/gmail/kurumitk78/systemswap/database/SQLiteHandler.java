package com.gmail.kurumitk78.systemswap.database;

import com.gmail.kurumitk78.systemswap.SystemSwap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.gmail.kurumitk78.systemswap.SystemSwap.SystemSwapInstance;

public class SQLiteHandler {
    private static Connection dbConnection = null;
    public static void connect(){
        try{
            String url = "jdbc:sqlite:" + SystemSwapInstance.getDataFolder()+"/systemSwap.db";
            dbConnection = DriverManager.getConnection(url);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(dbConnection != null){
                    dbConnection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void firstRun(){
        String systemTable = "CREATE TABLE IF NOT EXISTS systems (\n"
                + " systemUUID TEXT PRIMARY KEY,\n"
                + " PlayerUUID TEXT NOT NULL,\n"
                + " fronter TEXT\n"
                + ");";

        String alterTable = "CREATE TABLE IF NOT EXISTS alters (\n"
                + " alterUUID TEXT PRIMARY KEY,\n"
                + " name TEXT NOT NULL,\n"
                + " nickname TEXT,\n"
                + " proxytag TEXT,\n"
                + " systemUUID TEXT NOT NULL\n"
                + ");";
            try{
                Statement stmt = dbConnection.createStatement();
                stmt.execute(systemTable);
                stmt.execute(alterTable);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }
    public static void dbCall (String sqlStatement){
        try{
            Statement stmt = dbConnection.createStatement();
            stmt.execute(sqlStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
