package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MachineBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.MachineDAOImpl;
import com.assignment.buildmaster.dto.MachineDto;
import com.assignment.buildmaster.entity.Machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineBOImpl implements MachineBO {
    MachineDAOImpl machineDAO = (MachineDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MACHINE);

    public String getNextMachineId() throws SQLException {
        return machineDAO.getNextId();
    }

    public ArrayList<String> getAllMachineIds() throws SQLException {
        return machineDAO.getAllIds();
    }

    public MachineDto findByMachineId(String selectedMachineId) throws SQLException {
        Machine machine = machineDAO.findById(selectedMachineId);
        return new MachineDto(machine.getMachineId(), machine.getName(), machine.getStatus(), machine.getDescription());
    }

    public boolean saveMachine(MachineDto machineDto) throws SQLException {
        return machineDAO.save(new Machine(machineDto.getMachineId(), machineDto.getName(), machineDto.getStatus(), machineDto.getDescription()));
    }

    public boolean updateMachine(MachineDto machineDto) throws SQLException {
        return machineDAO.update(new Machine(machineDto.getMachineId(), machineDto.getName(), machineDto.getStatus(), machineDto.getDescription()));
    }

    public boolean deleteMachine(String machineID) throws SQLException {
        return machineDAO.delete(machineID);
    }

    public List<MachineDto> getAllMachine() throws SQLException {
        List<Machine> machines = machineDAO.getAll();
        List<MachineDto> machineList = new ArrayList<>();
        for (Machine machine : machines) {
            machineList.add(new MachineDto(machine.getMachineId(), machine.getName(), machine.getStatus(), machine.getDescription()));
        }
        return machineList;
    }
}
