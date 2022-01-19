
package pruebas;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Testgrafico {
    
    
    public static void main(String[] args) 
    {
        //Crear un dataset
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Categría 1",43.2);
        data.setValue("Categría 2",27.9);
        data.setValue("Categría 3",79.5);
        //Creamos un Chart
        JFreeChart chart = ChartFactory.createPieChart(
                           "Ejemplo de JFreeChart", //Títrulo del gráfico
                           data,
                           true,//Leyenda
                           true,//ToolTips
                           true);
        //Creamos una especie de frame y mostramos el JFreeChart en él
        //Este constructor nos pide el título del Chart y el chart creado
        ChartFrame frame=new ChartFrame("Primer Chart para javax0711",chart);
        frame.pack();
        frame.setVisible(true);
    }
}
    

