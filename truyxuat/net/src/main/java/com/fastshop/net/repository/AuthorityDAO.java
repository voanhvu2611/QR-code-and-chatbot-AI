package com.fastshop.net.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Role;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
    Optional<Authority> findByAccount(Account account);
    void deleteByAccount(Account account);

    interface EmployeeInfo {
        Account getAccount();
        Role getRole();
    }
    @Query("SELECT au FROM Authority au WHERE ((au.role.id = 'STAFF_M' OR au.role.id = 'STAFF_S' OR au.role.id = 'STAFF') AND (au.account.active != null OR au.account.active = false))")
    List<EmployeeInfo> getEmployeeAndRole();

    @Query("SELECT au.account FROM Authority au WHERE ((au.role.id = 'STAFF_M' OR au.role.id = 'STAFF_S' OR au.role.id = 'STAFF') AND ( au.account.active != null OR au.account.active = false))")
    List<Account> getEmployee();

    @Query("SELECT au.account FROM Authority au WHERE (au.role.id='STAFF') AND (au.account.id=?1)")
    List<Account> findByKeyword(String keyword);

    @Query("SELECT COUNT(o) FROM Authority o WHERE o.role.id ='USER'")
    Integer countByRoleEqualsUser();
}
