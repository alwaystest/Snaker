import java.sql.*;

public class sqlite
{
	Connection c = null;
	public sqlite()
	{
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:record.sqlite");
      Statement stmt=c.createStatement();
      //ResultSet rs=stmt.executeQuery("select name,grades from record");
      ResultSet rs=stmt.executeQuery("SELECT COUNT(*) as TabCount FROM sqlite_master where type='table' and name='record' ");
      if(rs.getInt("TabCount")!=1){
    	  stmt.executeUpdate("create table record(name char(10),grades int,time text)");
      }
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");
	}
  
	public void in(int grades){
		try {
			Statement stmt=c.createStatement();
			System.out.println(grades);
			stmt.executeUpdate("insert into record values('admin','"+grades+"',datetime('now'))");
			stmt.executeUpdate("delete from record "
					+ "where(select count(*)from record)>2 and "
					+ "time in (select time from record order by grades desc limit (select count(grades) from record) offset 2)");
			/*
			ResultSet rs=stmt.executeQuery("select count(*) as num from record");
			for(int i=rs.getInt("num");i>10;i--){
				stmt.executeUpdate("delete  from record where grades in (select min(grades) from record)");
			}*/
			stmt.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("inserted!");
	}
	

	public void out(){
		try {
			Statement stmt=c.createStatement();
			ResultSet rs=stmt.executeQuery("select name,grades from record order by grades desc");
			while (rs.next()){
				System.out.println(rs.getString("name")+"	"+rs.getInt("grades"));
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
