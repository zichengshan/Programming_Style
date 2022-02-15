package com.week6_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reference:
 * https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * https://github.com/xerial/sqlite-jdbc
 */


public class TwentySix {
    public static void main(String[] args) throws SQLException, IOException {
        // Create a database connection

        Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
        // disable auto-commit mode
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        create_db_schema(c);
        // load all the info into the database.db
        load_file_into_database(args[0], c);

        //Add query language
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT value, COUNT(*) as C FROM words GROUP BY value ORDER BY C DESC");
        int k = 0;
        while (rs.next()) {
            if (k++ == 25)
                break;
            // column 1 : value; column 2 : count(*)
            System.out.println(rs.getString(1) + " - " + rs.getInt(2));
        }

        ResultSet rsCountZ = stmt.executeQuery("SELECT value, COUNT(DISTINCT value) as C FROM words WHERE value LIKE '%z%'");
        System.out.println("The number of unique words with 'z': " + rsCountZ.getInt(2));

        //  close the connection
        if(c != null)
            c.close();
    }
    public static void create_db_schema(Connection c) throws SQLException {
        Statement statement = c.createStatement();
        // DROP TABLES IF EXIST
        statement.executeUpdate("DROP TABLE IF EXISTS words");
        statement.executeUpdate("DROP TABLE IF EXISTS characters");
        //ADD TABLES
        statement.executeUpdate("CREATE TABLE words (id INTEGER PRIMARY KEY AUTOINCREMENT, value)");
        statement.executeUpdate("CREATE TABLE characters (id, word_id, value)");
        statement.close();
    }
    private static void load_file_into_database(String path, Connection connection) throws IOException, SQLException {
        List<String> wordsFinal = extractWords(path);

        Statement statement = connection.createStatement();
        int row = 0;
        ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM words");
        while(rs.next()){
            row = rs.getInt(1);
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO words VALUES (?, ?)");
        for (String word: wordsFinal){
            preparedStatement.setInt(1, row);
            preparedStatement.setString(2, word);
            preparedStatement.executeUpdate();
            row++;
        }
        statement.close();
    }

    /**
     * Extract the valid words
     * @param filePath
     * @return
     */
    public static List<String> extractWords(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            // ge the stops words
            List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../../stop_words.txt"))).split(","));

            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = reader.readLine();
            while (strLine != null) {
                String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                for (String s : wordSplit) {
                    String w = s.toLowerCase();
                    if(w.length() >= 2 && !stop_words.contains(w))
                        list.add(w);
                }
                strLine = reader.readLine();
            }
            // Need to close the reader once finished
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return the result
        return list;
    }
}