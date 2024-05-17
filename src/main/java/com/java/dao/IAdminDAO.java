package com.java.dao;

import com.java.entity.Admin;

public interface IAdminDAO {
    Admin getAdminById(int adminId);
    Admin getAdminByUsername(String username);
    void registerAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(int adminId);
}
