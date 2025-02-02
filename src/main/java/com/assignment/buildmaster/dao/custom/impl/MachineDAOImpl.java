package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MachineDAO;
import com.assignment.buildmaster.dto.MachineDto;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.Machine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAOImpl implements MachineDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Machine_ID from Machine order by Machine_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("M%03d", newIdIndex);
        }
        return "M001";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Machine_ID from Machine");
        ArrayList<String> machineIds = new ArrayList<>();

        while (rst.next()){
            machineIds.add(rst.getString(1));
        }

        return machineIds;
    }

    public Machine findById(String selectedMachineId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Machine where Machine_ID=?", selectedMachineId);

        if (rst.next()) {
            return new Machine(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public boolean save(Machine entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Machine values (?,?,?,?)",
                entity.getMachineId(),
                entity.getName(),
                entity.getStatus(),
                entity.getDescription()
        );
    }

    public boolean update(Machine entity) throws SQLException {
        String sql = "update Machine set Name=?, Status=?, Description=? where Machine_ID=?";
        return SQLUtil.execute(sql, entity.getName(), entity.getStatus(), entity.getDescription(), entity.getMachineId());
    }

    public boolean delete(String machineID) throws SQLException {
        String sql = "DELETE FROM Machine WHERE Machine_ID=?";
        return SQLUtil.execute(sql, machineID);
    }

    public List<Machine> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Machine");
        List<Machine> machineList = new ArrayList<>();

        while (resultSet.next()) {
            machineList.add(new Machine(
                    resultSet.getString("Machine_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Status"),
                    resultSet.getString("Description")
            ));
        }
        return machineList;
    }

}
