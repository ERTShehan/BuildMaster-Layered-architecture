package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.ClientDAO;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    public boolean save(Client entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Client values (?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNo(),
                entity.getEmail()
        );
    }

    public boolean update(Client entity) throws SQLException {
        String sql = "update Client set Name=?, Address=?, Phone_No=?, Email=? where Client_ID=?";
        return SQLUtil.execute(sql, entity.getName(), entity.getAddress(), entity.getPhoneNo(), entity.getEmail(), entity.getId());
    }
    
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Client_ID from Client order by Client_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public Client findById(String selectedClId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Client where Client_ID=?", selectedClId);

        if (rst.next()) {
            return new Client(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Client_ID from Client");
        ArrayList<String> clientIds = new ArrayList<>();

        while (rst.next()){
            clientIds.add(rst.getString(1));
        }

        return clientIds;
    }

    public boolean delete(String clientID) throws SQLException {
        String sql = "DELETE FROM Client WHERE Client_ID=?";
        return SQLUtil.execute(sql, clientID);
    }

    public List<Client> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Client");
        List<Client> clientList = new ArrayList<>();

        while (resultSet.next()) {
            clientList.add(new Client(
                    resultSet.getString("Client_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Phone_No"),
                    resultSet.getString("Email")
            ));
        }
        return clientList;
    }

    public int getClientCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) AS client_count FROM Client");
        if (rst.next()) {
            return rst.getInt("client_count");
        }
        return 0;
    }
}
