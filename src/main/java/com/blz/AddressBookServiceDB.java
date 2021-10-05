package com.blz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookServiceDB {
    Contacts contactObj = null;

    public List<Contacts> viewAddressBook() throws DBServiceException {
        List<Contacts> contactsList = new ArrayList<>();
        String query = "select * from address_book";
        try (Connection con = JDBC.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
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
                contactObj = new Contacts(id, firstName, lastName, addressName, addressType, address, city, state, zip, phoneNumber, email);
                contactsList.add(contactObj);
            }
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        System.out.println(contactsList);
        return contactsList;
    }

    public List<Contacts> viewContactsByName(String fName) throws DBServiceException {
        List<Contacts> contactsListByName = new ArrayList<>();
        String query = "select * from address_book where first_name = ?";
        try (Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, fName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
                contactObj = new Contacts(id, fisrtName, lastName, addressName, addressType, address, city, state, zip, phoneNumber, email);
                contactsListByName.add(contactObj);
            }
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        System.out.println(contactsListByName);
        return contactsListByName;
    }

    /**
     * UC17
     */
    public void updateContactDetails(String state, String zip, String fName) throws DBServiceException {
        String query = "update address_book set state = ? , zip = ? where first_name = ?";
        try (Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, state);
            preparedStatement.setString(2, zip);
            preparedStatement.setString(3, fName);
            int result = preparedStatement.executeUpdate();
            contactObj = getContactDetails(fName);
            if (result > 0 && contactObj != null) {
                contactObj.setStateName(state);
                contactObj.setZipCode(zip);
            }
        } catch (Exception e) {
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
     * UC17
     */
    public boolean isAddressBookSyncedWithDB(String fName) throws DBServiceException {
        try {
            return viewContactsByName(fName).get(0).equals(getContactDetails(fName));
        } catch (IndexOutOfBoundsException e) {
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        return false;
    }

    //*UC18*
    public List<Contacts> viewContactsByDateRange(LocalDate startDate, LocalDate endDate) throws DBServiceException {
        List<Contacts> contactsListByStartDate = new ArrayList<>();
        String query = "select * from address_book where date_added between ? and  ?";
        try (Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                contactObj = new Contacts(id, firstName, lastName, addressName, addressType, address, city, state, zip, phoneNumber, email);
                contactsListByStartDate.add(contactObj);
            }
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        return contactsListByStartDate;
    }

    public Map<String, Integer> countContactsByCityOrState(String column) throws DBServiceException {
        Map<String, Integer> contactsCount = new HashMap<>();
        String query = String.format("select %s , count(%s) from address_book group by %s;", column, column, column);
        try (Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contactsCount.put(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        return contactsCount;
    }

    public List<Contacts> insertNewContactToDB(String firstName, String lastName, String address_name, String addressType,
                                               String address, String city, String state, String zip, String phoneNo, String email, String date) throws DBServiceException {
        String sql = String.format("insert into address_book (first_name,last_name,address_name,address_type,address,city,state,zip,phone_number,email,date_added)" +
                " values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');", firstName, lastName, address_name, addressType, address, city, state, zip, phoneNo, email, date);
        try (Connection con = JDBC.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            int result = preparedStatement.executeUpdate();
            if (result == 1)
                contactObj = new Contacts(firstName, lastName, address_name, addressType, address, city, state, zip, phoneNo, email, date);
            viewAddressBook().add(contactObj);
        } catch (Exception e) {
            throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
        }
        return viewAddressBook();
    }

}
