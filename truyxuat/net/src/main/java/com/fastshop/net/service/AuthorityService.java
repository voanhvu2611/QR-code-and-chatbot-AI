package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Role;
import com.fastshop.net.repository.AuthorityDAO;

public interface AuthorityService {
    void save(Authority authority);
    void deleteById(Integer id);
    void deleteByAuthority(Authority authority);
    Role getRoleByAccount(Account account);
    Authority findByAccount(Account account);
    Integer countByRoleEqualsUser();
    List<Authority> findAll();
    List<Account> getListStaff();
    List<AuthorityDAO.EmployeeInfo> getListEmployeeAndRole();
    List<Account> findByKeyword(String keyword);
}
