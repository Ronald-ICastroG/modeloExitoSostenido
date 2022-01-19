/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.*;
public class Conexion {
    Connection conn;
    
    
    //Constructor
    
    public Connection conexion(){
        
        String url="src\\main\\java\\database\\ExitoSostenido.db";        
        try {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:"+url);
            System.out.println("Conexion Establecida !!");
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }
    
    
    
//    void actualizar(String tabla,String campo, float valor ){
//{
//    /*
//           
//}
//	sqlqry =  'update ' + tabla  + ' set madurez = ' + valor + ' where subcriterio ' + campo  	
//	execute (sqlqry);
//	
//}
//
//
//
//void insertarPlandeMejoramiento(subcriterio, accion, plazo, responsable, presupuesto){
//
//
//	sqlqry =  'insert into mejoramiento (subcriterio, accion, plazo, responsable, presupuesto) values (' + subcriterio + ',' + accion + ',' + plazo + ',' + responsable + ',' +  presupuesto + ')'
//	execute (sqlqry);
//	
//   */ 
//    
    
}
    
