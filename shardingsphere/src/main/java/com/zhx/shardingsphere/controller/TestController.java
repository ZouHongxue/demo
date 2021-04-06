package com.zhx.shardingsphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author hongxue.zou @Date 2021-04-06 @Time 14:26
 */
@Controller
public class TestController {
  @Resource
  DataSource dataSource;

  @GetMapping("/get")
  public String test() throws SQLException {
    Connection connection = dataSource.getConnection();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("select * from t_order order by create_time limit 2, 1");
    System.out.println("create_time");
    while (rs.next()) {
      System.out.println(rs.getString(2));
    }
    return "success";
  }
}
