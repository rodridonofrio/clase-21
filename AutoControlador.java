package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Auto;
import model.AutoDao;
import view.Pantalla;
import model.MarcaDao;
import model.ModeloDao;
import model.VersionDao;

public class AutoControlador implements ActionListener, MouseListener, KeyListener {
    private Auto auto;
    private AutoDao autoDao;
    private Pantalla panta;

    DefaultTableModel model = new DefaultTableModel();

    public AutoControlador(Auto auto, AutoDao autoDao, Pantalla panta) {
        this.auto = auto;
        this.autoDao = autoDao;
        this.panta = panta;
        
        //Botón de registrar auto
        this.panta.btn_AgregarAuto.addActionListener(this);
        //Botón de modificar auto
        this.panta.btn_ModificarAuto.addActionListener(this);
        //Botón de borrar auto
        this.panta.btn_BorrarAuto.addActionListener(this);
        //Botón de limpiar
        this.panta.btn_LimpiarAuto.addActionListener(this);
        
        //Listado de auto
        this.panta.tb_Auto.addMouseListener(this);
        this.panta.txt_BuscarAuto.addKeyListener(this);
              
        listarAutos(); 
        
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panta.btn_AgregarAuto){
            //verifica si el campo título está vacío
             //panta.radiobutton_combustible.getText().equals("")|| panta.radiobuttonCondicion.getText().equals("")
            if(panta.cmb_MarcaAuto.getSelectedItem().equals("")|| panta.cmb_ModeloAuto.getSelectedItem().equals("")|| panta.cmb_VersionAuto.getSelectedItem().equals("")|| panta.txt_Año.getText().equals("")|| panta.txt_Precio.getText().equals("")|| panta.txt_Kilometros.getText().equals("")|| panta.txt_Puertas.getText().equals("")||panta.cmb_Tipo.getSelectedItem().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                if(Integer.parseInt(panta.txt_Año.getText())<1970 ||Integer.parseInt(panta.txt_Año.getText())>2024){
                JOptionPane.showMessageDialog(null, "El año ingresado debe ser de 1970 a 2024");
                }else{
                   if (Integer.parseInt(panta.txt_Puertas.getText())<3||Integer.parseInt(panta.txt_Puertas.getText())>5){
                       JOptionPane.showMessageDialog(null, "La cantidad de puertas debe ser de 3 a 5");
                   }else{
                       if (Integer.parseInt(panta.txt_Kilometros.getText())<0||Integer.parseInt(panta.txt_Kilometros.getText())>5000000){  
                   JOptionPane.showMessageDialog(null, "Los km ingresados deben ser de 0 a 5000000");
                       }else{
                           
                           
                       
// AQUI SE GUARDA EN LA VARIABLE E 
                //Realiza el agregado
                MarcaDao marcaDao = new MarcaDao();         //CREA UN OBJETO PARA PODER USAR LOS METODOS DE MARCADAO
                int idMarca = marcaDao.buscarIdMarca(panta.cmb_MarcaAuto.getSelectedItem().toString());     //AGARRA LO QUE TIENE DENTRO DEL COMBOBOX DE MARCA AUTO, LO PASA A STRING, ESE STRING LO MANDA AL METODO BUSCARIdMARCA, EL CUAL RETORNA UN ID, Y LO GUARDA EN LA VARIABLE IdMARCA, PARA LUEGO USARLO
                ModeloDao modeloDao = new ModeloDao();
                int idModelo = modeloDao.buscarIdModelo(panta.cmb_ModeloAuto.getSelectedItem().toString());
                VersionDao versionDao = new VersionDao();
                int idVersion = versionDao.buscarIdVersion(panta.cmb_VersionAuto.getSelectedItem().toString());

                auto.setAño(Integer.parseInt(panta.txt_Año.getText()));
                auto.setPrecio(Double.parseDouble(panta.txt_Precio.getText()));
                auto.setKilometraje(Integer.parseInt(panta.txt_Kilometros.getText()));      //ESTOS ASIGNAN LOS VALORES A LOS ATRIBUTOS DE AUTO
                auto.setPuertas(Integer.parseInt(panta.txt_Puertas.getText()));
                auto.setTipo(panta.cmb_Tipo.getSelectedItem().toString());
                //no se como va combustible y condicion
                 if (panta.radiobutton_Nuevo.isSelected()){                 //<<<<<<<<<<<<
                    auto.setCondicion("Nuevo");
                }else{
                    auto.setCondicion("Usado");
                }
                
                if(panta.radiobutton_Diesel.isSelected()){                  //ESTA ZONA VERIFICA SI NUEVO ESTA SELECCIONADO, Y SI LO ESTA, PONE UN 1 EN EL ATRIBUTO CONDICION QUE ESTA EN AUTO
                    auto.setCombustible("Diesel");
                }else if(panta.radiobutton_Nafta.isSelected()){             //LUEGO CON MULTIPLES IF VERIFICA QUE BOTON DE COMBUSTIBLE ESTA PRESIONADO Y EN BASE A ESO COLOCA VALORES EN EL ATRIBUTO COMBUSTIBLE DE LA CLASE AUTO, EN ESTE CASO SOLO SERA VALIDO 1 VALOR
                    auto.setCombustible("Nafta");
                }else if(panta.radiobutton_GNC.isSelected()){
                     auto.setCombustible("GNC");
                }else if(panta.radiobutton_Electrico.isSelected()){
                    auto.setCombustible("Electrico");                                 //<<<<<<<<<<<<<<
                }
                auto.setIdmarca(idMarca);
                auto.setIdmodelo(idModelo);
                auto.setIdversion(idVersion);
                
                if(autoDao.agregarAuto(auto)){
                    limpiarTabla();
                    limpiarCampos();
                    listarAutos();
                    JOptionPane.showMessageDialog(null, "Se agregó el auto");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al agregar el auto");
                }
            }
            }
            }
            }
        }else if(e.getSource() == panta.btn_ModificarAuto){
            //verifica si el campo id está vacío
            if(panta.txt_IdAuto.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro desde la tabla");
            }else{
                //Realiza la modificación
                MarcaDao autorDao = new MarcaDao();
                int idMarca = autorDao.buscarIdMarca(panta.cmb_MarcaAuto.getSelectedItem().toString());
                ModeloDao modeloDao = new ModeloDao();
                int idModelo = modeloDao.buscarIdModelo(panta.cmb_ModeloAuto.getSelectedItem().toString());
                VersionDao versionDao = new VersionDao();
                int idVersion = versionDao.buscarIdVersion(panta.cmb_VersionAuto.getSelectedItem().toString());

                auto.setIdauto(Integer.parseInt(panta.txt_IdAuto.getText()));
                auto.setAño(Integer.parseInt(panta.txt_Año.getText()));
                auto.setPrecio(Double.parseDouble(panta.txt_Precio.getText()));
                auto.setKilometraje(Integer.parseInt(panta.txt_Kilometros.getText()));
                auto.setPuertas(Integer.parseInt(panta.txt_Puertas.getText()));
                auto.setTipo(panta.cmb_Tipo.getSelectedItem().toString());
                
                 if (panta.radiobutton_Nuevo.isSelected()){                 //<<<<<<<<<<<<
                    auto.setCondicion("Nuevo");
                }else{
                    auto.setCondicion("Usado");
                }
                
                if(panta.radiobutton_Diesel.isSelected()){                  //ESTA ZONA VERIFICA SI NUEVO ESTA SELECCIONADO, Y SI LO ESTA, PONE UN 1 EN EL ATRIBUTO CONDICION QUE ESTA EN AUTO
                    auto.setCombustible("Diesel");
                }else if(panta.radiobutton_Nafta.isSelected()){             //LUEGO CON MULTIPLES IF VERIFICA QUE BOTON DE COMBUSTIBLE ESTA PRESIONADO Y EN BASE A ESO COLOCA VALORES EN EL ATRIBUTO COMBUSTIBLE DE LA CLASE AUTO, EN ESTE CASO SOLO SERA VALIDO 1 VALOR
                    auto.setCombustible("Nafta");
                }else if(panta.radiobutton_GNC.isSelected()){
                     auto.setCombustible("GNC");
                }else if(panta.radiobutton_Electrico.isSelected()){
                    auto.setCombustible("Electrico");                                 //<<<<<<<<<<<<<<
                }
                
                auto.setIdmarca(idMarca);
                auto.setIdmodelo(idModelo);
                auto.setIdversion(idVersion);
                if(autoDao.modificarAuto(auto)){
                    limpiarTabla();
                    limpiarCampos();
                    listarAutos();
                    JOptionPane.showMessageDialog(null, "Se modificó el auto");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el auto");
                }
            }
        }else if(e.getSource() == panta.btn_BorrarAuto){
            //verifica si el campo id está vacío
            if(panta.txt_IdAuto.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro desde la tabla");
            }else{
                //Realiza el borrado
                int id = Integer.parseInt(panta.txt_IdAuto.getText());
                if(autoDao.borrarAuto(id)){
                    limpiarTabla();
                    limpiarCampos();
                    listarAutos();
                    JOptionPane.showMessageDialog(null, "Se eliminó el auto");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al eliminar el auto");
                }
            }
        }else if(e.getSource() == panta.btn_LimpiarAuto){
                limpiarTabla();
                limpiarCampos();
                listarAutos();    
                panta.btn_AgregarAuto.setEnabled(true);
        }    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == panta.tb_Auto){
            int row = panta.tb_Auto.rowAtPoint(e.getPoint());
            panta.txt_IdAuto.setText(panta.tb_Auto.getValueAt(row,0).toString());
            panta.cmb_MarcaAuto.setSelectedItem(panta.tb_Auto.getValueAt(row,1).toString());
            panta.cmb_ModeloAuto.setSelectedItem(panta.tb_Auto.getValueAt(row,2).toString());
            panta.cmb_VersionAuto.setSelectedItem(panta.tb_Auto.getValueAt(row,3).toString());
             panta.txt_Año.setText(panta.tb_Auto.getValueAt(row,4).toString());
             panta.txt_Precio.setText(panta.tb_Auto.getValueAt(row,5).toString());
             panta.txt_Kilometros.setText(panta.tb_Auto.getValueAt(row,6).toString());
             panta.txt_Puertas.setText(panta.tb_Auto.getValueAt(row,7).toString());
             //no se como poner el combustible y condicion
             
             String combustible = panta.tb_Auto.getValueAt(row,8).toString();             
                if(combustible.equals("Diesel")){
                     panta.radiobutton_Diesel.setSelected(true);
                }else if(combustible.equals("Nafta")){
                            panta.radiobutton_Nafta.setSelected(true);
                      }else if(combustible.equals("GNC")){
                                  panta.radiobutton_GNC.setSelected(true);
                            }else{
                                  panta.radiobutton_Electrico.setSelected(true);
                            }
             
             String condicion = panta.tb_Auto.getValueAt(row,9).toString();
                if (condicion.equals("Nuevo")){
                    panta.radiobutton_Nuevo.setSelected(true);
                }else{
                    panta.radiobutton_Usado.setSelected(true);
                }
                
             panta.cmb_Tipo.setSelectedItem(panta.tb_Auto.getValueAt(row,10).toString());

            

            //Deshabilitar
            panta.btn_AgregarAuto.setEnabled(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == panta.txt_BuscarAuto){
                listarAutos();
    }
        if(e.getSource() == panta.txt_Kilometros){
            if(Integer.parseInt(panta.txt_Kilometros.getText())==0){
                panta.radiobutton_Usado.setEnabled(false);
                panta.radiobutton_Nuevo.setEnabled(true);
            }  
        }
    }
    //Listar todos los autores
    public void listarAutos(){
        if(panta.txt_BuscarAuto.getText().equals("")){   
        List<Auto> list = autoDao.listarAuto();
        model = (DefaultTableModel) panta.tb_Auto.getModel();
        Object[] row = new Object[11];
        limpiarTabla();
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getIdauto();
            row[1] = list.get(i).getNombremarca();
            row[2] = list.get(i).getNombremodelo();
            row[3] = list.get(i).getNombreversion();
            row[4] = list.get(i).getAño();
            row[5] = list.get(i).getPrecio();
            row[6] = list.get(i).getKilometraje();
            row[7] = list.get(i).getPuertas();
            row[8] = list.get(i).getCombustible();
            row[9] = list.get(i).getCondicion();
            row[10] = list.get(i).getTipo();
            
            model.addRow(row);
            panta.cmb_Tipo.removeAllItems();
            panta.cmb_Tipo.addItem("Auto");
            panta.cmb_Tipo.addItem("Camioneta");
            panta.cmb_Tipo.addItem("Utilitario");
        }
    }else {
            String titulo = panta.txt_BuscarAuto.getText();
            List<Auto> list1 = autoDao.listarAutoTitulo(titulo);
               model = (DefaultTableModel) panta.tb_Auto.getModel();
               Object[] row = new Object[5];
               limpiarTabla();
                   for (int i=0;i<list1.size(); i++){
            row[0] = list1.get(i).getIdauto();
            row[2] = list1.get(i).getNombremarca();
            row[3] = list1.get(i).getNombremodelo();
            row[4] = list1.get(i).getNombreversion();
            row[5] = list1.get(i).getAño();
            row[6] = list1.get(i).getPrecio();
            row[7] = list1.get(i).getKilometraje();
            row[8] = list1.get(i).getPuertas();
            row[9] = list1.get(i).getCombustible();
            row[10] = list1.get(i).getCondicion();
            row[11] = list1.get(i).getTipo();
                    model.addRow(row);
        }
    
    
    }
    }

    //Limpiar la tabla
    public void limpiarTabla(){
        for (int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
    //Limpiar los campos
    public void limpiarCampos(){
        panta.txt_IdAuto.setText("");
        panta.txt_Año.setText("");
        panta.txt_Precio.setText("");
        panta.txt_Kilometros.setText("");
        panta.txt_Puertas.setText("");
        panta.cmb_Tipo.setSelectedItem("");
        
        //falta combustible y condicion
        
        panta.cmb_MarcaAuto.setSelectedItem("");
        panta.cmb_ModeloAuto.setSelectedItem("");
        panta.cmb_VersionAuto.setSelectedItem("");
    }
    
}
