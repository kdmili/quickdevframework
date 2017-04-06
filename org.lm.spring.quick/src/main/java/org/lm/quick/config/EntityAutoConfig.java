package org.lm.quick.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class EntityAutoConfig implements ApplicationListener<ContextRefreshedEvent> {
	public static void main(String[] args) {
		Resource sqlres = new ClassPathResource("/japtest.sql",EntityAutoConfig.class);
		System.out.println(sqlres.isReadable());
		System.out.println(sqlres.exists());
		URL rs = EntityAutoConfig.class.getResource("/japtest.sql");
		try {
			System.out.println(rs.getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  void init_data(DataSource ds) {
		Connection con = null;
		try {
			con = ds.getConnection();
			Statement stat = con.createStatement();
			Resource sqlres = new ClassPathResource("/init.sql",EntityAutoConfig.class);
			BufferedReader br = new BufferedReader(new InputStreamReader(sqlres.getInputStream()));
			String line = br.readLine();
			while (line != null) {
				stat.execute(line);
				line = br.readLine();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		DataSource ds = event.getApplicationContext().getBean(DataSource.class);
		init_data(ds);
		
	}

 
}
