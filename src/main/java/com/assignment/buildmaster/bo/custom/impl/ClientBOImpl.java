package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.ClientBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.ClientDAOImpl;
import com.assignment.buildmaster.dto.ClientDto;
import com.assignment.buildmaster.entity.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientBOImpl implements ClientBO {
    ClientDAOImpl clientDAO = (ClientDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);

    public boolean saveClient(ClientDto clientDto) throws SQLException{
        return clientDAO.save(new Client(clientDto.getId(), clientDto.getName(), clientDto.getAddress(), clientDto.getPhoneNo(), clientDto.getEmail()));
    }

    public boolean updateClient(ClientDto clientDto) throws SQLException{
        return clientDAO.update(new Client(clientDto.getId(), clientDto.getName(), clientDto.getAddress(), clientDto.getPhoneNo(), clientDto.getEmail()));
    }

    public String getNextClientId() throws SQLException{
        return clientDAO.getNextId();
    }

    public ClientDto findByClientId(String selectedId) throws SQLException{
        Client client = clientDAO.findById(selectedId);
        return new ClientDto(client.getId(), client.getName(), client.getAddress(), client.getPhoneNo(), client.getEmail());
    }

    public ArrayList<String> getAllClientIds() throws SQLException {
        return clientDAO.getAllIds();
    }

    public boolean deleteClient(String ID) throws SQLException {
        return clientDAO.delete(ID);
    }

    public List<ClientDto> getAllClient() throws SQLException {
        List<Client> clients = clientDAO.getAll();
        List<ClientDto> clientList = new ArrayList<>();
        for (Client client : clients) {
            clientList.add(new ClientDto(client.getId(), client.getName(), client.getAddress(), client.getPhoneNo(), client.getEmail()));
        }
        return clientList;
    }

//    public int getCount() throws SQLException {
//        return clientDAO.getCount();
//    }
//
//    public String getName(String Id) throws SQLException {
//        return clientDAO.getName(Id);
//    }
//
//    public String getInfo(String Id) throws SQLException {
//        return clientDAO.getInfo(Id);
//    }
//
//    public String getUnit(String Id) throws SQLException {
//        return clientDAO.getUnit(Id);
//    }
//
//    public ArrayList<String> findAllIds() throws SQLException {
//        return clientDAO.findAllIds();
//    }
//
//    public String findNameById(String Id) throws SQLException {
//        return clientDAO.findNameById(Id);
//    }
//
//    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
//        return clientDAO.getAllIdsBy(Id);
//    }
}
