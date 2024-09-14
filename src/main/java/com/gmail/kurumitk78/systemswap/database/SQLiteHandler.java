package com.gmail.kurumitk78.systemswap.database;

import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

import static com.gmail.kurumitk78.systemswap.SystemSwap.SystemSwapInstance;

public class SQLiteHandler {
    private static final String dbURL = "jdbc:sqlite:" + SystemSwapInstance.getDataFolder()+"/SystemSwap.db";


    public static void firstRun(){
        String systemTable = "CREATE TABLE IF NOT EXISTS systems (\n"
                + " systemUUID TEXT PRIMARY KEY,\n"
                + " playerUUID TEXT NOT NULL,\n"
                + " fronterUUID TEXT\n"
                + ");";

        String alterTable = "CREATE TABLE IF NOT EXISTS alters (\n"
                + " alterUUID TEXT PRIMARY KEY,\n"
                + " name TEXT NOT NULL,\n"
                + " nickname TEXT,\n"
                + " proxytag TEXT,\n"
                + " description TEXT,\n"
                + " systemUUID TEXT NOT NULL\n"
                + ");";
            try{
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();

               stmt.execute(systemTable);
               stmt.execute(alterTable);
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("SYSTEMSWAP: error 3 " + e);
            }

    }
    //  SQLiteHandler.dbCall("statement");
    public static void dbCall (String sqlStatement){
        new BukkitRunnable() {
            @Override
            public void run() {
                try{
                    Class.forName("org.sqlite.JDBC");
                    Connection conn = DriverManager.getConnection(dbURL);
                    Statement stmt = conn.createStatement();
                    stmt.execute(sqlStatement);
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("SYSTEMSWAP: error 4 " + e);
                }
            }

        }.runTaskAsynchronously(SystemSwapInstance);

    }
    public static ResultSet dbCallReturn(String sqlStatement){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(dbURL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sqlStatement);



        } catch (Exception e) {
            System.out.println("SYSTEMSWAP: error 5 " + e);
            return null;
        }

    }
}
