CREATE DATABASE Buildmaster;
USE Buildmaster;

User Table >
CREATE TABLE User (
                      email VARCHAR(50),
                      password VARCHAR(30)
);

insert into User values("dertsshehan@gmail.com","1234");

-- Client Table >
CREATE TABLE Client (
                        Client_ID VARCHAR(10) PRIMARY KEY,
                        Name VARCHAR(100),
                        Address VARCHAR(150),
                        Phone_No VARCHAR(15),
                        Email VARCHAR(100)
);

-- Project Table >
CREATE TABLE Project (
                         Project_ID VARCHAR(10) PRIMARY KEY,
                         Name VARCHAR(100),
                         Start_date DATE,
                         End_date DATE,
                         Type VARCHAR(50),
                         Status VARCHAR(50),
                         Client_ID VARCHAR(10),
                         FOREIGN KEY (Client_ID) REFERENCES Client(Client_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Employee Table >
CREATE TABLE Employee (
                          Employee_ID VARCHAR(10) PRIMARY KEY,
                          Name VARCHAR(100),
                          Phone_No VARCHAR(15),
                          Address VARCHAR(255),
                          Role VARCHAR(50)
);

-- EmployeeDetails Table (for Project-Employee relationship) >
CREATE TABLE EmployeeDetails (
                                 Project_ID VARCHAR(10),
                                 Work_date DATE,
                                 Work_Day_Count INT,
                                 Employee_ID VARCHAR(10),
                                 FOREIGN KEY (Project_ID) REFERENCES Project(Project_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                 PRIMARY KEY (Project_ID, Employee_ID)
);

-- Payment Table >
CREATE TABLE Payment (
                         Payment_ID VARCHAR(10) PRIMARY KEY,
                         Project_ID VARCHAR(10),
                         Date DATE,
                         Type VARCHAR(50),
                         Amount DECIMAL(10, 2),
                         FOREIGN KEY (Project_ID) REFERENCES Project(Project_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Expenses Table >
CREATE TABLE Expenses (
                          Expense_ID VARCHAR(10) PRIMARY KEY,
                          Type VARCHAR(50),
                          Amount DECIMAL(10, 2),
                          Date DATE,
                          Project_ID VARCHAR(10),
                          Employee_ID VARCHAR(10),
                          FOREIGN KEY (Project_ID) REFERENCES Project(Project_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                          FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Material Table >
CREATE TABLE Material (
                          Material_ID VARCHAR(10) PRIMARY KEY,
                          Name VARCHAR(100),
                          Quantity_in_Stock VARCHAR(20),
                          Unit VARCHAR(20)
);

-- MaterialUsage Table (for Project-Material relationship) >
CREATE TABLE MaterialUsage (
                               Usage_ID VARCHAR(10) PRIMARY KEY,
                               Project_ID VARCHAR(10),
                               Material_ID VARCHAR(10),
                               Quantity_used VARCHAR(20),
                               Date DATE,
                               FOREIGN KEY (Project_ID) REFERENCES Project(Project_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                               FOREIGN KEY (Material_ID) REFERENCES Material(Material_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Machine Table >
CREATE TABLE Machine (
                         Machine_ID VARCHAR(10) PRIMARY KEY,
                         Name VARCHAR(100),
                         Status VARCHAR(50),
                         Description VARCHAR(150)
);

-- MachineMaintenance Table (for Machine Maintenance Record)
CREATE TABLE MachineMaintenance (
                                    MMR_ID INT PRIMARY KEY,
                                    Machine_ID INT,
                                    Date DATE,
                                    Cost DECIMAL(10, 2),
                                    Description VARCHAR(255),
                                    FOREIGN KEY (Machine_ID) REFERENCES Machine(Machine_ID)
);

-- MachineDetails Table (for Project-Machine relationship) >
CREATE TABLE MachineDetails (
                                Project_ID VARCHAR(10),
                                Machine_ID VARCHAR(10),
                                FOREIGN KEY (Project_ID) REFERENCES Project(Project_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                FOREIGN KEY (Machine_ID) REFERENCES Machine(Machine_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                PRIMARY KEY (Project_ID, Machine_ID)
);

-- Supplier Table >
CREATE TABLE Supplier (
                          Supplier_ID VARCHAR(10) PRIMARY KEY,
                          Name VARCHAR(100),
                          Address VARCHAR(250),
                          Phone_No VARCHAR(15),
                          Email VARCHAR(100)
);

-- MaterialBuy Table >
CREATE TABLE MaterialBuy (
                             Payment_ID VARCHAR(10) PRIMARY KEY,
                             Material_ID VARCHAR(10),
                             Supplier_ID VARCHAR(10),
                             Date DATE,
                             Unit_Amount DECIMAL(10, 2),
                             Quantity VARCHAR(20),
                             Total_Price DECIMAL(20, 2),
                             FOREIGN KEY (Material_ID) REFERENCES Material(Material_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                             FOREIGN KEY (Supplier_ID) REFERENCES Supplier(Supplier_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Report Table
CREATE TABLE Report (
                        Report_ID INT PRIMARY KEY,
                        Material_ID INT,
                        Daily DATE,
                        FOREIGN KEY (Material_ID) REFERENCES Material(Material_ID)
);
