package DAO;

import DTO.Cat;
import DTO.Owner;

import java.sql.*;
import java.util.ArrayList;

public class OwnerDao {
    public Owner getOwner(Integer id){
        Connection connection = DbConnect.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM owner WHERE id=" + id);

            if (resultSet.next()){
                extractOwnerFromResultSet(resultSet);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }

    public ArrayList<Owner> getAllOwners(){
        Connection connection = DbConnect.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM owner");

            ArrayList<Owner> owners = new ArrayList<Owner>();

            while (resultSet.next()){
                Owner owner = extractOwnerFromResultSet(resultSet);
                owners.add(owner);
            }
            return owners;
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }

    public Boolean insertOwner(Owner owner){
        Connection connection = DbConnect.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO owner VALUE (NULL, ?, ?, ?)");
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setDate(2, owner.getDateOfBirth());
            preparedStatement.setArray(3, (Array) owner.getCats());
            int i = preparedStatement.executeUpdate();

            if (i == 1){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean updateOwner(Owner owner){
        Connection connection = DbConnect.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE owner SET name=?, dateOfBirth=?, cats=? WHERE id=?");
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setDate(2, owner.getDateOfBirth());
            preparedStatement.setArray(3, (Array) owner.getCats());
            preparedStatement.setInt(4, owner.getId());
            int i = preparedStatement.executeUpdate();

            if (i == 1){
                return true;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean deleteOwner(Integer id) {
        Connection connection = DbConnect.getConnection();
        try {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate("DELETE FROM owner WHERE id=" + id);

            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private Owner extractOwnerFromResultSet(ResultSet resultSet) throws SQLException{
        Owner owner = new Owner();
        owner.setId(resultSet.getInt("id"));
        owner.setName(resultSet.getString("name"));
        owner.setDateOfBirth(resultSet.getDate("dateOfBirth"));
        owner.setCats((ArrayList<Cat>) resultSet.getArray("cats"));

        return owner;
    }
}
