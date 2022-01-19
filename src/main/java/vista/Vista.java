/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author R_CASTRO
 */
public class Vista extends javax.swing.JFrame {

    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtNa.setEnabled(false);
        limpiarTMejora();
        mostrarTablaMejora("");
        muestraResultados("");
        CuadroResultados();
//        Ventana1();
        //Ventana1.setVisible(true);
        
        
//        PuntajeTotal();
//        PromedioMadurez();
//        Prioridad1();
//        CriterioAlto();
//        PuntajeBase();
       
   }

    
    void limpiarTMejora(){
        txtNa.setText("");
        txtsub.setText("");
        txtaccion.setText("");
        txtresp.setText("");
        txtplazo.setText("");
        txtpresupuesto.setText("");
    }
   //limpiar campos criterio1 
    void limpiarC1(){
        txt1a.setText("");
        txt1b.setText("");
        txt1c.setText("");
        txt1d.setText("");
   
    }
    
    
    void limpiarC2(){
        txt2a.setText("");
        txt2b.setText("");
        txt2c.setText("");
        
    }
    void limpiarC3(){
        txt3a.setText("");
        txt3b.setText("");
        txt3c.setText("");
    }
    void limpiarC4(){
        txt4a.setText("");
        txt4b.setText("");
        txt4c.setText("");
    }
    void limpiarC5(){
        txt5a.setText("");
        txt5b.setText("");
        txt5c.setText("");
        txt5d.setText("");
        txt5e.setText("");
    }
    
    void limpiarC6(){
        txt6a.setText("");
        txt6b.setText("");  
    }
    void limpiarC7(){
        txt7a.setText("");
        txt7b.setText("");  
    }
    void limpiarC8(){
        txt8a.setText("");
        txt8b.setText("");  
    }
    void limpiarC9(){
        txt9a.setText("");
        txt9c.setText("");  
    }
    
    void mostrarTablaMejora(String valor){
        DefaultTableModel modelo=new DefaultTableModel();
        
         modelo.addColumn("#");
        modelo.addColumn("Subcriterio");
        modelo.addColumn("Acción");
        modelo.addColumn("Responsable");
        modelo.addColumn("Plazo");
        modelo.addColumn("Presupuesto");
        
        //seleccione a cuál tabla le va a mandar las columnas
        tablaMejora.setModel(modelo);
        
        //Sentencia sql para mostrar la tabla 
        String sql="SELECT * FROM PlanMejora WHERE subcriterio LIKE '%"+valor+"%'";
        
        //arreglo tipo String (los 5 datos id,)
        String datos[]=new String[6];
        
        //Crear e importar statement;
        Statement st;
        
        try {
            st=conn.createStatement();
            
            //el resultSet es el que ejecuta la sentencia st.executeQuery(sql)
            ResultSet rs=st.executeQuery(sql);
            //fila por fila retorne los valores en el arreglo
            
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                
                modelo.addRow(datos);
            }
            tablaMejora.setModel(modelo);
        } catch (SQLException e) {
            System.err.println("Error al llenar la tabla..."+e);
            JOptionPane.showMessageDialog(null,"Error al llenar la tabla");
        }
        
        
        
    }
    //reporte resultados
    void muestraResultados(String valor2){
        DefaultTableModel modelo2=new DefaultTableModel();
     
        modelo2.addColumn("Criterio");
        modelo2.addColumn("nombre");
        
        modelo2.addColumn("puntaje Base");
        modelo2.addColumn("Puntaje obtenido");
        modelo2.addColumn("Nivel de madurez");
        
        
        //seleccione a cuál tabla se le van a asignar los valores
        
        tablaResultados.setModel(modelo2);
       
        //SENTENCIA PARA CONSULTAR LOS DATOS DE LA TABLA
        String SqlRes="SELECT * FROM ResultadosXVigencia WHERE criterio LIKE '%"+valor2+"%'";
        
        
        
        String[] valores=new String[6];
        
        
        Statement st;
        
        
        
        try {
            st=conn.createStatement();
            ResultSet rs=st.executeQuery(SqlRes);
            
            //fila por fila retorne los valores en el arreglo
            
            while(rs.next()){
                valores[0]=rs.getString(1);
                valores[1]=rs.getString(2);
                valores[2]=rs.getString(4);
                valores[3]=rs.getString(5);
                valores[4]=rs.getString(6);
               
                modelo2.addRow(valores);
                
                tablaResultados.setModel(modelo2);
                
            }
            
        } catch (SQLException e) {
            System.err.println("Error al llenar la tabla de datos... "+e);
            JOptionPane.showMessageDialog(null, "Error al llenar la tabla");
        }
        
    }
    
    
//Insertar puntajes Suma de los resultados
    
    void ResultadoC1(){
        String s1="Select SUM(conversion)from Criterio1";
        
        String r1=null;
        try {
            PreparedStatement psr1=conn.prepareStatement(s1);
            ResultSet Rs1=psr1.executeQuery();
            
            
            while(Rs1.next()){
                r1=Rs1.getString(1);
                System.out.println("Resultado Criterio 1"+r1);
                
                try {
                    PreparedStatement psC1=conn.prepareStatement("Update ResultadosXVigencia set puntajeObtenido='"+r1+"'WHERE Criterio='1'");
                    float rsC1=psC1.executeUpdate();
                    
                    if(rsC1>0){
                        System.out.println("Datos insertados correctamente en PuntajeObtenido de la tabla ResultadosXVigencia");
                        muestraResultados("");
                     
                    }
                } catch (SQLException e) {
                    System.err.println("Error al actualizar el puntaje total del Criterio 1 en ResultadosXVigencia "+e);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al recuperar la suma de los puntajes del criterio 1");
            e.printStackTrace();
        }
    }
    
    void ResultadoC2(){
        //creo el string con la sentencia
        String s2="SELECT SUM(conversion)from Criterio2";
        
        String r2=null;
        try {
            PreparedStatement psC2=conn.prepareStatement(s2);
            
            ResultSet rsC2=psC2.executeQuery();
            
            while(rsC2.next()){
                r2=rsC2.getString(1);
            }
            System.out.println("resultado2= "+r2);
            
            try {
                PreparedStatement psR2=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r2+"'WHERE Criterio='2'");
                float RPc2=psR2.executeUpdate();
                if(RPc2>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio2 ");
                    muestraResultados("");
                  
                  
                   
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio2 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    
    void ResultadoC3(){
        //creo el string con la sentencia
        String s3="SELECT SUM(conversion)from Criterio3";
        
        String r3=null;
        try {
            PreparedStatement psC3=conn.prepareStatement(s3);
            
            ResultSet rsC3=psC3.executeQuery();
            
            while(rsC3.next()){
                r3=rsC3.getString(1);
            }
            System.out.println("resultado Criterio3= "+r3);
            
            try {
                PreparedStatement psR3=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r3+"'WHERE Criterio='3'");
                float RPc3=psR3.executeUpdate();
                if(RPc3>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio3 ");
                        muestraResultados("");
                        CuadroResultados();
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio3 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    
    void ResultadoC4(){
        //creo el string con la sentencia
        String s4="SELECT SUM(conversion)from Criterio4";
        
        String r4=null;
        try {
            PreparedStatement psC4=conn.prepareStatement(s4);
            
            ResultSet rsC4=psC4.executeQuery();
            
            while(rsC4.next()){
                r4=rsC4.getString(1);
            }
            System.out.println("Resultado Criterio4= "+r4);
            
            try {
                PreparedStatement psR4=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r4+"'WHERE Criterio='4'");
                float RPc4=psR4.executeUpdate();
                if(RPc4>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio4 ");
                        muestraResultados("");
                        CuadroResultados();
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio4 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    void ResultadoC5(){
        //creo el string con la sentencia
        String s5="SELECT SUM(conversion)from Criterio5";
        
        String r5=null;
        try {
            PreparedStatement psC5=conn.prepareStatement(s5);
            
            ResultSet rsC5=psC5.executeQuery();
            
            while(rsC5.next()){
                r5=rsC5.getString(1);
            }
            System.out.println("Resultado Criterio5= "+r5);
            
            try {
                PreparedStatement psR5=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r5+"'WHERE Criterio='5'");
                float RPc5=psR5.executeUpdate();
                if(RPc5>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio5 ");
                        muestraResultados("");
                        CuadroResultados();
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio4 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    
    
    void ResultadoC6(){
        //creo el string con la sentencia
        String s6="SELECT SUM(conversion)from Criterio6";
        
        String r6=null;
        try {
            PreparedStatement psC6=conn.prepareStatement(s6);
            
            ResultSet rsC6=psC6.executeQuery();
            
            while(rsC6.next()){
                r6=rsC6.getString(1);
            }
            System.out.println("resultado Criterio6 = "+r6);
            
            try {
                PreparedStatement psR6=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r6+"'WHERE Criterio='6'");
                float RPc6=psR6.executeUpdate();
                if(RPc6>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio6 ");
                    muestraResultados("");
                        CuadroResultados();
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio6 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }void ResultadoC7(){
        //creo el string con la sentencia
        String s7="SELECT SUM(conversion)from Criterio7";
        
        String r7=null;
        try {
            PreparedStatement psC7=conn.prepareStatement(s7);
            
            ResultSet rsC7=psC7.executeQuery();
            
            while(rsC7.next()){
                r7=rsC7.getString(1);
            }
            System.out.println("Resultado Criterio7= "+r7);
            
            try {
                PreparedStatement psR7=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r7+"'WHERE Criterio='7'");
                float RPc7=psR7.executeUpdate();
                if(RPc7>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio7 ");
                        muestraResultados("");
                        CuadroResultados();
                    
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio7 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    
    void ResultadoC8(){
        //creo el string con la sentencia
        String s8="SELECT SUM(conversion)from Criterio8";
        
        String r8=null;
        try {
            PreparedStatement psC8=conn.prepareStatement(s8);
            
            ResultSet rsC8=psC8.executeQuery();
            
            while(rsC8.next()){
                r8=rsC8.getString(1);
            }
            System.out.println("Puntaje Criterio8= "+r8);
            
            try {
                PreparedStatement psR8=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r8+"'WHERE Criterio='8'");
                float RPc8=psR8.executeUpdate();
                if(RPc8>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio8 ");
                        muestraResultados("");
                        CuadroResultados();
                
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio8 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT"+e);
        }
        
    }
    void ResultadoC9(){
        //creo el string con la sentencia
        String s9="SELECT SUM(conversion)from Criterio9";
        
        String r9=null;
        try {
            PreparedStatement psC9=conn.prepareStatement(s9);
            
            ResultSet rsC9=psC9.executeQuery();
            
            while(rsC9.next()){
                r9=rsC9.getString(1);
            }
            System.out.println("Puntaje Criterio8= "+r9);
            
            try {
                PreparedStatement psR9=conn.prepareStatement("UPDATE ResultadosXVigencia SET puntajeObtenido='"+r9+"'WHERE Criterio='9'");
                float RPc9=psR9.executeUpdate();
                if(RPc9>0){
                    JOptionPane.showMessageDialog(null,"puntaje Actualizado en ResultadosXVigencia Criterio9 ");
                        muestraResultados("");
                        CuadroResultados(); 
                
                }
                
            } catch (SQLException e) {
                System.err.println("Error al actualizar el puntaje CRiterio9 ResultadosXVigencia... "+e);
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el SELECT... "+e);
            e.printStackTrace();
        }
        
    }
    

    
//Calcular madurez  
    
    
    void SumaMadurezC1() {
        String avg1 = "SELECT (ROUND(AVG(madurez))) from Criterio1";

        String resultado = null;

        try {

            PreparedStatement pst1 = conn.prepareStatement(avg1);

            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                resultado = rs.getString(1);
                System.out.println("resultado Madurez C1 = " + resultado);
            }

            try {
                PreparedStatement psRC1 = conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='" + resultado + "'WHERE Criterio='1'");
                int resRC1 = psRC1.executeUpdate();

                if ((resRC1 > 0)) {
                    ResultadoC1();
                    JOptionPane.showMessageDialog(null, "Madurez del criterio 1 Actualizada");

                } else {
                    JOptionPane.showMessageDialog(null, "Datos Incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al insertar el dato en la tabla ResultadosXVigencia");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato");
            e.printStackTrace();

        }
    }
    
    void SumaMadurezC2(){
        String avg2="SELECT (ROUND(AVG(madurez))) FROM Criterio2";
        
        String resultado=null;
        try {
            PreparedStatement pst2=conn.prepareStatement(avg2);
            ResultSet rs=pst2.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c2= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC2=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='2'");
                int resRC2=psRC2.executeUpdate();
                
                if (resRC2>0){
                    ResultadoC2();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 2 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
    
    
     void SumaMadurezC3(){
        String avg3="SELECT (ROUND(AVG(madurez))) FROM Criterio3";
        
        String resultado=null;
        try {
            PreparedStatement pst3=conn.prepareStatement(avg3);
            ResultSet rs=pst3.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c3= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC3=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='3'");
                int resRC3=psRC3.executeUpdate();
                
                if (resRC3>0){
                    ResultadoC3();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 3 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
     void SumaMadurezC4(){
        String avg4="SELECT (ROUND(AVG(madurez))) FROM Criterio4";
        
        String resultado=null;
        try {
            PreparedStatement pst4=conn.prepareStatement(avg4);
            ResultSet rs=pst4.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c4= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC4=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='4'");
                int resRC4=psRC4.executeUpdate();
                
                if (resRC4>0){
                    ResultadoC4();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 4 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
    
    
    void SumaMadurezC5(){
        String avg5="SELECT (ROUND(AVG(madurez))) from Criterio5";
        
         String resultado = null;
         
        try {
           
             PreparedStatement pst5=conn.prepareStatement(avg5);
             
               ResultSet rs=pst5.executeQuery();
              
                   while(rs.next()){
                      resultado=rs.getString(1);
                       System.out.println("resultado = " + resultado);
                   }
            
            try {
                PreparedStatement psRC5 = conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='" +resultado + "'WHERE Criterio='5'");
                int resRC5 = psRC5.executeUpdate();

                if ((resRC5 > 0)) {
                    ResultadoC5();
                    JOptionPane.showMessageDialog(null, "Madurez del criterio 5 Actualizada");

                } else {
                    JOptionPane.showMessageDialog(null, "Datos Incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al insertar el dato en la tabla ResultadosXVigencia");
                e.printStackTrace();
            }


            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato");
            e.printStackTrace();
            
        }
    }
     void SumaMadurezC6(){
        String avg6="SELECT (ROUND(AVG(madurez))) FROM Criterio6";
        
        String resultado=null;
        try {
            PreparedStatement pst6=conn.prepareStatement(avg6);
            ResultSet rs=pst6.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c6= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC6=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='6'");
                int resRC6=psRC6.executeUpdate();
                
                if (resRC6>0){
                    ResultadoC6();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 6 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
     void SumaMadurezC7(){
        String avg7="SELECT (ROUND(AVG(madurez))) FROM Criterio7";
        
        String resultado=null;
        try {
            PreparedStatement pst7=conn.prepareStatement(avg7);
            ResultSet rs=pst7.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c2= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC7=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='7'");
                int resRC7=psRC7.executeUpdate();
                
                if (resRC7>0){
                    ResultadoC7();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 7 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
     void SumaMadurezC8(){
        String avg8="SELECT (ROUND(AVG(madurez))) FROM Criterio8";
        
        String resultado=null;
        try {
            PreparedStatement pst8=conn.prepareStatement(avg8);
            ResultSet rs=pst8.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c8= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC8=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='8'");
                int resRC8=psRC8.executeUpdate();
                
                if (resRC8>0){
                    ResultadoC8();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 8 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
      void SumaMadurezC9(){
        String avg9="SELECT (ROUND(AVG(madurez))) FROM Criterio9";
        
        String resultado=null;
        try {
            PreparedStatement pst9=conn.prepareStatement(avg9);
            ResultSet rs=pst9.executeQuery();
            
            while(rs.next()){
                resultado=rs.getString(1);
                System.out.println("resultado madurez c2= "+resultado);
            }
            
            
            try {
                PreparedStatement psRC9=conn.prepareStatement("UPDATE ResultadosXVigencia set Madurez='"+resultado+"'WHERE Criterio='9'");
                int resRC9=psRC9.executeUpdate();
                
                if (resRC9>0){
                    ResultadoC9();
                    JOptionPane.showMessageDialog(null,"Madurez del Criterio 9 Actualizada");
                }else{
                    JOptionPane.showMessageDialog(null, "Datos incorrectos");
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el resultado en la tabla ResultadosXVigencia "+e);
            }
            
            
        } catch (SQLException e) {
            System.err.println("Error al recuperar el dato..."+e);
        }
    }
//Calcular puntaje por cada subcriterio  
     void calcularPCr1(){
         String s1a="SELECT(puntajeBase*(madurez/5))FROM Criterio1 WHERE subcriterio='1a'";
         String s1b="SELECT(puntajeBase*(madurez/5))FROM Criterio1 WHERE subcriterio='1b'";
         String s1c="SELECT(puntajeBase*(madurez/5))FROM Criterio1 WHERE subcriterio='1c'";
         String s1d="SELECT(puntajeBase*(madurez/5))FROM Criterio1 WHERE subcriterio='1d'";
         
         String r1a=null;
         String r1b=null;
         String r1c=null;
         String r1d=null;
         
         try {
             PreparedStatement psS1a=conn.prepareStatement(s1a);
             PreparedStatement psS1b=conn.prepareStatement(s1b);
             PreparedStatement psS1c=conn.prepareStatement(s1c);
             PreparedStatement psS1d=conn.prepareStatement(s1d);
             
             ResultSet rs1a=psS1a.executeQuery();
             ResultSet rs1b=psS1b.executeQuery();
             ResultSet rs1c=psS1c.executeQuery();
             ResultSet rs1d=psS1d.executeQuery();
             
              while(rs1a.next()&&rs1b.next()&&rs1c.next()&&rs1d.next()){
                r1a=rs1a.getString(1);
                r1b=rs1b.getString(1);
                r1c=rs1c.getString(1);
                r1d=rs1d.getString(1);
                
                System.out.println("subcriterio 1a= "+r1a);
                System.out.println("subcriterio 1b= "+r1b);
                System.out.println("subcriterio 1c= "+r1c);
                System.out.println("subcriterio 1d= "+r1d);
            }
              try {
                 PreparedStatement psP1a=conn.prepareStatement("UPDATE Criterio1 set conversion='"+r1a+"'WHERE subcriterio='1a'");
                 PreparedStatement psP1b=conn.prepareStatement("UPDATE Criterio1 set conversion='"+r1b+"'WHERE subcriterio='1b'");
                 PreparedStatement psP1c=conn.prepareStatement("UPDATE Criterio1 set conversion='"+r1c+"'WHERE subcriterio='1c'");
                 PreparedStatement psP1d=conn.prepareStatement("UPDATE Criterio1 set conversion='"+r1d+"'WHERE subcriterio='1d'");
                 
                 
                 float P1a=psP1a.executeUpdate();
                 float P1b=psP1b.executeUpdate();
                 float P1c=psP1c.executeUpdate();
                 float P1d=psP1d.executeUpdate();
                 
                 
                 if((P1a>0)&&(P1b>0)&&(P1c>0)&&(P1d>0)){
                     JOptionPane.showMessageDialog(null,"Datos Insertados correctamente en conversión Madurez");
                 }
                 
             } catch (SQLException e) {
                 System.err.println("Error al tratar de actualizar el campo conversión en el criterio 1..."+e);
                 e.printStackTrace();
             }
             
         } catch (SQLException e) {
             System.err.println("Error al recuperar los datos... "+e);
             e.printStackTrace();
         }
     }
      
      void calcularPcr2(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s2a="SELECT (puntajeBase*(madurez/5)) FROM Criterio2 WHERE subcriterio='2a'";
          String s2b="SELECT (puntajeBase*(madurez/5)) FROM Criterio2 WHERE subcriterio='2b'";
          String s2c="SELECT (puntajeBase*(madurez/5)) FROM Criterio2 WHERE subcriterio='2c'";
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r2a=null;
          String r2b=null;
          String r2c=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps2a=conn.prepareStatement(s2a);
              PreparedStatement ps2b=conn.prepareStatement(s2b);
              PreparedStatement ps2c=conn.prepareStatement(s2c);
              //obtengo las consultas
              ResultSet rs2a=ps2a.executeQuery();
              ResultSet rs2b=ps2b.executeQuery();
              ResultSet rs2c=ps2c.executeQuery();
              //recupero los datos de la consulta
              while(rs2a.next()&&rs2b.next()&&rs2c.next()){
                  r2a=rs2a.getString(1);
                  r2b=rs2b.getString(1);
                  r2c=rs2c.getString(1);    
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r2a = "+r2a); 
              System.out.println("r2b = "+r2b);  
              System.out.println("r2c = " + r2c);
            
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR2a="UPDATE Criterio2 SET conversion='"+r2a+"'WHERE subcriterio='2a'";
            String sR2b="UPDATE Criterio2 SET conversion='"+r2b+"'WHERE subcriterio='2b'";
            String sR2c="UPDATE Criterio2 SET conversion='"+r2c+"'WHERE subcriterio='2c'";
            
              try {
                  PreparedStatement psC2a=conn.prepareStatement(sR2a);
                  PreparedStatement psC2b=conn.prepareStatement(sR2b);
                  PreparedStatement psC2c=conn.prepareStatement(sR2c);
                  
                  float p2a=psC2a.executeUpdate();
                  float p2b=psC2b.executeUpdate();
                  float p2c=psC2c.executeUpdate();
                  
                  if((p2a>0)&&(p2b>0)&&(p2c>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio 2 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 2... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio2..."+e);
          }
          
      }
      
    void calcularPcr3(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s3a="SELECT (puntajeBase*(madurez/5)) FROM Criterio3 WHERE subcriterio='3a'";
          String s3b="SELECT (puntajeBase*(madurez/5)) FROM Criterio3 WHERE subcriterio='3b'";
          String s3c="SELECT (puntajeBase*(madurez/5)) FROM Criterio3 WHERE subcriterio='3c'";
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r3a=null;
          String r3b=null;
          String r3c=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps3a=conn.prepareStatement(s3a);
              PreparedStatement ps3b=conn.prepareStatement(s3b);
              PreparedStatement ps3c=conn.prepareStatement(s3c);
              //obtengo las consultas
              ResultSet rs3a=ps3a.executeQuery();
              ResultSet rs3b=ps3b.executeQuery();
              ResultSet rs3c=ps3c.executeQuery();
              //recupero los datos de la consulta
              while(rs3a.next()&&rs3b.next()&&rs3c.next()){
                  r3a=rs3a.getString(1);
                  r3b=rs3b.getString(1);
                  r3c=rs3c.getString(1);    
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r3a = "+r3a); 
              System.out.println("r3b = "+r3b);  
              System.out.println("r3c = " + r3c);
            
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR3a="UPDATE Criterio3 SET conversion='"+r3a+"'WHERE subcriterio='3a'";
            String sR3b="UPDATE Criterio3 SET conversion='"+r3b+"'WHERE subcriterio='3b'";
            String sR3c="UPDATE Criterio3 SET conversion='"+r3c+"'WHERE subcriterio='3c'";
            
              try {
                  PreparedStatement psC3a=conn.prepareStatement(sR3a);
                  PreparedStatement psC3b=conn.prepareStatement(sR3b);
                  PreparedStatement psC3c=conn.prepareStatement(sR3c);
                  
                  float p3a=psC3a.executeUpdate();
                  float p3b=psC3b.executeUpdate();
                  float p3c=psC3c.executeUpdate();
                  
                  if((p3a>0)&&(p3b>0)&&(p3c>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio 3 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 3... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio3..."+e);
          }
          
      }  
      
      void calcularPcr4(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s4a="SELECT (puntajeBase*(madurez/5)) FROM Criterio4 WHERE subcriterio='4a'";
          String s4b="SELECT (puntajeBase*(madurez/5)) FROM Criterio4 WHERE subcriterio='4b'";
          String s4c="SELECT (puntajeBase*(madurez/5)) FROM Criterio4 WHERE subcriterio='4c'";
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r4a=null;
          String r4b=null;
          String r4c=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps4a=conn.prepareStatement(s4a);
              PreparedStatement ps4b=conn.prepareStatement(s4b);
              PreparedStatement ps4c=conn.prepareStatement(s4c);
              //obtengo las consultas
              ResultSet rs4a=ps4a.executeQuery();
              ResultSet rs4b=ps4b.executeQuery();
              ResultSet rs4c=ps4c.executeQuery();
              //recupero los datos de la consulta
              while(rs4a.next()&&rs4b.next()&&rs4c.next()){
                  r4a=rs4a.getString(1);
                  r4b=rs4b.getString(1);
                  r4c=rs4c.getString(1);    
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r4a = "+r4a); 
              System.out.println("r4b = "+r4b);  
              System.out.println("r4c = " + r4c);
            
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR4a="UPDATE Criterio4 SET conversion='"+r4a+"'WHERE subcriterio='4a'";
            String sR4b="UPDATE Criterio4 SET conversion='"+r4b+"'WHERE subcriterio='4b'";
            String sR4c="UPDATE Criterio4 SET conversion='"+r4c+"'WHERE subcriterio='4c'";
            
              try {
                  PreparedStatement psC4a=conn.prepareStatement(sR4a);
                  PreparedStatement psC4b=conn.prepareStatement(sR4b);
                  PreparedStatement psC4c=conn.prepareStatement(sR4c);
                  
                  float p4a=psC4a.executeUpdate();
                  float p4b=psC4b.executeUpdate();
                  float p4c=psC4c.executeUpdate();
                  
                  if((p4a>0)&&(p4b>0)&&(p4c>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio 4 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 4... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio4..."+e);
          }
          
      }  
      
      void calcularPcr5(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s5a="SELECT (puntajeBase*(madurez/5)) FROM Criterio5 WHERE subcriterio='5a'";
          String s5b="SELECT (puntajeBase*(madurez/5)) FROM Criterio5 WHERE subcriterio='5b'";
          String s5c="SELECT (puntajeBase*(madurez/5)) FROM Criterio5 WHERE subcriterio='5c'";
          String s5d="SELECT (puntajeBase*(madurez/5)) FROM Criterio5 WHERE subcriterio='5d'";
          String s5e="SELECT (puntajeBase*(madurez/5)) FROM Criterio5 WHERE subcriterio='5e'";
          
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r5a=null;
          String r5b=null;
          String r5c=null;
          String r5d=null;
          String r5e=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps5a=conn.prepareStatement(s5a);
              PreparedStatement ps5b=conn.prepareStatement(s5b);
              PreparedStatement ps5c=conn.prepareStatement(s5c);
              PreparedStatement ps5d=conn.prepareStatement(s5d);
              PreparedStatement ps5e=conn.prepareStatement(s5e);
              
              //obtengo las consultas
              ResultSet rs5a=ps5a.executeQuery();
              ResultSet rs5b=ps5b.executeQuery();
              ResultSet rs5c=ps5c.executeQuery();
              ResultSet rs5d=ps5d.executeQuery();
              ResultSet rs5e=ps5e.executeQuery();
              
              
              //recupero los datos de la consulta
              while(rs5a.next()&&rs5b.next()&&rs5c.next()&&rs5d.next()&&rs5e.next()){
                  r5a=rs5a.getString(1);
                  r5b=rs5b.getString(1);
                  r5c=rs5c.getString(1); 
                  r5d=rs5d.getString(1);
                  r5e=rs5e.getString(1);
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r5a = "+r5a); 
              System.out.println("r5b = "+r5b);  
              System.out.println("r5c = " + r5c);
              System.out.println("r5d = " + r5d);
              System.out.println("r5e = " + r5e);
            
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR5a="UPDATE Criterio5 SET conversion='"+r5a+"'WHERE subcriterio='5a'";
            String sR5b="UPDATE Criterio5 SET conversion='"+r5b+"'WHERE subcriterio='5b'";
            String sR5c="UPDATE Criterio5 SET conversion='"+r5c+"'WHERE subcriterio='5c'";
            String sR5d="UPDATE Criterio5 SET conversion='"+r5d+"'WHERE subcriterio='5d'";
            String sR5e="UPDATE Criterio5 SET conversion='"+r5e+"'WHERE subcriterio='5e'";
            
              try {
                  PreparedStatement psC5a=conn.prepareStatement(sR5a);
                  PreparedStatement psC5b=conn.prepareStatement(sR5b);
                  PreparedStatement psC5c=conn.prepareStatement(sR5c);
                  PreparedStatement psC5d=conn.prepareStatement(sR5d);
                  PreparedStatement psC5e=conn.prepareStatement(sR5e);
                  
                  float p5a=psC5a.executeUpdate();
                  float p5b=psC5b.executeUpdate();
                  float p5c=psC5c.executeUpdate();
                  float p5d=psC5d.executeUpdate();
                  float p5e=psC5e.executeUpdate();
                  
                  if((p5a>0)&&(p5b>0)&&(p5c>0)&&(p5d>0)&&(p5e>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio5 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 5... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio5..."+e);
          }
          
      }  
      
       void calcularPcr6(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s6a="SELECT (puntajeBase*(madurez/5)) FROM Criterio6 WHERE subcriterio='6a'";
          String s6b="SELECT (puntajeBase*(madurez/5)) FROM Criterio6 WHERE subcriterio='6b'";
          
          
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r6a=null;
          String r6b=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps6a=conn.prepareStatement(s6a);
              PreparedStatement ps6b=conn.prepareStatement(s6b);
          
              
              //obtengo las consultas
              ResultSet rs6a=ps6a.executeQuery();
              ResultSet rs6b=ps6b.executeQuery();
              
              
              
              //recupero los datos de la consulta
              while(rs6a.next()&&rs6b.next()){
                  r6a=rs6a.getString(1);
                  r6b=rs6b.getString(1);
                  
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r6a = "+r6a); 
              System.out.println("r6b = "+r6b);  
       
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR6a="UPDATE Criterio6 SET conversion='"+r6a+"'WHERE subcriterio='6a'";
            String sR6b="UPDATE Criterio6 SET conversion='"+r6b+"'WHERE subcriterio='6b'";
          
            
              try {
                  PreparedStatement psC6a=conn.prepareStatement(sR6a);
                  PreparedStatement psC6b=conn.prepareStatement(sR6b);
                  
                  
                  float p6a=psC6a.executeUpdate();
                  float p6b=psC6b.executeUpdate();
                
                  
                  if((p6a>0)&&(p6b>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio6 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 6... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio6..."+e);
          }
          
      }  
       
       void calcularPcr7(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s7a="SELECT (puntajeBase*(madurez/5)) FROM Criterio7 WHERE subcriterio='7a'";
          String s7b="SELECT (puntajeBase*(madurez/5)) FROM Criterio7 WHERE subcriterio='7b'";
          
          
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r7a=null;
          String r7b=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps7a=conn.prepareStatement(s7a);
              PreparedStatement ps7b=conn.prepareStatement(s7b);
          
              
              //obtengo las consultas
              ResultSet rs7a=ps7a.executeQuery();
              ResultSet rs7b=ps7b.executeQuery();
              
              
              
              //recupero los datos de la consulta
              while(rs7a.next()&&rs7b.next()){
                  r7a=rs7a.getString(1);
                  r7b=rs7b.getString(1);
                  
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r7a = "+r7a); 
              System.out.println("r7b = "+r7b);  
       
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR7a="UPDATE Criterio7 SET conversion='"+r7a+"'WHERE subcriterio='7a'";
            String sR7b="UPDATE Criterio7 SET conversion='"+r7b+"'WHERE subcriterio='7b'";
          
            
              try {
                  PreparedStatement psC7a=conn.prepareStatement(sR7a);
                  PreparedStatement psC7b=conn.prepareStatement(sR7b);
                  
                  
                  float p7a=psC7a.executeUpdate();
                  float p7b=psC7b.executeUpdate();
                
                  
                  if((p7a>0)&&(p7b>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio7 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 7... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio7..."+e);
          }
          
      }  
      
       void calcularPcr8(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s8a="SELECT (puntajeBase*(madurez/5)) FROM Criterio8 WHERE subcriterio='8a'";
          String s8b="SELECT (puntajeBase*(madurez/5)) FROM Criterio8 WHERE subcriterio='8b'";
          
          
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r8a=null;
          String r8b=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps8a=conn.prepareStatement(s8a);
              PreparedStatement ps8b=conn.prepareStatement(s8b);
          
              
              //obtengo las consultas
              ResultSet rs8a=ps8a.executeQuery();
              ResultSet rs8b=ps8b.executeQuery();
              
              
              
              //recupero los datos de la consulta
              while(rs8a.next()&&rs8b.next()){
                  r8a=rs8a.getString(1);
                  r8b=rs8b.getString(1);
                  
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r8a = "+r8a); 
              System.out.println("r8b = "+r8b);  
       
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR8a="UPDATE Criterio8 SET conversion='"+r8a+"'WHERE subcriterio='8a'";
            String sR8b="UPDATE Criterio8 SET conversion='"+r8b+"'WHERE subcriterio='8b'";
          
            
              try {
                  PreparedStatement psC8a=conn.prepareStatement(sR8a);
                  PreparedStatement psC8b=conn.prepareStatement(sR8b);
                  
                  
                  float p8a=psC8a.executeUpdate();
                  float p8b=psC8b.executeUpdate();
                
                  
                  if((p8a>0)&&(p8b>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio8 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 8... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio8..."+e);
          }
          
      }  
      
       void calcularPcr9(){
          //Creo sentencias sql y las almaceno en un string cada una
          String s9a="SELECT (puntajeBase*(madurez/5)) FROM Criterio9 WHERE subcriterio='9a'";
          String s9b="SELECT (puntajeBase*(madurez/5)) FROM Criterio9 WHERE subcriterio='9b'";
          
          
          
          //creo los valores en los cuales voy a almacenar el dato recuperado
          String r9a=null;
          String r9b=null;
          
          try {
             // Envío las consultas a la base de datos
              PreparedStatement ps9a=conn.prepareStatement(s9a);
              PreparedStatement ps9b=conn.prepareStatement(s9b);
          
              
              //obtengo las consultas
              ResultSet rs9a=ps9a.executeQuery();
              ResultSet rs9b=ps9b.executeQuery();
              
              
              
              //recupero los datos de la consulta
              while(rs9a.next()&&rs9b.next()){
                  r9a=rs9a.getString(1);
                  r9b=rs9b.getString(1);
                  
              }
              //Devuelvo los valores recuperados por con sola como medida de control
             System.out.println("r9a = "+r9a); 
              System.out.println("r9b = "+r9b);  
       
              
            //Paso 2: Inserte la suma de ese mierdero en la tabla resultadosxvigencia
            
            String sR9a="UPDATE Criterio9 SET conversion='"+r9a+"'WHERE subcriterio='9a'";
            String sR9b="UPDATE Criterio9 SET conversion='"+r9b+"'WHERE subcriterio='9b'";
          
            
              try {
                  PreparedStatement psC9a=conn.prepareStatement(sR9a);
                  PreparedStatement psC9b=conn.prepareStatement(sR9b);
                  
                  
                  float p9a=psC9a.executeUpdate();
                  float p9b=psC9b.executeUpdate();
                
                  
                  if((p9a>0)&&(p9b>0)){
                      JOptionPane.showMessageDialog(null,"Datos insertados correctamente en tabla Criterio9 Columna conversión");
                      
                  }
                  
              } catch (SQLException e) {
                  System.err.println("Error al actualizar datos conversión Criterio 9... "+e);
                  e.printStackTrace();
                  
              }
              
              
              
          } catch (SQLException e) {
              System.err.println("Error al consultar los datos de la columna conversión en la tabla criterio9..."+e);
          }
          
      }  
      
       
       
       void PuntajeTotal(){
        String Stotal="SELECT SUM(puntajeObtenido) FROM ResultadosXVigencia";
        
        String Ptotal;
        
        
        try {
            PreparedStatement psT=conn.prepareStatement(Stotal);
            
            ResultSet rs=psT.executeQuery();
            
            if(rs.next()){
               Ptotal=rs.getString(1);
               txtPuntaje.setText(Ptotal);
                //JOptionPane.showMessageDialog(null, "Total puntaje recuperado correctamente");
                System.out.println("Puntaje Total:"+Ptotal);
            }
        } catch (SQLException e) {
            System.err.println("error al recuperar el dato..."+e);
        }
        
        
       }
      
     void PromedioMadurez(){
    
         String smadurez="SELECT (FLOOR(AVG(madurez))) FROM ResultadosXVigencia";
         
         String promMadurez=null;
         
         try {
             PreparedStatement psMadurez=conn.prepareStatement(smadurez);
             ResultSet rs=psMadurez.executeQuery();
             
             if(rs.next()){
                promMadurez=rs.getString(1);
                //int promedioMad=Integer.parseInt(promMadurez);
                
                 txtMadurez.setText(promMadurez);
                 System.out.println("promedio madurez = " + promMadurez);
                 
             }
             
             
         } catch (SQLException e) {
             System.err.println("Error al recuperar el valor e introducir la sentencia en el cuadro"+e);
         }
         
}
     
     
     void CriterioAlto(){
       String smax="SELECT (MAX(puntajeObtenido/puntajeBase)) FROM ResultadosXVigencia";  
       
        String Pmax=null;
        
         try {
             PreparedStatement psMax=conn.prepareStatement(smax);
             ResultSet rs=psMax.executeQuery();
             if(rs.next()){
                 Pmax=rs.getString(1);
                 txtmejorPuntuado.setText(Pmax);
                 System.out.println("Criterio Mayor Cumplimiento"+Pmax);
             }
             
         } catch (SQLException e) {
             e.printStackTrace();
         }
      
     }
     
      void Prioridad1(){
          String smin="SELECT MIN(puntajeObtenido/puntajeBase) from ResultadosXVigencia";
          
          String rmin=null;
          
          try {
              PreparedStatement psmin=conn.prepareStatement(smin);
              ResultSet rs=psmin.executeQuery();
              if (rs.next()){
                  rmin=rs.getString(1);
                  txtPrioridad1.setText(rmin);
                  System.out.println("Dato recuperado correctamente..."+rmin);
              }
              
          } catch (SQLException e) {
              e.printStackTrace();
          }
          
         
      }
      void PuntajeBase(){
          String sBase="SELECT SUM(puntajeBase) from ResultadosXVigencia";
          String Tbase=null;
          try {
              PreparedStatement psBase=conn.prepareStatement(sBase);
              ResultSet rs=psBase.executeQuery();
              if(rs.next()){
                  Tbase=rs.getString(1);
                  txtPrioridad2.setText(Tbase);
                  System.out.println("Total Puntos del modelo= "+Tbase);
              }
              
          } catch (SQLException e) {
              System.err.println("error al ejecutar Sentencia... "+e);
              e.printStackTrace();
          }
      }
             
          
       
     void CuadroResultados(){
         PuntajeTotal();
         PromedioMadurez();
         CriterioAlto();
         Prioridad1();
         PuntajeBase();
         barras3D();
     }
       
//      void datosGrafico(){
//          String cr="SELECT (Criterio) from ResultadosXVigencia";
//          String pb="SELECT (puntajeBase)from ResultadosXVigencia";
//          String ro="SELECT (puntajeObtenido from ResultadosXVigencia";
//          
//          Object [] criterios=new Object[9];
//          Object [] pBase=new Object[9];
//          Object [] pObtenido=new Object[9];
//          
//          try {
//              PreparedStatement pscri=conn.prepareStatement(cr);
//              PreparedStatement pspbs=conn.prepareStatement(pb);
//              PreparedStatement pspob=conn.prepareStatement(ro);
//              
//              ResultSet rs1=pscri.executeQuery();
//              ResultSet rs2=pspbs.executeQuery();
//              ResultSet rs3=pspob.executeQuery();
//              
//              
//              
//              if(rs1.next()&&rs2.next()&&rs3.next()){
//                  while(rs1.next()&&rs2.next()&&rs3.next()){
//                      criterios[]=rs1.getObject(1);
//                      
//                      i++;
//                  }
//              }
//              
//              
//          } catch (SQLException e) {
//              
//              System.err.println();
//              e.printStackTrace();
//          }
//          
//          
//          
//          
//          
//      } 
       void barras3D(){
           
           DefaultCategoryDataset dtsc=new DefaultCategoryDataset();
           
           for (int i = 0; i < tablaResultados.getRowCount(); i++) {
               dtsc.setValue(Float.parseFloat(tablaResultados.getValueAt(i,0).toString()),(tablaResultados.getValueAt(i, 3).toString()),(tablaResultados.getValueAt(i, 4).toString()));
               
           }
           JFreeChart ch=ChartFactory.createBarChart("Resumen Resultados","Criterio","Puntaje Base",dtsc,PlotOrientation.VERTICAL,true,true,false);
           ChartPanel cp=new ChartPanel(ch);
           add(cp);
           cp.setBounds(500, 60, 400, 400);
           cp.setVisible(true);
           
           
           
       }
        

       
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        BtnLiderazgo = new javax.swing.JButton();
        jLabel84 = new javax.swing.JLabel();
        PnlResClv = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        lbl9a = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtC9a = new javax.swing.JTextField();
        txt9b = new javax.swing.JTextField();
        btnReg9 = new javax.swing.JButton();
        based4 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jscroll9 = new javax.swing.JScrollPane();
        textA9 = new javax.swing.JTextArea();
        tabPlanM = new javax.swing.JTabbedPane();
        tabInicio = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        based12 = new javax.swing.JLabel();
        jPanDiag = new javax.swing.JPanel();
        TabLiderazgi = new javax.swing.JTabbedPane();
        Indicaciones = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        based10 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        PnlLidr = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt1a = new javax.swing.JTextField();
        txt1b = new javax.swing.JTextField();
        txt1c = new javax.swing.JTextField();
        txt1d = new javax.swing.JTextField();
        btnRegistrarC1 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbl1a = new javax.swing.JLabel();
        lbl1b = new javax.swing.JLabel();
        lbl1c = new javax.swing.JLabel();
        lbl1d = new javax.swing.JLabel();
        based2 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        PnlEstrategia = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblSub2 = new javax.swing.JLabel();
        lbl2a = new javax.swing.JLabel();
        lbl2b = new javax.swing.JLabel();
        lbl2c = new javax.swing.JLabel();
        lblDesc2 = new javax.swing.JLabel();
        txt2a = new javax.swing.JTextField();
        txt2b = new javax.swing.JTextField();
        txt2c = new javax.swing.JTextField();
        lblDesc3 = new javax.swing.JLabel();
        jlbl2a = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jlbl2c = new javax.swing.JLabel();
        based1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel68 = new javax.swing.JLabel();
        btnRegistrarC2 = new javax.swing.JButton();
        PnlPersonas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblDescr3 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt3a = new javax.swing.JTextField();
        txt3c = new javax.swing.JTextField();
        based3 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jscroll3 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        lbl3a = new javax.swing.JLabel();
        lbl3a1 = new javax.swing.JLabel();
        lbl3a2 = new javax.swing.JLabel();
        txt3b = new javax.swing.JTextField();
        btnRegistrarC3 = new javax.swing.JButton();
        PnlAlianzas = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt4a = new javax.swing.JTextField();
        based6 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jscroll4 = new javax.swing.JScrollPane();
        textA4 = new javax.swing.JTextArea();
        btnRegistrarC4 = new javax.swing.JButton();
        txt4c = new javax.swing.JTextField();
        txt4b = new javax.swing.JTextField();
        PnlProcesos = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txt5a = new javax.swing.JTextField();
        txt5b = new javax.swing.JTextField();
        txt5c = new javax.swing.JTextField();
        txt5d = new javax.swing.JTextField();
        txt5e = new javax.swing.JTextField();
        btnRegistrarC5 = new javax.swing.JButton();
        based7 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jscroll5 = new javax.swing.JScrollPane();
        textA5 = new javax.swing.JTextArea();
        PnlResCli = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txt6a = new javax.swing.JTextField();
        txt6b = new javax.swing.JTextField();
        btnRegistrarC6 = new javax.swing.JButton();
        based9 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jscroll6 = new javax.swing.JScrollPane();
        textA6 = new javax.swing.JTextArea();
        PnlResPer = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        lbl9a2 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lbl7b = new javax.swing.JLabel();
        lbl7a = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        txt7a = new javax.swing.JTextField();
        txt7b = new javax.swing.JTextField();
        btnRegistrarC7 = new javax.swing.JButton();
        based8 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jscroll7 = new javax.swing.JScrollPane();
        textA7 = new javax.swing.JTextArea();
        PnlResSoc = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        lbl9a1 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txt8a = new javax.swing.JTextField();
        txt8b = new javax.swing.JTextField();
        btnRegistrarC8 = new javax.swing.JButton();
        based5 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jscroll8 = new javax.swing.JScrollPane();
        textA8 = new javax.swing.JTextArea();
        PnlRsClav = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        txt9a = new javax.swing.JTextField();
        txt9c = new javax.swing.JTextField();
        btnRegistrarC9 = new javax.swing.JButton();
        jLabel93 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lbl1a1 = new javax.swing.JLabel();
        lbl1b1 = new javax.swing.JLabel();
        based11 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jPanRep = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        panelGrafico = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        lblprioridad1 = new javax.swing.JLabel();
        txtPuntaje = new javax.swing.JTextField();
        txtMadurez = new javax.swing.JTextField();
        txtPrioridad1 = new javax.swing.JTextField();
        txtmejorPuntuado = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        lblprioridad2 = new javax.swing.JLabel();
        txtPrioridad2 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        btnBarras = new javax.swing.JButton();
        btnTorta = new javax.swing.JButton();
        jLabel101 = new javax.swing.JLabel();
        JPanPlaM = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMejora = new javax.swing.JTable();
        btnRegistrarAccion = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtpresupuesto = new javax.swing.JTextField();
        txtplazo = new javax.swing.JTextField();
        txtresp = new javax.swing.JTextField();
        txtaccion = new javax.swing.JTextField();
        txtsub = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        txtNa = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnCancelarM = new javax.swing.JButton();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnReporte = new javax.swing.JButton();
        btnEliminaAccionesMejora = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        jCheckBox1.setText("jCheckBox1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        BtnLiderazgo.setBackground(new java.awt.Color(0, 0, 0));
        BtnLiderazgo.setFont(new java.awt.Font("Roboto Black", 3, 12)); // NOI18N
        BtnLiderazgo.setForeground(new java.awt.Color(255, 255, 255));
        BtnLiderazgo.setText("Criterio 1:Liderazgo");
        BtnLiderazgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLiderazgoActionPerformed(evt);
            }
        });

        jLabel84.setText("jLabel84");

        jLabel48.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel48.setText("Subcriterios");

        lbl9a.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl9a.setText("9a.");

        jLabel50.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel50.setText("9b.");

        jLabel51.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Indicadores clave de rendimiento");

        jLabel52.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Resultados clave de la actividad");

        jLabel53.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel53.setText("Descripción");

        jLabel54.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel54.setText("Calificación");

        btnReg9.setBackground(new java.awt.Color(0, 0, 0));
        btnReg9.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnReg9.setForeground(new java.awt.Color(255, 255, 255));
        btnReg9.setText("Registrar");

        based4.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based4.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel77.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA9.setEditable(false);
        textA9.setBackground(new java.awt.Color(204, 204, 204));
        textA9.setColumns(20);
        textA9.setFont(new java.awt.Font("Roboto Light", 1, 13)); // NOI18N
        textA9.setRows(5);
        textA9.setText("La empresa conoce cuáles son sus resultados clave a\n alcanzar en el desarrollo de sus actividades (estos resultados pueden ser económico-financieros\ntales como ventas, márgenes, beneficios, dividendos, etc.) también conoce aquellos resultados no\neconómicos como cuotas de mercado, nuevos productos, nuevos mercados.\n\nLa empresa cuenta con Indicadores clave de rendimiento, que le permiten supervisar, entender, predecir\ny mejorar los probables resultados clave. Pueden hacer referencia a elementos de nuestros procesos y de\nnuestros recursos como, por ejemplo:\n\n- Procesos: rendimiento, tiempos de ciclo, productividad.\n- Economía y finanzas: Rendimiento de activos, depreciaciones….\n- Materiales: Índice de defectos, rotación de inventarios.\n");
        jscroll9.setViewportView(textA9);

        javax.swing.GroupLayout PnlResClvLayout = new javax.swing.GroupLayout(PnlResClv);
        PnlResClv.setLayout(PnlResClvLayout);
        PnlResClvLayout.setHorizontalGroup(
            PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResClvLayout.createSequentialGroup()
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReg9)
                .addContainerGap())
            .addGroup(PnlResClvLayout.createSequentialGroup()
                .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResClvLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel50)
                            .addComponent(lbl9a))
                        .addGap(126, 126, 126)
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51)))
                    .addGroup(PnlResClvLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel48)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel53)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResClvLayout.createSequentialGroup()
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt9b, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(txtC9a))
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResClvLayout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(93, 93, 93))))
            .addComponent(jscroll9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PnlResClvLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(based4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlResClvLayout.setVerticalGroup(
            PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlResClvLayout.createSequentialGroup()
                .addComponent(jscroll9, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResClvLayout.createSequentialGroup()
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(jLabel53)
                            .addComponent(jLabel48))
                        .addGap(48, 48, 48)
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtC9a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl9a)
                                .addComponent(jLabel52)))
                        .addGap(70, 70, 70)
                        .addGroup(PnlResClvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt9b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51)
                            .addComponent(jLabel50))
                        .addGap(77, 77, 77)
                        .addComponent(btnReg9)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResClvLayout.createSequentialGroup()
                        .addComponent(based4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Modelo para la mejora continua de las pequeñas empresas manufactureras");
        setBackground(new java.awt.Color(51, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setSize(new java.awt.Dimension(0, 0));

        tabPlanM.setBackground(new java.awt.Color(255, 255, 255));
        tabPlanM.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N

        tabInicio.setBackground(new java.awt.Color(255, 255, 255));
        tabInicio.setAutoscrolls(true);
        tabInicio.setMaximumSize(new java.awt.Dimension(600, 600));

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\modelRecurso 2.png")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel70.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\modelRecurso 1escudo_2x.png")); // NOI18N

        jLabel71.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(51, 51, 51));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Universidad de Pamplona");

        jLabel72.setBackground(new java.awt.Color(255, 255, 255));
        jLabel72.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(51, 51, 51));
        jLabel72.setText("Maestría en Ingeniería Industrial");

        jLabel73.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(51, 51, 51));
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("2021");

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setIcon(new javax.swing.ImageIcon("C:\\Users\\R_CASTRO\\Pictures\\0.25x\\modelRecurso 2Acreditacion_peq.png")); // NOI18N

        jLabel83.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        based12.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based12.setText("<html> <small><b>basado en:</b></small>");

        javax.swing.GroupLayout tabInicioLayout = new javax.swing.GroupLayout(tabInicio);
        tabInicio.setLayout(tabInicioLayout);
        tabInicioLayout.setHorizontalGroup(
            tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInicioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInicioLayout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addGap(328, 328, 328))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInicioLayout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addGap(312, 312, 312))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInicioLayout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGap(218, 218, 218))))
            .addGroup(tabInicioLayout.createSequentialGroup()
                .addGroup(tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabInicioLayout.createSequentialGroup()
                        .addGroup(tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabInicioLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabInicioLayout.createSequentialGroup()
                                .addGap(246, 246, 246)
                                .addComponent(jLabel71)))
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(tabInicioLayout.createSequentialGroup()
                        .addGroup(tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel83)
                            .addGroup(tabInicioLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(based12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel69)))
                .addContainerGap())
        );
        tabInicioLayout.setVerticalGroup(
            tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInicioLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addGap(1, 1, 1)
                .addComponent(jLabel70)
                .addGap(24, 24, 24)
                .addGroup(tabInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabInicioLayout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addGap(4, 4, 4)
                        .addComponent(based12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel69))
                .addGap(25, 25, 25))
        );

        tabPlanM.addTab("Inicio", tabInicio);

        jPanDiag.setBackground(new java.awt.Color(51, 51, 51));

        TabLiderazgi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TabLiderazgi.setFont(new java.awt.Font("Roboto Black", 3, 12)); // NOI18N

        Indicaciones.setBackground(new java.awt.Color(255, 255, 255));

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setIcon(new javax.swing.ImageIcon("C:\\Users\\R_CASTRO\\Pictures\\1x\\modelRecurso 3niveles_madurez.png")); // NOI18N

        jLabel63.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("<html> <strong> La manera de calificar los subcriterios en cada una de las siguientes secciones, se basa en la lógica de los modelos de madurez que se muestra a continuación, la misma comprende que el puntaje mínimo es 1 y el máximo es 5, por ende el correcto diligenciamiento y registro de la información permitirá en primera instancia conocer la realidad de la empresas, sus aspectos a mejorar y por último, definir el curso de acción mediante un plan de mejoramiento.");

        based10.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based10.setText("<html> <small><b>basado en:</b></small>");

        jLabel86.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setIcon(new javax.swing.ImageIcon("C:\\Users\\R_CASTRO\\Pictures\\0.25x\\modelRecurso 2Acreditacion_peq.png")); // NOI18N

        jLabel67.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("<html> <strong>En cuanto al dilgenciamiento del plan de mejoramiento, se requiere que en lo posible, no se repitan acciones para la correcta consolidación del plan de mejoramiento ya que, al contar con una estrategia clara para la mejora, se puede definir un mejor curso de acción para la organización, con acciones y plazo definidos para el reporte de las evidencias, por último, el presente aplicativo es escalable y en caso de ser un aporte benéfico para las organizaciones, cuenta con la posibilidad de mejorar sus prestaciones e incluir módulos nuevos.</strong>");

        javax.swing.GroupLayout IndicacionesLayout = new javax.swing.GroupLayout(Indicaciones);
        Indicaciones.setLayout(IndicacionesLayout);
        IndicacionesLayout.setHorizontalGroup(
            IndicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IndicacionesLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(IndicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(IndicacionesLayout.createSequentialGroup()
                .addGroup(IndicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(IndicacionesLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(based10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel86))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel74)
                .addContainerGap())
        );
        IndicacionesLayout.setVerticalGroup(
            IndicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IndicacionesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(IndicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IndicacionesLayout.createSequentialGroup()
                        .addComponent(based10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel74, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        TabLiderazgi.addTab("Indicaciones", Indicaciones);

        PnlLidr.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel10.setText("Subcriterio");

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setText("1a.");

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel12.setText("1b.");

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel13.setText("1c.");

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel14.setText("1d.");

        jLabel15.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Descripción");

        btnRegistrarC1.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC1.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC1.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC1.setText("Registrar");
        btnRegistrarC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC1ActionPerformed(evt);
            }
        });

        jLabel66.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel66.setText("Calificación");

        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(false);

        TextAreaLiderazgo.setEditable(false);
        TextAreaLiderazgo.setColumns(20);
        TextAreaLiderazgo.setFont(new java.awt.Font("Roboto Light", 1, 15)); // NOI18N
        TextAreaLiderazgo.setRows(5);
        TextAreaLiderazgo.setText("El Liderazgo hace referencia a los comportamientos e iniciativas promulgadas por los gerentes,\ndirectivos (o también aquellas personas que tengan responsabilidad sobre otras) para la\n consolidación de una cultura dentro de la empresa, en donde sus actuaciones y comportamientos \nsirven como ejemplo para sus colaboradores y crean una base o sustento que puede ser traducida \nen principios y valores éticos que pueden ser demostrados en la organización.");
        TextAreaLiderazgo.setBorder(null);
        TextAreaLiderazgo.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(TextAreaLiderazgo);

        lbl1a.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1a.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1a.setText("<html>\n<small>Los líderes desarrollan la Misión, Visión, valores y principios éticos y actúan como modelo de referencia.</small>");

        lbl1b.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1b.setText("<html><small>Los líderes definen, supervisan, revisan e impulsan tanto la mejora del sistema de gestión de la organización como su rendimiento.</small>");

        lbl1c.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1c.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1c.setText("<html><small>\nLos líderes definen, supervisan, revisan e impulsan tanto la mejora del sistema de gestión de la organización como su rendimiento.</small>");

        lbl1d.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1d.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1d.setText("<html><small>\nLos líderes se aseguran de que la organización sea flexible y gestionan el cambio de manera eficaz.</small>");

        based2.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based2.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel75.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        javax.swing.GroupLayout PnlLidrLayout = new javax.swing.GroupLayout(PnlLidr);
        PnlLidr.setLayout(PnlLidrLayout);
        PnlLidrLayout.setHorizontalGroup(
            PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlLidrLayout.createSequentialGroup()
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PnlLidrLayout.createSequentialGroup()
                            .addGap(126, 126, 126)
                            .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGroup(PnlLidrLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel75))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlLidrLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel13)))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel14)
                        .addGap(46, 46, 46)
                        .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl1a, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PnlLidrLayout.createSequentialGroup()
                                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl1c, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl1d, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)
                                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt1a, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt1b, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt1c, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt1d, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbl1b, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel10)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel66))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(based2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(btnRegistrarC1)
                .addGap(19, 19, 19))
            .addComponent(jScrollPane2)
        );
        PnlLidrLayout.setVerticalGroup(
            PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlLidrLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel66))
                .addGap(18, 18, 18)
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1a, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txt1a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl1b, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt1b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel12)))
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lbl1c, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel13))
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(txt1c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlLidrLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt1d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl1d, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(based2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(PnlLidrLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(PnlLidrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlLidrLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(PnlLidrLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRegistrarC1)
                                .addGap(24, 24, 24))))))
        );

        TabLiderazgi.addTab("Criterio 1: Liderazgo", PnlLidr);

        PnlEstrategia.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        lblSub2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblSub2.setText("Subcriterio");

        lbl2a.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl2a.setText("2a");

        lbl2b.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl2b.setText("2b");

        lbl2c.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl2c.setText("2c");

        lblDesc2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblDesc2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDesc2.setText("Descripción");

        txt2a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt2aActionPerformed(evt);
            }
        });

        lblDesc3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblDesc3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDesc3.setText("Calificación");

        jlbl2a.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        jlbl2a.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl2a.setText("<html>\n<small>\nLa estrategia se basa en comprender las necesidades y expectativas de los grupos de interés y del entorno externo.\n</small>");

        jLabel85.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("<html> <small>La estrategia se basa en comprender el rendimiento de la organización y sus capacidades.</small>");

        jlbl2c.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        jlbl2c.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl2c.setText("<html> <small>La estrategia y sus políticas de apoyo se comunican, implantan y supervisan.<small>");

        based1.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based1.setText("<html>\n<small><b>basado en:</b></small>");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl2a, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl2b, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl2c, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jlbl2a, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt2a, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(lblSub2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(lblDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(lblDesc3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(based1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbl2c, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt2b, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt2c, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDesc2)
                    .addComponent(lblDesc3)
                    .addComponent(lblSub2))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbl2a, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lbl2a))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(txt2a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbl2b))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt2b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl2c, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl2c)
                    .addComponent(txt2c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(based1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Roboto Light", 1, 15)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("El criterio de estrategia, se relaciona con el despliegue de la misión y la visión de la organización, \nesto hace referencia a que las empresas definan objetivos que sean acordes a la razón de ser \nde la organización y lleve a cabo los planes y estrategias para alcanzar los objetivos propuestos \nen el desarrollo de su razón social. Así mismo hace referencia a si la empresa ha identificado\naquellos factores externos que le pueden afectar en el mercado, como grupos de interés,\nnuevos entrantes, productos sustitutos, que le permitirán ajustar la estrategia a la realidad\nde su contexto.\n");
        jTextArea1.setBorder(null);
        jTextArea1.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(jTextArea1);

        jLabel68.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        btnRegistrarC2.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC2.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC2.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC2.setText("Registrar");
        btnRegistrarC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlEstrategiaLayout = new javax.swing.GroupLayout(PnlEstrategia);
        PnlEstrategia.setLayout(PnlEstrategiaLayout);
        PnlEstrategiaLayout.setHorizontalGroup(
            PnlEstrategiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlEstrategiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlEstrategiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlEstrategiaLayout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrarC2)
                        .addGap(33, 33, 33))
                    .addGroup(PnlEstrategiaLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))))
            .addComponent(jScrollPane4)
        );
        PnlEstrategiaLayout.setVerticalGroup(
            PnlEstrategiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlEstrategiaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(PnlEstrategiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlEstrategiaLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnRegistrarC2)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlEstrategiaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        TabLiderazgi.addTab("Criterio 2:Estrategia", PnlEstrategia);

        PnlPersonas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("3a");

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setText("3b");

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel16.setText("3c");

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel17.setText("Subcriterio");

        lblDescr3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblDescr3.setText("Descripción");

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel19.setText("Calificación");

        txt3a.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        txt3c.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        txt3c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt3cActionPerformed(evt);
            }
        });

        based3.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based3.setText("<html> <small><b>basado en:</b></small>");

        jLabel76.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        jTextArea7.setEditable(false);
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Roboto Light", 0, 15)); // NOI18N
        jTextArea7.setRows(5);
        jTextArea7.setText("hace referencia al modo en que la organización valora, promueve y desarrolla el potencial \nde sus trabajadores, así como a las maneras de alinear los objetivos personales con la visión \nde la organización. La empresa deberá involucrar y hacer partícipes del proyecto de la organización\na todos sus colaboradores estableciendomecanismos que refuercen la comunicación y el diálogo, \ndesarrollando las capacidades de las personas respecto a la toma de decisionesy asumir \nresponsabilidades.\n");
        jTextArea7.setBorder(null);
        jTextArea7.setCaretColor(new java.awt.Color(255, 255, 255));
        jscroll3.setViewportView(jTextArea7);

        lbl3a.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl3a.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3a.setText("<html>\n<small>Las personas se comunican eficazmente en toda la organización.</small>");

        lbl3a1.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl3a1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3a1.setText("<html>\n<small>Se desarrolla el conocimiento y las capacidades de las personas.</small>");

        lbl3a2.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl3a2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3a2.setText("<html>\n<small>Las personas están alineadas con las necesidades de la organización, implicadas y asumen su responsabilidad.</small>");

        txt3b.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        txt3b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt3bActionPerformed(evt);
            }
        });

        btnRegistrarC3.setBackground(new java.awt.Color(51, 51, 51));
        btnRegistrarC3.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC3.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC3.setText("Registrar");
        btnRegistrarC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlPersonasLayout = new javax.swing.GroupLayout(PnlPersonas);
        PnlPersonas.setLayout(PnlPersonasLayout);
        PnlPersonasLayout.setHorizontalGroup(
            PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlPersonasLayout.createSequentialGroup()
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlPersonasLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlPersonasLayout.createSequentialGroup()
                                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(30, 30, 30))
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlPersonasLayout.createSequentialGroup()
                                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PnlPersonasLayout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl3a2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl3a, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(55, 55, 55))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlPersonasLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl3a1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(68, 68, 68)))
                                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt3c, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt3a, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt3b, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlPersonasLayout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(lblDescr3)
                                .addGap(104, 104, 104)
                                .addComponent(jLabel19))))
                    .addGroup(PnlPersonasLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(based3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 117, Short.MAX_VALUE))
            .addGroup(PnlPersonasLayout.createSequentialGroup()
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC3)
                .addGap(40, 40, 40))
            .addComponent(jscroll3)
        );
        PnlPersonasLayout.setVerticalGroup(
            PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlPersonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jscroll3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblDescr3)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl3a1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt3a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(23, 23, 23)
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl3a2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt3b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl3a, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt3c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(based3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlPersonasLayout.createSequentialGroup()
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlPersonasLayout.createSequentialGroup()
                        .addComponent(btnRegistrarC3)
                        .addGap(40, 40, 40))))
        );

        TabLiderazgi.addTab("Criterio 3:Personas", PnlPersonas);

        PnlAlianzas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel18.setText("subcriterios");

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel20.setText("Descripción");

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel21.setText("4b.");

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel22.setText("4c.");

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel24.setText("4a.");

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel23.setText("Calificación");

        jLabel25.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("<html>\n<small>Gestión de los recursos económico-financieros para asegurar un éxito sostenido.</small>");

        jLabel26.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("<html>\n<small>Gestión de la tecnología para hacer realidad la estrategia.</small>");

        jLabel27.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        jLabel27.setText("<html>\n<small>Gestión de la información y del conocimiento para apoyar una eficaz toma de decisiones y construir las capacidades de la organización.</small>");

        based6.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based6.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel79.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA4.setEditable(false);
        textA4.setColumns(20);
        textA4.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        textA4.setRows(5);
        textA4.setText("Alianzas y recursos contempla aquellos convenios, acuerdos y estrategias implementadas por la organización \npara mantener una posición sostenible y que sea mutuamente benéfica para las partes, en ella se incluyen\ntodos aquellos acuerdos como por ejemplo plazos de entrega, tiempos de reaprovisionamiento, descuentos,\nplazos de pago entre otros directamente relacionados con organizaciones o empresas externas.\nAsí mismo, debe gestionar los recursos de una forma eficaz y eficiente en apoyo de la estrategia y planes para\nun eficaz funcionamiento de sus procesos, por ello se consideran recursos como:\n-  Recursos económicos y financieros.\n-  Instalaciones máquinas y equipos.\n-  medios tecnológicos.\n- gestión del conocimiento.");
        jscroll4.setViewportView(textA4);

        btnRegistrarC4.setBackground(new java.awt.Color(51, 51, 51));
        btnRegistrarC4.setFont(new java.awt.Font("Roboto Light", 1, 15)); // NOI18N
        btnRegistrarC4.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC4.setText("Registrar");
        btnRegistrarC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC4ActionPerformed(evt);
            }
        });

        txt4c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt4cActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlAlianzasLayout = new javax.swing.GroupLayout(PnlAlianzas);
        PnlAlianzas.setLayout(PnlAlianzasLayout);
        PnlAlianzasLayout.setHorizontalGroup(
            PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAlianzasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC4)
                .addGap(38, 38, 38))
            .addComponent(jscroll4)
            .addGroup(PnlAlianzasLayout.createSequentialGroup()
                .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlAlianzasLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addGap(69, 69, 69)
                        .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PnlAlianzasLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel18)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel20)))
                .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlAlianzasLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt4a, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt4c, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt4b, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAlianzasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addGap(116, 116, 116))))
            .addGroup(PnlAlianzasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(based6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlAlianzasLayout.setVerticalGroup(
            PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlAlianzasLayout.createSequentialGroup()
                .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlAlianzasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jscroll4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlAlianzasLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel21)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel22)
                                .addGap(27, 27, 27))
                            .addGroup(PnlAlianzasLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txt4a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(txt4b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(txt4c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAlianzasLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(18, 18, 18)
                .addComponent(based6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlAlianzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAlianzasLayout.createSequentialGroup()
                        .addComponent(btnRegistrarC4)
                        .addGap(43, 43, 43))))
        );

        TabLiderazgi.addTab("Criterio 4: Alianzas y Recursos", PnlAlianzas);

        PnlProcesos.setBackground(new java.awt.Color(255, 255, 255));
        PnlProcesos.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel28.setText("Subcriterios");

        jLabel29.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel29.setText("5a.");

        jLabel30.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel30.setText("5b.");

        jLabel31.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel31.setText("5c.");

        jLabel32.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel32.setText("5d.");

        jLabel33.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel33.setText("5e.");

        jLabel34.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel34.setText("Descripción");

        jLabel35.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("<html>\n<small>Los procesos se diseñan y gestionan a fin de optimizar el valor para los grupos de interés.</small>");

        jLabel36.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("<html>\n<small>Los productos y servicios se desarrollan para dar valor óptimo a los clientes.</small>");

        jLabel37.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("<html>\n<small>Los productos y servicios se promocionan y ponen en el mercado eficazmente.</small>");

        jLabel38.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("<html>\n<small>Los productos y servicios se producen, distribuyen y gestionan.</small>\n");

        jLabel39.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("<html>\n<small>Las relaciones con los clientes se gestionan y mejoran.</small>");

        jLabel40.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel40.setText("Calificación");

        txt5a.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        txt5a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt5aActionPerformed(evt);
            }
        });

        txt5b.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        txt5c.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        txt5d.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        txt5d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt5dActionPerformed(evt);
            }
        });

        txt5e.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        btnRegistrarC5.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC5.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC5.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC5.setText("Registrar");
        btnRegistrarC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC5ActionPerformed(evt);
            }
        });

        based7.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based7.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel80.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA5.setEditable(false);
        textA5.setColumns(20);
        textA5.setFont(new java.awt.Font("Roboto Light", 1, 13)); // NOI18N
        textA5.setRows(5);
        textA5.setText("El criterio Procesos productos y servicios hace referencia a la manera en cómo las organizaciones diseñan, \ngestionan y mejoran sus procesos, dentro de este criterio se incluyen la trazabilidad del diseño, así como \nlos procesos de retroalimentaciónentre los clientes y la empresa, con el fin de satisfacer las necesidades de\nmanera cada vez más precisa. Para ello se analiza si la empresa ha identificado sus procesos y subprocesos,\nha identificado sus procesos clave y cómo se introducen los cambios yse evalúan los resultados. En cuanto a los \nproductos y servicios se analiza si se identifican las necesidades actuales y futuras de los clientes y cómo se \ndiseñan productos/servicios a partir de dichas necesidades.\n");
        jscroll5.setViewportView(textA5);

        javax.swing.GroupLayout PnlProcesosLayout = new javax.swing.GroupLayout(PnlProcesos);
        PnlProcesos.setLayout(PnlProcesosLayout);
        PnlProcesosLayout.setHorizontalGroup(
            PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlProcesosLayout.createSequentialGroup()
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel80)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlProcesosLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(based7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(52, 52, 52)))
                            .addGroup(PnlProcesosLayout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel29)
                                        .addGroup(PnlProcesosLayout.createSequentialGroup()
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(1, 1, 1))
                                        .addComponent(jLabel31))
                                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel32)
                                            .addComponent(jLabel30))))))
                        .addGap(104, 104, 104)
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlProcesosLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel28)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel34)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt5a, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(txt5b, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt5c, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt5d, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt5e, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlProcesosLayout.createSequentialGroup()
                        .addComponent(btnRegistrarC5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
            .addComponent(jscroll5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        PnlProcesosLayout.setVerticalGroup(
            PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlProcesosLayout.createSequentialGroup()
                .addComponent(jscroll5, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel29))
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txt5a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlProcesosLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(txt5b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30)))
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt5c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(10, 10, 10))
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlProcesosLayout.createSequentialGroup()
                        .addComponent(txt5d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(based7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlProcesosLayout.createSequentialGroup()
                                .addComponent(btnRegistrarC5)
                                .addGap(18, 18, 18))))
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33))
                    .addGroup(PnlProcesosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PnlProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt5e, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );

        TabLiderazgi.addTab("Criterio 5:Procesos", PnlProcesos);

        PnlResCli.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel41.setText("Subcriterio");

        jLabel42.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel42.setText("Descripción");

        jLabel43.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel43.setText("Calificación");

        jLabel44.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel44.setText("6a");

        jLabel45.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel45.setText("6b");

        jLabel46.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Percepciones");

        jLabel47.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel47.setText("Indicadores");

        btnRegistrarC6.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC6.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC6.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC6.setText("Registrar");
        btnRegistrarC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC6ActionPerformed(evt);
            }
        });

        based9.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based9.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel82.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA6.setEditable(false);
        textA6.setColumns(20);
        textA6.setFont(new java.awt.Font("Roboto Light", 1, 13)); // NOI18N
        textA6.setRows(5);
        textA6.setText("rl ítem percepciones Hace referencia a conocer los puntos de vista que tienen los clientes de los productos y\nserviciosque oferta la empresa. Las percepciones se obtienen mediante encuestas, buzón de sugerencias, \nllamadas telefónicas o contacto mediante los correos corporativos, evaluaciones estructuradas, para conocer\nlas opiniones respecto a quejas,distribución de productos o servicios,atención posventa, reposición de productos\nentre otros. \nAsí mismo los indicadores Hace referencia a los métodos para recopilar la información (indicadores) que sirven \npara supervisar, entender, predecir y mejorar el desempeño de la empresa y así anticiparse a las percepciones\nde los clientes. Dentro de estas medidas internas se contemplan algunas como niveles de satisfacción al cliente,\nproducto no conforme, costes de garantía.\n");
        jscroll6.setViewportView(textA6);

        javax.swing.GroupLayout PnlResCliLayout = new javax.swing.GroupLayout(PnlResCli);
        PnlResCli.setLayout(PnlResCliLayout);
        PnlResCliLayout.setHorizontalGroup(
            PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlResCliLayout.createSequentialGroup()
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC6)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResCliLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel44))
                .addGap(535, 535, 535))
            .addGroup(PnlResCliLayout.createSequentialGroup()
                .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(based9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlResCliLayout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(112, 112, 112)
                                .addComponent(txt6a, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PnlResCliLayout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(122, 122, 122)
                                .addComponent(txt6b, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel41)
                        .addGap(113, 113, 113)
                        .addComponent(jLabel42)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel43)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jscroll6)
        );
        PnlResCliLayout.setVerticalGroup(
            PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResCliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jscroll6, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43))
                .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel44))
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt6a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))))
                .addGap(65, 65, 65)
                .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt6b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47)
                            .addComponent(jLabel45))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PnlResCliLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(PnlResCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResCliLayout.createSequentialGroup()
                                .addComponent(based9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResCliLayout.createSequentialGroup()
                                .addComponent(btnRegistrarC6)
                                .addGap(39, 39, 39))))))
        );

        TabLiderazgi.addTab("Criterio 6: Resultados en los clientes", PnlResCli);

        PnlResPer.setBackground(new java.awt.Color(255, 255, 255));

        jLabel60.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel60.setText("Subcriterios");

        lbl9a2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl9a2.setText("7a.");

        jLabel61.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel61.setText("7b.");

        lbl7b.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        lbl7b.setText("Indicadores");

        lbl7a.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        lbl7a.setText("Percepciones");

        jLabel64.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel64.setText("Descripción");

        jLabel65.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel65.setText("Calificación");

        txt7a.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        txt7b.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N

        btnRegistrarC7.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC7.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC7.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC7.setText("Registrar");
        btnRegistrarC7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC7ActionPerformed(evt);
            }
        });

        based8.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based8.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel81.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA7.setEditable(false);
        textA7.setColumns(20);
        textA7.setFont(new java.awt.Font("Roboto Light", 1, 13)); // NOI18N
        textA7.setRows(5);
        textA7.setText("La empresa conoce y considera las percepciones de sus colaboradores sobre eficacia del\ndespliegue y los resultados de la estrategia de personas, sus políticas de apoyo y sus procesos.\n(incentivos, satisfacción laboral,compromiso, desarrollo profesional, clima organizacional, seguridad, estabilidad,\nentre otros aplicables a la empresa). Estas percepciones se obtienen mediante encuestas, buzón de sugerencias, \nllamadas telefónicas o contacto mediante los correos corporativos, evaluaciones estructuradas. \nEl primer paso es identificar los aspectos significativos para  las personas y cuál es su importancia relativa, \nluego se analiza su percepción. \nla empresa cuenta con Indicadores que permitan supervisar, entender, predecir y mejorar el rendimiento \nde la empresa y anticiparse a las percepciones de sus colaboradores. Dentro de estas medidas internas \nse pueden analizar aspectos como el rendimiento de las personas de la organización, formación, ausentismo,\nquejas, participación entre otras.\n");
        jscroll7.setViewportView(textA7);

        javax.swing.GroupLayout PnlResPerLayout = new javax.swing.GroupLayout(PnlResPer);
        PnlResPer.setLayout(PnlResPerLayout);
        PnlResPerLayout.setHorizontalGroup(
            PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlResPerLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl9a2)
                    .addComponent(jLabel61))
                .addGap(162, 162, 162)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl7b)
                    .addComponent(lbl7a))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt7a, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt7b, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
            .addGroup(PnlResPerLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jLabel60)
                .addGap(106, 106, 106)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jLabel65)
                .addGap(73, 73, 73))
            .addComponent(jscroll7)
            .addGroup(PnlResPerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC7)
                .addGap(41, 41, 41))
            .addGroup(PnlResPerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(based8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlResPerLayout.setVerticalGroup(
            PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlResPerLayout.createSequentialGroup()
                .addComponent(jscroll7, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jLabel65)
                    .addComponent(jLabel60))
                .addGap(52, 52, 52)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl7a)
                    .addComponent(txt7a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl9a2))
                .addGap(50, 50, 50)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(txt7b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl7b))
                .addGap(34, 34, 34)
                .addComponent(based8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlResPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResPerLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnRegistrarC7))
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86))
        );

        TabLiderazgi.addTab("Criterio 7:Resultados en las personas", PnlResPer);

        PnlResSoc.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel49.setText("Subcriterios");

        lbl9a1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbl9a1.setText("8a.");

        jLabel55.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel55.setText("8b.");

        jLabel56.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel56.setText("Indicadores");

        jLabel57.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        jLabel57.setText("Percepciones");

        jLabel58.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel58.setText("Descripción");

        jLabel59.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel59.setText("Calificación");

        btnRegistrarC8.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC8.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC8.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC8.setText("Registrar");
        btnRegistrarC8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC8ActionPerformed(evt);
            }
        });

        based5.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based5.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel78.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        textA8.setEditable(false);
        textA8.setColumns(20);
        textA8.setFont(new java.awt.Font("Roboto Light", 1, 13)); // NOI18N
        textA8.setRows(5);
        textA8.setText("Resultados clave de la actividad: La empresa conoce cuáles son sus resultados clave a alcanzar en el desarrollo \nde sus actividades (estos resultados pueden ser económico-financieros tales como ventas, márgenes, beneficios,\ndividendos, etc.) también conoce aquellos resultados no económicos como cuotas de mercado, nuevos productos,\nnuevos mercados.\n9b. Indicadores clave: la empresa cuenta con Indicadores clave de rendimiento, que le permiten supervisar, \nentender, predecir y mejorar los probables resultados clave. Pueden hacer referencia a elementos de nuestros \nprocesos y de  nuestros recursos como, por ejemplo:\n- Procesos: rendimiento, tiempos de ciclo, productividad.\n- Economía y finanzas: Rendimiento de activos, depreciaciones….\n- Materiales: Índice de defectos, rotación de inventarios.\n\n");
        jscroll8.setViewportView(textA8);

        javax.swing.GroupLayout PnlResSocLayout = new javax.swing.GroupLayout(PnlResSoc);
        PnlResSoc.setLayout(PnlResSocLayout);
        PnlResSocLayout.setHorizontalGroup(
            PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscroll8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResSocLayout.createSequentialGroup()
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC8)
                .addGap(33, 33, 33))
            .addGroup(PnlResSocLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(based5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PnlResSocLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(lbl9a1))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResSocLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addGap(73, 73, 73)
                .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(PnlResSocLayout.createSequentialGroup()
                            .addComponent(jLabel57)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt8a, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlResSocLayout.createSequentialGroup()
                            .addComponent(jLabel56)
                            .addGap(122, 122, 122)
                            .addComponent(txt8b, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PnlResSocLayout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel59)))
                .addGap(130, 130, 130))
        );
        PnlResSocLayout.setVerticalGroup(
            PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlResSocLayout.createSequentialGroup()
                .addComponent(jscroll8, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59)
                    .addComponent(jLabel49))
                .addGap(31, 31, 31)
                .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt8a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(lbl9a1))
                .addGap(52, 52, 52)
                .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResSocLayout.createSequentialGroup()
                        .addGroup(PnlResSocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt8b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56)
                            .addComponent(jLabel55))
                        .addGap(64, 64, 64)
                        .addComponent(btnRegistrarC8)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlResSocLayout.createSequentialGroup()
                        .addComponent(based5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        TabLiderazgi.addTab("Criterio 8: Resultados en la sociedad", PnlResSoc);

        PnlRsClav.setBackground(new java.awt.Color(255, 255, 255));

        jLabel87.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel87.setText("Subcriterio");

        jLabel88.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel88.setText("9a.");

        jLabel90.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel90.setText("9b.");

        jLabel92.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Descripción");

        btnRegistrarC9.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarC9.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarC9.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarC9.setText("Registrar");
        btnRegistrarC9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarC9ActionPerformed(evt);
            }
        });

        jLabel93.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel93.setText("Calificación");

        jScrollPane5.setBorder(null);
        jScrollPane5.setOpaque(false);

        TextAreaLiderazgo1.setEditable(false);
        TextAreaLiderazgo1.setColumns(20);
        TextAreaLiderazgo1.setFont(new java.awt.Font("Roboto Light", 1, 15)); // NOI18N
        TextAreaLiderazgo1.setRows(5);
        TextAreaLiderazgo1.setText("Resultados clave de la actividad: La empresa conoce cuáles son sus resultados clave a alcanzar\n en el desarrollo de sus actividades (estos resultados pueden ser económico-financieros \ntales como ventas, márgenes, beneficios, dividendos, etc.) \ntambién conoce aquellos resultados no económicos como cuotas de mercado, \nnuevos productos, nuevos mercados.\n\n9b. Indicadores estratégicos: la empresa cuenta con Indicadores clave de rendimiento,\nque le permiten supervisar, entender, predecir y mejorar los probables resultados clave. \nPueden hacer referencia a elementos de nuestros procesosy de nuestros recursos como, \n\npor ejemplo:\n- Procesos: rendimiento, tiempos de ciclo, productividad.\n- Economía y finanzas: Rendimiento de activos, depreciaciones….\n");
        TextAreaLiderazgo1.setBorder(null);
        TextAreaLiderazgo1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jScrollPane5.setViewportView(TextAreaLiderazgo1);

        lbl1a1.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1a1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1a1.setText("Percepciones Clave");

        lbl1b1.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        lbl1b1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1b1.setText("Indicadores Estratégicos");

        based11.setFont(new java.awt.Font("Roboto Light", 1, 11)); // NOI18N
        based11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        based11.setText("<html>\n<small><b>basado en:</b></small>");

        jLabel94.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\ModeloExitoSostenido\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        javax.swing.GroupLayout PnlRsClavLayout = new javax.swing.GroupLayout(PnlRsClav);
        PnlRsClav.setLayout(PnlRsClavLayout);
        PnlRsClavLayout.setHorizontalGroup(
            PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlRsClavLayout.createSequentialGroup()
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94)
                    .addGroup(PnlRsClavLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(based11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarC9)
                .addGap(34, 34, 34))
            .addGroup(PnlRsClavLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel88)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl1b1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl1a1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt9a, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt9c, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlRsClavLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel87)
                .addGap(69, 69, 69)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(jLabel93)
                .addGap(120, 120, 120))
            .addComponent(jScrollPane5)
        );
        PnlRsClavLayout.setVerticalGroup(
            PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlRsClavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel92)
                    .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlRsClavLayout.createSequentialGroup()
                        .addComponent(jLabel88)
                        .addGap(8, 8, 8))
                    .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl1a1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt9a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl1b1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt9c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PnlRsClavLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel90)))
                .addGap(8, 8, 8)
                .addGroup(PnlRsClavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlRsClavLayout.createSequentialGroup()
                        .addComponent(btnRegistrarC9)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlRsClavLayout.createSequentialGroup()
                        .addComponent(based11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );

        TabLiderazgi.addTab("Criterio 9:Resultados Clave", PnlRsClav);

        javax.swing.GroupLayout jPanDiagLayout = new javax.swing.GroupLayout(jPanDiag);
        jPanDiag.setLayout(jPanDiagLayout);
        jPanDiagLayout.setHorizontalGroup(
            jPanDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabLiderazgi)
        );
        jPanDiagLayout.setVerticalGroup(
            jPanDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanDiagLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabLiderazgi, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPlanM.addTab("Diagnóstico", jPanDiag);

        jPanRep.setBackground(new java.awt.Color(255, 255, 255));

        tablaResultados.setAutoCreateRowSorter(true);
        tablaResultados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaResultados.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaResultados.setEnabled(false);
        jScrollPane3.setViewportView(tablaResultados);

        panelGrafico.setBackground(new java.awt.Color(255, 255, 255));
        panelGrafico.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                panelGraficoComponentAdded(evt);
            }
        });

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "RESUMEN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Black", 3, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Puntaje alcanzado");

        jLabel97.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Madurez");

        lblprioridad1.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        lblprioridad1.setForeground(new java.awt.Color(255, 255, 255));
        lblprioridad1.setText("Prioridad");

        txtPuntaje.setEditable(false);
        txtPuntaje.setBackground(new java.awt.Color(255, 255, 255));
        txtPuntaje.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        txtPuntaje.setForeground(new java.awt.Color(102, 0, 0));
        txtPuntaje.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntajeActionPerformed(evt);
            }
        });

        txtMadurez.setEditable(false);
        txtMadurez.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        txtMadurez.setForeground(new java.awt.Color(102, 0, 0));
        txtMadurez.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtPrioridad1.setEditable(false);
        txtPrioridad1.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        txtPrioridad1.setForeground(new java.awt.Color(102, 0, 0));
        txtPrioridad1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrioridad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrioridad1ActionPerformed(evt);
            }
        });

        txtmejorPuntuado.setEditable(false);
        txtmejorPuntuado.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        txtmejorPuntuado.setForeground(new java.awt.Color(102, 0, 0));
        txtmejorPuntuado.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel98.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("Mejor puntuado");

        lblprioridad2.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        lblprioridad2.setForeground(new java.awt.Color(255, 255, 255));
        lblprioridad2.setText("Puntos Posibles");

        txtPrioridad2.setEditable(false);
        txtPrioridad2.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        txtPrioridad2.setForeground(new java.awt.Color(102, 0, 0));
        txtPrioridad2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(lblprioridad1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPrioridad1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel98)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmejorPuntuado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel97)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMadurez, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblprioridad2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPrioridad2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblprioridad2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPrioridad2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMadurez, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel98)
                    .addComponent(txtmejorPuntuado, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrioridad1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblprioridad1))
                .addGap(44, 44, 44))
        );

        jLabel99.setIcon(new javax.swing.ImageIcon("G:\\Mi unidad\\respaldos\\tg\\RC8_ModeloExitoSostenido_jar_1.8.00_intellij\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        jLabel100.setIcon(new javax.swing.ImageIcon("G:\\Mi unidad\\respaldos\\tg\\RC8_ModeloExitoSostenido_jar_1.8.00_intellij\\src\\main\\java\\Images\\modelRecurso 2Acreditacion_peq.png")); // NOI18N

        btnBarras.setText("barras");
        btnBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarrasActionPerformed(evt);
            }
        });

        btnTorta.setText("circular");
        btnTorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTortaActionPerformed(evt);
            }
        });

        jLabel101.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel101.setText("basado en:");

        javax.swing.GroupLayout jPanRepLayout = new javax.swing.GroupLayout(jPanRep);
        jPanRep.setLayout(jPanRepLayout);
        jPanRepLayout.setHorizontalGroup(
            jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanRepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanRepLayout.createSequentialGroup()
                        .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel101)
                            .addComponent(jLabel99))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel100))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                    .addGroup(jPanRepLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanRepLayout.createSequentialGroup()
                                .addComponent(btnBarras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTorta)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(panelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanRepLayout.setVerticalGroup(
            jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanRepLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanRepLayout.createSequentialGroup()
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanRepLayout.createSequentialGroup()
                        .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel100)
                            .addGroup(jPanRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBarras)
                                .addComponent(btnTorta)))
                        .addGap(18, 18, 18))))
        );

        tabPlanM.addTab("Reporte", jPanRep);

        JPanPlaM.setBackground(new java.awt.Color(255, 255, 255));

        tablaMejora.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        tablaMejora.setForeground(new java.awt.Color(51, 51, 51));
        tablaMejora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaMejora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMejoraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMejora);

        btnRegistrarAccion.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarAccion.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnRegistrarAccion.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarAccion.setText("Registrar Acción");
        btnRegistrarAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarAccionActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Subcriterio:");

        jLabel5.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Acción a implementar:");

        jLabel6.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Responsable:");

        jLabel7.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Plazo de ejecución:");

        jLabel8.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Presupuesto estipulado:");

        txtaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtaccionActionPerformed(evt);
            }
        });

        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("#:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel91)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtplazo, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addComponent(txtresp, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtsub, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtaccion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtpresupuesto))
                    .addComponent(txtNa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtresp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtplazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtpresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnActualizar.setBackground(new java.awt.Color(0, 0, 0));
        btnActualizar.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelarM.setBackground(new java.awt.Color(0, 0, 0));
        btnCancelarM.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnCancelarM.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarM.setText("Cancelar");
        btnCancelarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMActionPerformed(evt);
            }
        });

        lblBuscar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblBuscar.setText("Buscar:");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        btnReporte.setBackground(new java.awt.Color(0, 0, 0));
        btnReporte.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setText("Generar Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        btnEliminaAccionesMejora.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminaAccionesMejora.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        btnEliminaAccionesMejora.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminaAccionesMejora.setText("Borrar registros");
        btnEliminaAccionesMejora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaAccionesMejoraActionPerformed(evt);
            }
        });

        jLabel95.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\RC8_ModeloExitoSostenido_jar_2.0\\src\\main\\java\\Images\\Recurso 1.png")); // NOI18N

        jLabel89.setIcon(new javax.swing.ImageIcon("C:\\Cursos\\Java\\Fundamentos\\RC8_ModeloExitoSostenido_jar_2.0\\src\\main\\java\\Images\\modelRecurso 2Acreditacion_peq.png")); // NOI18N

        jLabel96.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        jLabel96.setText("basado en:");

        javax.swing.GroupLayout JPanPlaMLayout = new javax.swing.GroupLayout(JPanPlaM);
        JPanPlaM.setLayout(JPanPlaMLayout);
        JPanPlaMLayout.setHorizontalGroup(
            JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanPlaMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanPlaMLayout.createSequentialGroup()
                        .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel95)
                            .addGroup(JPanPlaMLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel96)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel89))
                    .addComponent(jScrollPane1)
                    .addGroup(JPanPlaMLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRegistrarAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelarM, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminaAccionesMejora, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 151, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JPanPlaMLayout.setVerticalGroup(
            JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanPlaMLayout.createSequentialGroup()
                .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel89)
                    .addGroup(JPanPlaMLayout.createSequentialGroup()
                        .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanPlaMLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPanPlaMLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(btnRegistrarAccion)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar)
                                .addGap(26, 26, 26)
                                .addComponent(btnCancelarM)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminaAccionesMejora)
                                .addGap(18, 18, 18)
                                .addComponent(btnReporte)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanPlaMLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(JPanPlaMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblBuscar)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(JPanPlaMLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel96)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(27, 27, 27))
        );

        tabPlanM.addTab("Plan de mejoramiento", JPanPlaM);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Roboto", 3, 12)); // NOI18N

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu3.setText("Acerca de...");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPlanM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPlanM, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnLiderazgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLiderazgoActionPerformed
    
    }//GEN-LAST:event_BtnLiderazgoActionPerformed

    private void txtaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtaccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaccionActionPerformed

    private void txt5aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt5aActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt5aActionPerformed

    private void btnRegistrarC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC5ActionPerformed
        try {
            PreparedStatement psC5a=conn.prepareStatement("UPDATE Criterio5 SET madurez='"+txt5a.getText()+"'WHERE subcriterio='5a'");
            PreparedStatement psC5b=conn.prepareStatement("UPDATE Criterio5 SET madurez='"+txt5b.getText()+"'WHERE subcriterio='5b'");
            PreparedStatement psC5c=conn.prepareStatement("UPDATE Criterio5 SET madurez='"+txt5c.getText()+"'WHERE subcriterio='5c'");
            PreparedStatement psC5d=conn.prepareStatement("UPDATE Criterio5 SET madurez='"+txt5d.getText()+"'WHERE subcriterio='5d'");
            PreparedStatement psC5e=conn.prepareStatement("UPDATE Criterio5 SET madurez='"+txt5e.getText()+"'WHERE subcriterio='5e'");
           
        int respuesta5a=psC5a.executeUpdate();
        int respuesta5b=psC5b.executeUpdate();
        int respuesta5c=psC5c.executeUpdate();
        int respuesta5d=psC5d.executeUpdate();
        int respuesta5e=psC5e.executeUpdate(); 
      
            
        
        if ((respuesta5a>0)&&(respuesta5b>0)&&(respuesta5c>0)&&(respuesta5d>0)&&(respuesta5e>0)){
            JOptionPane.showMessageDialog(null, "Datos insertados correctamente en tabla Criterio 5:Procesos,productos y servicios");
            limpiarC5();
            calcularPcr5();
            SumaMadurezC5();
            muestraResultados("");
        }else{
            JOptionPane.showMessageDialog(null,"Datos Incorrectos");
        }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al insertar los datos en la tabla... "+e);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos en tabla");
            
        }



        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC5ActionPerformed

    
    
    
    
    
    
    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnRegistrarC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC1ActionPerformed

        try {
            PreparedStatement psC1a=conn.prepareStatement("UPDATE Criterio1 SET madurez='"+txt1a.getText()
                    +"' WHERE subcriterio='1a'");
            PreparedStatement psC1b=conn.prepareStatement("UPDATE Criterio1 SET madurez='"+txt1b.getText()
                    +"' WHERE subcriterio='1b'");
            
            PreparedStatement psC1c=conn.prepareStatement("UPDATE Criterio1 SET madurez='"+txt1c.getText()
                    +"' WHERE subcriterio='1c'");
           
            PreparedStatement psC1d=conn.prepareStatement("UPDATE Criterio1 SET madurez='"+txt1d.getText()
                    +"' WHERE subcriterio='1d'");
            
            int respuesta1a=psC1a.executeUpdate();
            int respuesta1b=psC1b.executeUpdate();
             int respuesta1c=psC1c.executeUpdate();
            int respuesta1d=psC1d.executeUpdate();
            
            if (respuesta1a>0 && respuesta1b>0&&respuesta1c>0&&respuesta1d>0){
               
                JOptionPane.showMessageDialog(null,"Datos Insertados en tabla Criterio 1:Liderazgo !");
                //sumaC1();
                limpiarC1();
                SumaMadurezC1();
                //actualizar tabla de resultados
                muestraResultados("");
                calcularPCr1();
                
                //mostrarTablaResultados("");
            }else{
               JOptionPane.showMessageDialog(null,"Datos incorrectos");
            }
        } catch (SQLException e) {
     System.err.println("Error al actualizar ..."+e);
     JOptionPane.showMessageDialog(null,"Error al actualizar");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC1ActionPerformed

    private void btnRegistrarC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC2ActionPerformed
 try {
            PreparedStatement psC2a=conn.prepareStatement("UPDATE Criterio2 SET madurez='"+txt2a.getText()
                    +"' WHERE subcriterio='2a'");
            PreparedStatement psC2b=conn.prepareStatement("UPDATE Criterio2 SET madurez='"+txt2b.getText()
                    +"' WHERE subcriterio='2b'");
            
            PreparedStatement psC2c=conn.prepareStatement("UPDATE Criterio2 SET madurez='"+txt2c.getText()
                    +"' WHERE subcriterio='2c'");
           
         
            
            int respuesta2a=psC2a.executeUpdate();
            int respuesta2b=psC2b.executeUpdate();
             int respuesta2c=psC2c.executeUpdate();
            
            
            if ((respuesta2a>0) &&(respuesta2b>0)&&(respuesta2c>0)){
                JOptionPane.showMessageDialog(null,"Datos Insertados en tabla Criterio 2:Estrategia");
                limpiarC2();
                calcularPcr2();
                SumaMadurezC2();
                
                muestraResultados("");
                
                //mostrarTablaResultados("");
            }else{
               JOptionPane.showMessageDialog(null,"Datos incorrectos");
            }
        } catch (SQLException e) {
     System.err.println("Error al actualizar ..."+e);
     JOptionPane.showMessageDialog(null,"Error al actualizar");
        }





        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC2ActionPerformed

    private void txt3cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt3cActionPerformed

    private void txt5dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt5dActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt5dActionPerformed

    private void btnRegistrarC9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC9ActionPerformed
        try {
       PreparedStatement psC9a=conn.prepareStatement("UPDATE Criterio9 SET madurez='"+txt9a.getText()+"'WHERE subcriterio='9a'");
       PreparedStatement psC9b=conn.prepareStatement("UPDATE Criterio9 SET madurez='"+txt9c.getText()+"'WHERE subcriterio='9b'");
           
       int respuesta9a=psC9a.executeUpdate();
       int respuesta9b=psC9b.executeUpdate();
       if((respuesta9a>0)&&(respuesta9b>0)){
           JOptionPane.showMessageDialog(null,"Datos insertados correctamente en la tabla Criterio9:Resultados Clave");
           limpiarC9();
           calcularPcr9();
           SumaMadurezC9();
           muestraResultados("");
       }else{
           JOptionPane.showMessageDialog(null, "Datos incorrectos");
       }
        } catch (SQLException e) {
            System.err.println("Error al insertar los datos... "+e);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos en la tabla");
            
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC9ActionPerformed

    private void btnCancelarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMActionPerformed
        limpiarTMejora();
    }//GEN-LAST:event_btnCancelarMActionPerformed

    private void btnRegistrarAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAccionActionPerformed

 try {
            PreparedStatement ps=conn.prepareStatement("INSERT INTO planMejora (subcriterio,accion,responsable,plazoEjecucion,presupuesto)VALUES(?,?,?,?,?)");
            ps.setString(1,txtsub.getText());
            ps.setString(2,txtaccion.getText());
            ps.setString(3,txtresp.getText());
            ps.setString(4,txtplazo.getText());
            ps.setString(5,txtpresupuesto.getText());
            
            ps.executeUpdate();
            limpiarTMejora();
            mostrarTablaMejora("");
            JOptionPane.showMessageDialog(null, "Datos guardados");
        
        } catch (SQLException e) {
            System.err.println("Error al guardar ..."+e);
            
                                               



        }









        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarAccionActionPerformed

    private void txt3bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3bActionPerformed
       



        // TODO add your handling code here:
    }//GEN-LAST:event_txt3bActionPerformed

    private void btnRegistrarC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC3ActionPerformed
 try {
            PreparedStatement psC3a=conn.prepareStatement("UPDATE Criterio3 SET madurez='"+txt3a.getText()+"' WHERE subcriterio='3a'");
            PreparedStatement psC3b=conn.prepareStatement("UPDATE Criterio3 SET madurez='"+txt3b.getText()+"' WHERE subcriterio='3b'");
            PreparedStatement psC3c=conn.prepareStatement("UPDATE Criterio3 SET madurez='"+txt3c.getText()+"' WHERE subcriterio='3c'");
            
            int respuesta3a=psC3a.executeUpdate();
            int respuesta3b=psC3b.executeUpdate();
            int respuesta3c=psC3c.executeUpdate();
            
            if((respuesta3a>0)&&(respuesta3b>0)&&(respuesta3c>0)){
                JOptionPane.showMessageDialog(null, "Datos insertados Correctamente en Criterio 3:Personas");
                limpiarC3();
                calcularPcr3();
                SumaMadurezC3();
                muestraResultados("");
            }else{
                JOptionPane.showMessageDialog(null,"Datos Incorrectos");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar... "+e);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC3ActionPerformed

    private void btnRegistrarC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC4ActionPerformed


        try {
            PreparedStatement psC4a = conn.prepareStatement("UPDATE Criterio4 SET madurez='"+txt4a.getText()+ "' WHERE subcriterio='4a'");
            PreparedStatement psC4b = conn.prepareStatement("UPDATE Criterio4 SET madurez='"+txt4b.getText()+ "' WHERE subcriterio='4b'");
            PreparedStatement psC4c = conn.prepareStatement("UPDATE Criterio4 SET madurez='"+txt4c.getText()+ "' WHERE subcriterio='4c'");

            int respuesta4a = psC4a.executeUpdate();
            int respuesta4b = psC4b.executeUpdate();
            int respuesta4c = psC4c.executeUpdate();

            if ((respuesta4a > 0) && (respuesta4b > 0) && (respuesta4c > 0)) {
                JOptionPane.showMessageDialog(null, "Datos insertados correctamente en la tabla criterio4:Alianzas y recursos");
            limpiarC4();
            calcularPcr4();
            SumaMadurezC4();
            muestraResultados("");
            } else {
                JOptionPane.showMessageDialog(null, "Datos Incorrectos");
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar... " + e);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos");
        }



        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC4ActionPerformed

    private void btnRegistrarC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC6ActionPerformed

        try {
            PreparedStatement ps6a=conn.prepareStatement("UPDATE Criterio6 SET madurez='"+txt6a.getText()+"'WHERE subcriterio='6a'");
            PreparedStatement ps6b=conn.prepareStatement("UPDATE Criterio6 SET madurez='"+txt6b.getText()+"'WHERE subcriterio='6b'");
            
       int respuesta6a=ps6a.executeUpdate();
       int respuesta6b=ps6b.executeUpdate();
       if ((respuesta6a>0)&&(respuesta6b>0)){
               JOptionPane.showMessageDialog(null,"Datos insertados correctamente en la tabla Criterio 6:Resultados en los clientes!");
               limpiarC6();
               calcularPcr6();
               SumaMadurezC6();
               muestraResultados("");
       }else{
           JOptionPane.showMessageDialog(null, "Dato Incorrecto");
       }
       
        } catch (SQLException e) {
            System.err.println("Error al insertar datos.... "+e);
            JOptionPane.showMessageDialog(null,"Error al insertar los datos");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC6ActionPerformed

    private void btnRegistrarC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC7ActionPerformed
        try {
            PreparedStatement psC7a=conn.prepareStatement("UPDATE Criterio7 SET madurez='"+txt7a.getText()+"'WHERE subcriterio='7a'");
            PreparedStatement psC7b=conn.prepareStatement("UPDATE Criterio7 SET madurez='"+txt7b.getText()+"'WHERE subcriterio='7b'");
            
            int respuesta7a=psC7a.executeUpdate();
            int respuesta7b=psC7b.executeUpdate();
            
            if ((respuesta7a>0)&&(respuesta7b>0)){
                JOptionPane.showMessageDialog(null, "Datos insertados correctamente en la tabla Criterio 7:Resultados en las personas!!!");
            limpiarC7();
            calcularPcr7();
            SumaMadurezC7();
            muestraResultados("");
            }else{
                JOptionPane.showMessageDialog(null, "Datos Incorrectos");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar los datos"+e);
            JOptionPane.showMessageDialog(null,"Error al insertar los datos en la tabla");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC7ActionPerformed

    private void btnRegistrarC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarC8ActionPerformed
        try {
            PreparedStatement psC8a=conn.prepareStatement("UPDATE Criterio8 SET madurez='"+txt8a.getText()+"'WHERE subcriterio='8a'");
            PreparedStatement psC8b=conn.prepareStatement("Update Criterio8 SET madurez='"+txt8b.getText()+"'WHERE subcriterio='8b'");
            
        int respuesta8a=psC8a.executeUpdate();
        int respuesta8b=psC8b.executeUpdate();
        
        if((respuesta8a>0)&&(respuesta8b>0)){
            JOptionPane.showMessageDialog(null, "Datos Insertados Correctamente en la tabla Criterio 8:Resultados en la sociedad!");
        limpiarC8();
        calcularPcr8();
        SumaMadurezC8();
        muestraResultados("");
        }else{
            JOptionPane.showMessageDialog(null, "Datos incorrectos");
        }
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en tabla... "+e);
            JOptionPane.showMessageDialog(null, "Error al insertar los datos en la tabla");
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarC8ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            PreparedStatement psActualizar=conn.prepareStatement("UPDATE PlanMejora SET subcriterio='"
                    +txtsub.getText()
                    +"',accion='"+txtaccion.getText()
                    +"',responsable='"+txtresp.getText()
                    +"',plazoEjecucion='"+txtplazo.getText()
                    +"',presupuesto='"+txtpresupuesto.getText()
                    +"'WHERE num_accion='"+txtNa.getText()+"'");
            
            int respuestaActualizar=psActualizar.executeUpdate();
            
            if (respuestaActualizar>0){
                JOptionPane.showMessageDialog(null, "Acción de mejoramiento actualizada");
                limpiarTMejora();
                mostrarTablaMejora("");
                muestraResultados("");
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado Fila");
                
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar... "+e);
            JOptionPane.showMessageDialog(null, "Error al actualizar");
        }



        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tablaMejoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMejoraMouseClicked

int fila=this.tablaMejora.getSelectedRow();
this.txtNa.setText(this.tablaMejora.getValueAt(fila,0).toString());
this.txtsub.setText(this.tablaMejora.getValueAt(fila,1).toString());
this.txtaccion.setText(this.tablaMejora.getValueAt(fila,2).toString());
this.txtresp.setText(this.tablaMejora.getValueAt(fila,3).toString());
this.txtplazo.setText(this.tablaMejora.getValueAt(fila,4).toString());
this.txtpresupuesto.setText(this.tablaMejora.getValueAt(fila,5).toString());




        // TODO add your handling code here:
    }//GEN-LAST:event_tablaMejoraMouseClicked

    private void btnEliminaAccionesMejoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaAccionesMejoraActionPerformed

        try {
            PreparedStatement psEliminarMejoramiento=conn.prepareStatement("DELETE FROM 'PlanMejora'");
            PreparedStatement psReiniciarNa=conn.prepareStatement("UPDATE SQLITE_SEQUENCE SET seq=0 WHERE name='PlanMejora'");
            
            int resultado1=psEliminarMejoramiento.executeUpdate();
            int resultado2=psReiniciarNa.executeUpdate();
            
            if ((resultado1>0)&&(resultado2>0)){
               JOptionPane.showMessageDialog(null, "Registros plan de mejoramiento eliminados");
               
                limpiarTMejora();
                mostrarTablaMejora("");
            }else{
                JOptionPane.showMessageDialog(null, "Error al eliminar y reiniciar la tabla del plan de mejoramiento..");
            }
        } catch (SQLException e) {
            System.err.println("No se puede limpiar tabla... "+e);
            JOptionPane.showMessageDialog(null, "Error al limpiar tabla plan de mejoramiento...");
        }



        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminaAccionesMejoraActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
    





        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     
mostrarTablaMejora(txtBuscar.getText());        
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txt4cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt4cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt4cActionPerformed

    private void txtPuntajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntajeActionPerformed
   
       
        
    }//GEN-LAST:event_txtPuntajeActionPerformed

    private void txtPrioridad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrioridad1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrioridad1ActionPerformed

    private void panelGraficoComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_panelGraficoComponentAdded
    

           
        
    }//GEN-LAST:event_panelGraficoComponentAdded

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed

        try {
            String path="src\\main\\java\\reportes\\reporte1.jasper";
            JasperPrint informe=JasperFillManager.fillReport(path,null,conn);
            JasperViewer ventanavisor=new JasperViewer(informe,false);
            ventanavisor.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // TODO add your handling code here:
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarrasActionPerformed
   

DefaultCategoryDataset dtsc=new DefaultCategoryDataset();
           
           for (int i = 0; i < tablaResultados.getRowCount(); i++) {
               dtsc.setValue(Double.parseDouble(tablaResultados.getValueAt(i,3).toString()),(tablaResultados.getValueAt(i, 0).toString()),(tablaResultados.getValueAt(i, 3).toString()));
               
           }
           JFreeChart ch=ChartFactory.createBarChart("Resumen Resultados","Puntaje Obtenido","Puntaje Base",dtsc,PlotOrientation.HORIZONTAL,true,true,false);
           ChartPanel cp=new ChartPanel(ch);
           add(cp);
           cp.setBounds(250, 250,450,300);
           //cp.setVisible(true);-

// TODO add your handling code here:
    }//GEN-LAST:event_btnBarrasActionPerformed

    private void btnTortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTortaActionPerformed
DefaultPieDataset dtsc=new DefaultPieDataset();
           
           for (int i = 0; i < tablaResultados.getRowCount(); i++) {
               dtsc.setValue((tablaResultados.getValueAt(i, 3).toString()),(Double.parseDouble(tablaResultados.getValueAt(i, 3).toString())));
               
           }
           JFreeChart ch=ChartFactory.createRingChart("Resumen Resultados",dtsc,true,false,false);
           ChartPanel cp=new ChartPanel(ch);
           add(cp);
           cp.setBounds(250, 250,450,300);
           //cp.setVisible(true);-    
        



// TODO add your handling code here:
    }//GEN-LAST:event_btnTortaActionPerformed

    private void txt2aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2aActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2aActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
//                new Ventana1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnLiderazgo;
    private javax.swing.JPanel Indicaciones;
    private javax.swing.JPanel JPanPlaM;
    private javax.swing.JPanel PnlAlianzas;
    private javax.swing.JPanel PnlEstrategia;
    private javax.swing.JPanel PnlLidr;
    private javax.swing.JPanel PnlPersonas;
    private javax.swing.JPanel PnlProcesos;
    private javax.swing.JPanel PnlResCli;
    private javax.swing.JPanel PnlResClv;
    private javax.swing.JPanel PnlResPer;
    private javax.swing.JPanel PnlResSoc;
    private javax.swing.JPanel PnlRsClav;
    public javax.swing.JTabbedPane TabLiderazgi;
    private static final javax.swing.JTextArea TextAreaLiderazgo = new javax.swing.JTextArea();
    private static final javax.swing.JTextArea TextAreaLiderazgo1 = new javax.swing.JTextArea();
    private javax.swing.JLabel based1;
    private javax.swing.JLabel based10;
    private javax.swing.JLabel based11;
    private javax.swing.JLabel based12;
    private javax.swing.JLabel based2;
    private javax.swing.JLabel based3;
    private javax.swing.JLabel based4;
    private javax.swing.JLabel based5;
    private javax.swing.JLabel based6;
    private javax.swing.JLabel based7;
    private javax.swing.JLabel based8;
    private javax.swing.JLabel based9;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBarras;
    private javax.swing.JButton btnCancelarM;
    private javax.swing.JButton btnEliminaAccionesMejora;
    private javax.swing.JButton btnReg9;
    private javax.swing.JButton btnRegistrarAccion;
    public javax.swing.JButton btnRegistrarC1;
    public javax.swing.JButton btnRegistrarC2;
    private javax.swing.JButton btnRegistrarC3;
    private javax.swing.JButton btnRegistrarC4;
    private javax.swing.JButton btnRegistrarC5;
    private javax.swing.JButton btnRegistrarC6;
    private javax.swing.JButton btnRegistrarC7;
    private javax.swing.JButton btnRegistrarC8;
    private javax.swing.JButton btnRegistrarC9;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnTorta;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanDiag;
    private javax.swing.JPanel jPanRep;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JLabel jlbl2a;
    private javax.swing.JLabel jlbl2c;
    private javax.swing.JScrollPane jscroll3;
    private javax.swing.JScrollPane jscroll4;
    private javax.swing.JScrollPane jscroll5;
    private javax.swing.JScrollPane jscroll6;
    private javax.swing.JScrollPane jscroll7;
    private javax.swing.JScrollPane jscroll8;
    private javax.swing.JScrollPane jscroll9;
    private javax.swing.JLabel lbl1a;
    private javax.swing.JLabel lbl1a1;
    private javax.swing.JLabel lbl1b;
    private javax.swing.JLabel lbl1b1;
    private javax.swing.JLabel lbl1c;
    private javax.swing.JLabel lbl1d;
    private javax.swing.JLabel lbl2a;
    private javax.swing.JLabel lbl2b;
    private javax.swing.JLabel lbl2c;
    private javax.swing.JLabel lbl3a;
    private javax.swing.JLabel lbl3a1;
    private javax.swing.JLabel lbl3a2;
    private javax.swing.JLabel lbl7a;
    private javax.swing.JLabel lbl7b;
    private javax.swing.JLabel lbl9a;
    private javax.swing.JLabel lbl9a1;
    private javax.swing.JLabel lbl9a2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblDesc2;
    private javax.swing.JLabel lblDesc3;
    private javax.swing.JLabel lblDescr3;
    private javax.swing.JLabel lblSub2;
    private javax.swing.JLabel lblprioridad1;
    private javax.swing.JLabel lblprioridad2;
    public javax.swing.JPanel panelGrafico;
    private javax.swing.JPanel tabInicio;
    private javax.swing.JTabbedPane tabPlanM;
    private javax.swing.JTable tablaMejora;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextArea textA4;
    private javax.swing.JTextArea textA5;
    private javax.swing.JTextArea textA6;
    private javax.swing.JTextArea textA7;
    private javax.swing.JTextArea textA8;
    private javax.swing.JTextArea textA9;
    public javax.swing.JTextField txt1a;
    public javax.swing.JTextField txt1b;
    public javax.swing.JTextField txt1c;
    public javax.swing.JTextField txt1d;
    public javax.swing.JTextField txt2a;
    public javax.swing.JTextField txt2b;
    public javax.swing.JTextField txt2c;
    private javax.swing.JTextField txt3a;
    private javax.swing.JTextField txt3b;
    private javax.swing.JTextField txt3c;
    private javax.swing.JTextField txt4a;
    private javax.swing.JTextField txt4b;
    private javax.swing.JTextField txt4c;
    private javax.swing.JTextField txt5a;
    private javax.swing.JTextField txt5b;
    private javax.swing.JTextField txt5c;
    private javax.swing.JTextField txt5d;
    private javax.swing.JTextField txt5e;
    private javax.swing.JTextField txt6a;
    private javax.swing.JTextField txt6b;
    private javax.swing.JTextField txt7a;
    private javax.swing.JTextField txt7b;
    private javax.swing.JTextField txt8a;
    private javax.swing.JTextField txt8b;
    private javax.swing.JTextField txt9a;
    private javax.swing.JTextField txt9b;
    private javax.swing.JTextField txt9c;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtC9a;
    private javax.swing.JTextField txtMadurez;
    private javax.swing.JTextField txtNa;
    private javax.swing.JTextField txtPrioridad1;
    private javax.swing.JTextField txtPrioridad2;
    private javax.swing.JTextField txtPuntaje;
    private javax.swing.JTextField txtaccion;
    private javax.swing.JTextField txtmejorPuntuado;
    private javax.swing.JTextField txtplazo;
    private javax.swing.JTextField txtpresupuesto;
    private javax.swing.JTextField txtresp;
    private javax.swing.JTextField txtsub;
    // End of variables declaration//GEN-END:variables

Conexion con=new Conexion();
Connection conn=con.conexion();
//conn.close();
}
