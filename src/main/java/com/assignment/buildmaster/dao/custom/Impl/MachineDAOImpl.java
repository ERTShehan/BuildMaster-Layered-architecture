package com.assignment.buildmaster.dao.custom.Impl;

import com.assignment.buildmaster.dto.MachineDto;
import com.assignment.buildmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAOImpl {
    public String getNextMachineId() throws SQLException {
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

    public ArrayList<String> getAllMachineIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Machine_ID from Machine");
        ArrayList<String> machineIds = new ArrayList<>();

        while (rst.next()){
            machineIds.add(rst.getString(1));
        }

        return machineIds;
    }

    public MachineDto findById(String selectedMachineId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Machine where Machine_ID=?", selectedMachineId);

        if (rst.next()) {
            return new MachineDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public boolean saveMachine(MachineDto machineDto) throws SQLException {
        return SQLUtil.execute(
                "insert into Machine values (?,?,?,?)",
                machineDto.getMachineId(),
                machineDto.getName(),
                machineDto.getStatus(),
                machineDto.getDescription()
        );
    }

    public boolean updateMachine(MachineDto machineDto) throws SQLException {
        String sql = "update Machine set Name=?, Status=?, Description=? where Machine_ID=?";
        return SQLUtil.execute(sql, machineDto.getName(), machineDto.getStatus(), machineDto.getDescription(), machineDto.getMachineId());
    }

    public boolean deleteMachine(String machineID) throws SQLException {
        String sql = "DELETE FROM Machine WHERE Machine_ID=?";
        return SQLUtil.execute(sql, machineID);
    }

    public List<MachineDto> getAllMachines() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Machine");
        List<MachineDto> machineList = new ArrayList<>();

        while (resultSet.next()) {
            machineList.add(new MachineDto(
                    resultSet.getString("Machine_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Status"),
                    resultSet.getString("Description")
            ));
        }
        return machineList;
    }
}
