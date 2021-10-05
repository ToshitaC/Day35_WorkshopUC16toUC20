package com.blz;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookServiceDB {
    Contacts contactObj = null;
    public List<Contacts> viewAddressBook() throws DBServiceException
    {
        List<Contacts> contactsList = new ArrayList<>();
        String query = "select * from address_book";
        try(Connection con = JDBC.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String addressName = resultSet.getString(4);
                String addressType = resultSet.getString(5);
                String address = resultSet.getString(6);
                String city = resultSet.getString(7);
                String state = resultSet.getString(8);
                String zip = resultSet.getString(9);
                String phoneNumber = resultSet.getString(10);
                String email = resultSet.getString(11);
                contactObj = new Contacts(id,firstName,lastName,addressName,addressType,address,city,state,zip,phoneNumber,email);
                contactsList.add(contactObj);
            }
        }
        catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        System.out.println(contactsList);
        return contactsList;
    }
}
