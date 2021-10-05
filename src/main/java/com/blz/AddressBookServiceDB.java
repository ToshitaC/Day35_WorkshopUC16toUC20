package com.blz;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public List<Contacts> viewContactsByName(String fName) throws DBServiceException
    {
        List<Contacts> contactsListByName = new ArrayList<>();
        String query = "select * from address_book where first_name = ?";
        try(Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, fName );
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int id = resultSet.getInt(1);
                String fisrtName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String addressName = resultSet.getString(4);
                String addressType = resultSet.getString(5);
                String address = resultSet.getString(6);
                String city = resultSet.getString(7);
                String state = resultSet.getString(8);
                String zip = resultSet.getString(9);
                String phoneNumber = resultSet.getString(10);
                String email = resultSet.getString(11);
                contactObj = new Contacts(id,fisrtName,lastName,addressName,addressType,address,city,state,zip,phoneNumber,email);
                contactsListByName.add(contactObj);
            }
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        System.out.println(contactsListByName);
        return contactsListByName;
    }
    /**
     *UC17
     */
    public void updateContactDetails(String state,String zip,String fName) throws DBServiceException
    {
        String query = "update address_book set state = ? , zip = ? where first_name = ?";
        try(Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, state);
            preparedStatement.setString(2, zip);
            preparedStatement.setString(3, fName);
            int result = preparedStatement.executeUpdate();
            contactObj = getContactDetails( fName);
            if(result > 0 && contactObj != null)
            {
                contactObj.setStateName(state);
                contactObj.setZipCode(zip);
            }
        }catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
    }

    public Contacts getContactDetails(String fName) throws DBServiceException {
        return viewAddressBook().stream()
                .filter(e -> e.getFirstName()
                        .equals(fName))
                .findFirst()
                .orElse(null);
    }
    /**
     *UC17
     */
    public boolean isAddressBookSyncedWithDB(String fName) throws DBServiceException {
        try {
            return viewContactsByName(fName).get(0).equals(getContactDetails(fName));
        }
        catch (IndexOutOfBoundsException e) {
        }
        catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        return false;
    }
}
