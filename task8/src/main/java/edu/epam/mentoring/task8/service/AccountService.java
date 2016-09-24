package edu.epam.mentoring.task8.service;

import edu.epam.mentoring.task8.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * Created by eugen on 23.09.2016.
 */
@Service
public class AccountService {
    private static final String SELECT_BY_ID = "SELECT * FROM Accounts WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM Accounts WHERE name=?";
    private static final String INSERT = "INSERT INTO Accounts(name, status, balance) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Accounts SET name=?, status=?, balance=? WHERE id=?";
    private static final String DELETE = "DELETE FROM Accounts WHERE id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Number save(final Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new AccountInsertPreparedStatement(account), keyHolder);
        return keyHolder.getKey();
    }

    public int update(final Account account) {
        long id = account.getId();
        String name = account.getName();
        int status = account.getStatus();
        long balance = account.getBalance();
        return jdbcTemplate.update(UPDATE, name, status, balance, id);
    }

    public boolean deleteById(long id) {
        return jdbcTemplate.update(DELETE, id) == 1;
    }

    public Account getById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new AccountMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Account> getAllAccounts() {
        return jdbcTemplate.query(SELECT_ALL, new AccountMapper());
    }

    public boolean checkExistsName(String name) {
        int count = jdbcTemplate.queryForObject(SELECT_COUNT, Integer.class, name);
        return count > 0;
    }

    private class AccountInsertPreparedStatement implements PreparedStatementCreator {
        private Account account;

        AccountInsertPreparedStatement(Account account) {
            this.account = account;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getName());
            ps.setInt(2, account.getStatus());
            ps.setLong(3, account.getBalance());
            return ps;
        }
    }

    private class AccountMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account account = new Account();
            account.setId(resultSet.getLong("id"));
            account.setName(resultSet.getString("name"));
            account.setStatus(resultSet.getInt("status"));
            account.setBalance(resultSet.getLong("balance"));
            return account;
        }
    }
}
