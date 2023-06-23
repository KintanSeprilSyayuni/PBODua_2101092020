/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kintan.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kintan.dao.AnggotaDao;
import kintan.dao.AnggotaDaoImpl;
import kintan.dao.Koneksi;
import kintan.model.Anggota;
import kintan.view.FormAnggota;

/**
 *
 * @author User
 */
public class AnggotaController {
    private FormAnggota formAnggota;
    private Anggota anggota;
    private AnggotaDao anggotaDao;
    private Connection con;
    private Koneksi koneksi;
    
    public AnggotaController(FormAnggota formanggota) {
        try {
            this.formAnggota = formanggota;
            anggotaDao = new AnggotaDaoImpl();
            con = new Koneksi().getKoneksi();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public void clearForm(){
        formAnggota.getTxtkodeanggota().setText("");
        formAnggota.getTxtnamaanggota().setText("");
        formAnggota.getTxtalamat().setText("");
    }
   
       public void isiCboJenisKelamin(){
        formAnggota.getCbojeniskelamin().removeAllItems();
        formAnggota.getCbojeniskelamin().addItem("L");
        formAnggota.getCbojeniskelamin().addItem("P");
    }
   
   public void insert(){
        try {
            anggota = new Anggota();
            anggota.setKodeanggota(formAnggota.getTxtkodeanggota().getText());
            anggota.setNamaanggota(
                    formAnggota.getTxtnamaanggota().getText());
            anggota.setAlamat(
                    formAnggota.getTxtalamat().getText());
            anggota.setJeniskelamin (
                    formAnggota.getCbojeniskelamin().getSelectedItem().toString());
            anggotaDao.insert(con,anggota);
            JOptionPane.showMessageDialog(formAnggota, "Entri Ok");
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
   
   public void update(){
       
        try {
            anggota = new Anggota();
            anggota.setKodeanggota(formAnggota.getTxtkodeanggota().getText());
            anggota.setNamaanggota(
                    formAnggota.getTxtnamaanggota().getText());
            anggota.setAlamat(
                    formAnggota.getTxtalamat().getText());
            anggota.setJeniskelamin (
                    formAnggota.getCbojeniskelamin().getSelectedItem().toString());
            anggotaDao.update(con,anggota);
            JOptionPane.showMessageDialog(formAnggota, "Update Ok");
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
   public void delete(){
     
        try {
            anggotaDao.delete(con, anggota);
            JOptionPane.showMessageDialog(formAnggota, "Delete Ok");
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
   
   public void tabelKlik(){
        try {
            String kode = formAnggota.getTblanggota().getValueAt(formAnggota.getTblanggota().getSelectedRow(),0).toString();
            anggota = anggotaDao.getAnggota(con, kode);
            formAnggota.getTxtkodeanggota().setText(anggota.getKodeanggota());
            formAnggota.getTxtnamaanggota().setText(anggota.getNamaanggota());
            formAnggota.getTxtalamat().setText(anggota.getAlamat());
            formAnggota.getCbojeniskelamin().setSelectedItem(anggota.getJeniskelamin());
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
     
     public void cari(){
        try {
            String kode = formAnggota.getTxtkodeanggota().getText();
            anggota = anggotaDao.getAnggota(con,kode);
            if(anggota != null){
                formAnggota.getTxtnamaanggota().setText(anggota.getNamaanggota());
                formAnggota.getTxtalamat().setText(anggota.getAlamat());
                formAnggota.getCbojeniskelamin().setSelectedItem(anggota.getJeniskelamin());
            }else{
                JOptionPane.showMessageDialog(formAnggota, "Data Tidak Ada");
            }
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
   
   public void tampil(){
        try {
            DefaultTableModel tabel = (DefaultTableModel) formAnggota.getTblanggota().getModel();
            tabel.setRowCount(0);
            List<Anggota> list = anggotaDao.getAllAnggota(con);
            for (Anggota anggota1 : list) {
                Object[] row = {
                    anggota1.getKodeanggota(),
                    anggota1.getNamaanggota(),
                    anggota1.getAlamat(),
                    anggota1.getJeniskelamin()
                };
                tabel.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
