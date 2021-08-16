package com.hp.dao;

import com.hp.bean.Customer;
import com.hp.bean.CustomerAndUser;
import com.hp.bean.User;
import com.hp.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    // 1. 带参数的全查( 2 个表的)  // 作业1:
    public  List<CustomerAndUser> SelectAll() {

        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select t1.id,t1.cust_name,t1.cust_company,t1.cust_birth,t1.cust_sex,t1.cust_phone,t1.cust_position,t1.create_time,t1.modify_time,t2.username from t_customer t1 join t_user t2 on t2.id=t1.user_id";
        //3.使用连接对象 获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CustomerAndUser> lists = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            //4.执行预编译，得到结果集
            rs = ps.executeQuery();
            //5.遍历结果集
            while (rs.next()) {
                Customer cs=new Customer();
                User user=new User();
                CustomerAndUser cu=new CustomerAndUser();

                cs.setId(rs.getInt("id"));
                cs.setCust_name(rs.getString("cust_name"));
                cs.setCust_company(rs.getString("cust_company"));
                cs.setCust_birth(rs.getString("cust_birth"));
                cs.setCust_sex(rs.getInt("cust_sex"));
                cs.setCust_phone(rs.getString("cust_phone"));
                cs.setCust_position(rs.getString("cust_position"));
                cs.setCreate_time(rs.getString("create_time"));
                cs.setModify_time(rs.getString("modify_time"));
                user.setUsername(rs.getString("username"));

                cu.setCustomer(cs);
                cu.setUser(user);
                lists.add(cu);

            }
           // System.out.println("lists = " + lists);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

        // 2. 带参数的查总条数 (2个表的) // 作业2:
     public  int CountAll() {
        //1.创建出 连接对象
            Connection connection = DBHelper.getConnection();
            //2.创建出SQL语句
            String sql = "select count(*) i from t_customer t1 join t_user t2 on t2.id=t1.user_id";

            //3.使用连接对象 获取 预编译对象
            PreparedStatement ps = null;
            ResultSet rs=null;
            int i=0;
            try {
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                if(rs.next()) {
                 i=rs.getInt("i");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }



    public static void main(String[] args) {
        CustomerDao customerDao = new CustomerDao();
//        List list = customerDao.SelectAll();
//        //System.out.println("list = " + list);
//        for (Object o : list) {
//            System.out.println("o = " + o);
//        }
       int a= customerDao.CountAll();
        System.out.println("a = " + a);

    }

}
