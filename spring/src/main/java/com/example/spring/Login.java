package com.example.spring;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:3000")
@SpringBootApplication
@RestController
public class Login {
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginCredentials) throws JsonProcessingException {
        System.out.println((loginCredentials));
		/*for (String name : loginCredentials.keySet())
			System.out.println("key: " + name);

		for (String url : loginCredentials.values())
			System.out.println("value: " + url);*/

        String username="";
        String password="";
        Iterator<Map.Entry<String, String>> itr = loginCredentials.entrySet().iterator();

        while(itr.hasNext())
        {
            Map.Entry<String, String> entry = itr.next();
            if(entry.getKey()== "username"){
                username=entry.getValue();
				/*int ret=checkLoginCred(entry.getValue());
				return String.valueOf(ret);*/
            }
            else if(entry.getKey()== "password"){
                password=entry.getValue();
            }
            System.out.println(entry.getKey());

        }

        int mod = 1000000007;
        int base = 11;
        int cur = 1, hash = 0;
        for (int i = 0; i < password.length(); i++) {
            hash = (hash + cur * password.charAt(i));
            cur = (cur * base) % mod;
        }
        password= String.valueOf(hash);
        //System.out.println(password);
        int ret=checkLoginCred(username,password);
        return String.valueOf(ret);

    }

    public int checkLoginCred(String username,String password)
    {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/typerush", "root", "Rubaiyat26");

            /**
             * Here sonoo is database name, root is username and password
             * Statement stmt = con.createStatement();
             * ResultSet rs = stmt.executeQuery("select * from users where name = ?");
             **/
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password=?");
            //System.out.println(username);
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            resultSet = psCheckUserExists.executeQuery();
            /**/
            if (resultSet.isBeforeFirst()) {
                System.out.println("found");

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3));
                }
                connection.close();
                return 1;
            }
            else {
                System.out.println("no user");
                return 0;

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
