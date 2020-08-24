/*programAssigmetnUnit6.Java
 *Las edited 4/2/1010 by Jana Backman
 *This code connects to a remote MYSQL database on the website CWHerbert.com
 * It connects via IP address(IPV4) to the database  CWHDemo and the table fall2014
 *
 * host IP address: 68.178.217.12
 * database: CWHDemo
 * username: CWHDemo
 * password: Cwhdemo%123
 *
 *
 * Table metadata
 * crn        char(20)
 * subject    varchar(5)
 * course     varchar(5)
 * section    varchar(5)
 * credit     integer
 * time       varchar(20)
 * days       varchar(8)
 * term       varchar(5)
 * campus     varchar(5)
 * room       varchar(8)
 * enrollment integer
 */
package com.company;
import java.sql.*;
import java.io.*;

import static java.sql.DriverManager.*;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("Connecting to the database...\n");
        //connect to a database by establishing a Connection object
        Connection conn = getConnection("jdbc:mysql://68.178.217.12/CWHDemo","CWHDemo", "Cwhdemo%123");
        System.out.println("Databese connection established.\n");

        //Create a Statement object for this database connection
        Statement st = conn.createStatement();

        //call a method that performs a query using Statement st and prints the data into a CVS file
        selectAllToFile(st);

        //call a method that performs a query using Statemet st
        selectSome(st);

        //close connection
        conn.close();

    }//end main
    /*The following method performs a SQL query
     * The parameter must be a Statement object with and established connection to an SQL database
     * The method would print the data from the query into a CSV file
     */
    public static void selectAllToFile(Statement s) throws SQLException, ClassNotFoundException, IOException{
        //create file class object and give the file the name SQLresultJana.CSV
        java.io.File sqlResult = new java.io.File("SQLresultJana.CSV");
        java.io.PrintWriter outfile = new java.io.PrintWriter(sqlResult);

        String queryString; // a string to hols an SQL query
        ResultSet rs; //the result set from an sql query as a table

        //this sample query returns crn, subject, course, section, days and time for all CSCI courses, in order
        // according to the course number to a CSV file
        queryString= "SELECT crn, subject, course, section, days, time FROM fall2014 ORDER BY course;";

        //send a statement executing the query and saving the result set
        rs=s.executeQuery(queryString);

        System.out.println("Copying query "+queryString+" results to SQLresultJana.CSV");

        //print heading for output
        outfile.print("CRN,"+ "SUBJECT," +"COURSE," +"SECTION," +"DAYS,"+"TIME\n");

        //Iterate through the result send results to file
        while (rs.next()){
            outfile.print(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)
                    +","+rs.getString(5)+","+rs.getString(6)+"\n");
        }
        outfile.close();

    }//end selectAllToFile
    /*The following method performs an SQL query the paramenter must be a Statement object with an
     *established connection to an SQL database
     */
    public static void selectSome(Statement s) throws SQLException, ClassNotFoundException{
        String queryString;     // a String to hold an SQL query
        ResultSet rs;           // the result set from an SQL query as a table

        // This query returns the subject, the course number and section from the table fall2014
        //where the class is is a 4 credit class but not online class.
        queryString = "SELECT subject, course, section FROM fall2014 WHERE credits= 4 AND NOT days = 'DIS';";

        // Send a statement executing the query and saving the result set
        rs = s.executeQuery(queryString);

        // print headings for the output
        System.out.println(queryString);
        System.out.printf("%-12s%-12s%-12s%n", "Subject", "Course", "Section");
        System.out.println("*********************************");

        // Iterate through the result set and print name, owner, and species attributes
        while (rs.next())
            System.out.printf("%-12s%-12s%-12s%n", rs.getString("subject"), rs.getString("course"),
                    rs.getString("section"));

        System.out.println("*********************************");
    }// end select all
}
