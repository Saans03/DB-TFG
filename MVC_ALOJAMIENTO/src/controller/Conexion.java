package controller;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Conexion {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private static String url = "jdbc:mysql://localhost:3306/alojamiento";
	private static String user = "root";
	private static String password = "root";
 
	public Connection conectar(){
    	Connection con =  null;
        try {
        	con= DriverManager.getConnection(url, user, password);
        	stmt = con.createStatement();
            if (con.isValid(3)) {
                System.out.println("Conexión a MySQL exitosa.");
            } else {
                System.out.println("Fallo en la conexión.");
            }
 
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
        }
        return con;
    }
    public void desconectar() {
		try {
			if (stmt !=null) {stmt.close();}
			if (rs !=null)   {rs.close();}
			if (con !=null)  {con.close();}
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
    
}