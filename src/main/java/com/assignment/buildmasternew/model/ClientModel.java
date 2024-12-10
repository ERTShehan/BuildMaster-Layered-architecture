package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.ClientDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
    public boolean saveClient(ClientDto clientDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Client values (?,?,?,?,?)",
                clientDto.getId(),
                clientDto.getName(),
                clientDto.getAddress(),
                clientDto.getPhoneNo(),
                clientDto.getEmail()
        );
    }

//    public boolean updateClient(ClientDto clientDto) throws SQLException {
//        return CrudUtil.execute(
//                "update Client set Name=?, Address=?, Phone_No=?, Email=? where Client_ID=?",
//                clientDto.getId(),
//                clientDto.getName(),
//                clientDto.getAddress(),
//                clientDto.getPhoneNo(),
//                clientDto.getEmail()
//        );
//    }

    public boolean updateClient(ClientDto clientDto) throws SQLException {
        String sql = "update Client set Name=?, Address=?, Phone_No=?, Email=? where Client_ID=?";
        return CrudUtil.execute(sql, clientDto.getName(), clientDto.getAddress(), clientDto.getPhoneNo(), clientDto.getEmail(), clientDto.getId());
    }
    
    public String getNextClientId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Client_ID from Client order by Client_ID desc limit 1");

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
        ResultSet rst = CrudUtil.execute("select * from Client where Client_ID=?", selectedClId);

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

    public ArrayList<String> getAllClientIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Client_ID from Client");
        ArrayList<String> clientIds = new ArrayList<>();

        while (rst.next()){
            clientIds.add(rst.getString(1));
        }

        return clientIds;
    }

    public boolean deleteClient(String clientID) throws SQLException {
        String sql = "DELETE FROM Client WHERE Client_ID=?";
        return CrudUtil.execute(sql, clientID);
    }

    public List<ClientDto> getAllClients() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Client");
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

    public int getClientCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) AS client_count FROM Client");
        if (rst.next()) {
            return rst.getInt("client_count");
        }
        return 0;
    }
}
