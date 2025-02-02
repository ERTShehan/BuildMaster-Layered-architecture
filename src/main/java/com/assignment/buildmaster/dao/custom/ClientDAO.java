package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.Client;

import java.sql.SQLException;


public interface ClientDAO extends CrudDAO<Client> {
    int getClientCount() throws SQLException;
}