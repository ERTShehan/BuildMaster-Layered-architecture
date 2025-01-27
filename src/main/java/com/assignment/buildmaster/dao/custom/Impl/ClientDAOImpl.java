package com.assignment.buildmaster.dao.custom.Impl;

import com.assignment.buildmaster.dao.custom.ClientDAO;
import com.assignment.buildmaster.dto.ClientDto;
import com.assignment.buildmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    public boolean save(ClientDto clientDto) throws SQLException {
        return SQLUtil.execute(
                "insert into Client values (?,?,?,?,?)",
                clientDto.getId(),
                clientDto.getName(),
                clientDto.getAddress(),
                clientDto.getPhoneNo(),
                clientDto.getEmail()
        );
    }

    public boolean update(ClientDto clientDto) throws SQLException {
        String sql = "update Client set Name=?, Address=?, Phone_No=?, Email=? where Client_ID=?";
        return SQLUtil.execute(sql, clientDto.getName(), clientDto.getAddress(), clientDto.getPhoneNo(), clientDto.getEmail(), clientDto.getId());
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

    public ClientDto findById(String selectedClId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Client where Client_ID=?", selectedClId);

        if (rst.next()) {
            return new ClientDto(
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

    public List<ClientDto> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Client");
        List<ClientDto> clientList = new ArrayList<>();

        while (resultSet.next()) {
            clientList.add(new ClientDto(
                    resultSet.getString("Client_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Phone_No"),
                    resultSet.getString("Email")
            ));
        }
        return clientList;
    }

    public int getCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) AS client_count FROM Client");
        if (rst.next()) {
            return rst.getInt("client_count");
        }
        return 0;
    }

    @Override
    public String getName(String employeeId) throws SQLException {
        return "";
    }

    @Override
    public String getRole(String employeeId) throws SQLException {
        return "";
    }
}
