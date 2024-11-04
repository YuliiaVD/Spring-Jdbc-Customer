package org.example.app.dao.customer;

import org.example.app.dto.customer.CustomerDtoRequest;
import org.example.app.entity.Customer;
import org.example.app.entity.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("CustomerDaoImpl")
public class CustomerDaoImpl implements CustomerDao  {

    NamedParameterJdbcTemplate template;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(CustomerDtoRequest request) {
        String sql = "INSERT INTO customers (first_name, " +
                "last_name, address, phone) " +
                "VALUES (:firstName, :lastName, :address, :phone)";
        SqlParameterSource paramSource =
                new MapSqlParameterSource()
                        .addValue("firstName", request.firstName())
                        .addValue("lastName", request.lastName())
                        .addValue("address", request.address())
                        .addValue("phone", request.phone());

        return template.update(sql, paramSource) > 0;
    }

    @Override
    public Optional<List<Customer>> fetchAll() {
        String sql = "SELECT * FROM customers";
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql, new CustomerMapper()));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<Customer> fetchById(Long id) {
        String sql = "SELECT * FROM customers " +
                "WHERE id = :id LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public boolean updateById(Long id, CustomerDtoRequest request) {
        String sql = "UPDATE customers " +
                "SET first_name = :firstName, last_name = :lastName, " +
                "address = :address, " +
                "phone = :phone " +
                "WHERE id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("firstName", request.firstName())
                .addValue("lastName", request.lastName())
                .addValue("address", request.address())
                .addValue("phone", request.phone())
                .addValue("id", id);
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM customers WHERE id = :id";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public Optional<Customer> getLastEntity() {
        String sql = "SELECT * FROM customers " +
                "ORDER BY id DESC LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource();
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    // ---- Query Params ----------------------

    public Optional<List<Customer>> fetchByFirstName(String firstName) {
        String sql = "SELECT * FROM customers WHERE first_name = :firstName";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("firstName", firstName);
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql,  paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    public Optional<List<Customer>> fetchByLastName(String lastName) {
        String sql = "SELECT * FROM customers WHERE last_name = :lastName";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("lastName", lastName);
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql,  paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    public Optional<List<Customer>> fetchAllOrderBy(String orderBy) {
        String sql = "SELECT * FROM customers ORDER BY " + orderBy;
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql, new CustomerMapper()));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }
}
