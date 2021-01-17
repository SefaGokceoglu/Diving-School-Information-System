/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.packages;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Integer.parseInt;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public final class UI extends javax.swing.JFrame {
    Statement st=null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    Connection connDbc = null;
    DatabaseConnection dbc = new DatabaseConnection();
    
    
    public UI() {
        initComponents();
        connDbc = dbc.databaseConn();
        CreateSequence();
        UyelerVeriAl();
        ÖğretmenlerVeriAl();
        viewSınıflarVeriAl();
        DalışYeriVeriAl();
        Fonksiyon1Olustur();
        Fonksiyon2Olustur();
        Fonksiyon3Olustur();
        Trigger1Olustur();
        Trigger2Olustur();
        Trigger3Olustur();
        Trigger4Olustur();
        Ogr_Sayisi_TriggerOlustur();
        
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    String dropseq ="DROP SEQUENCE seq";
                    pst = connDbc.prepareStatement(dropseq);
                    pst.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                String droptrig1 ="DROP TRIGGER T_yıldızuyum1 ON sınıflar;";
                try {
                pst = connDbc.prepareStatement(droptrig1);
                pst.execute();
                } catch (SQLException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String droptrig2 ="DROP TRIGGER T_yıldızuyum2 ON ogretmenler;";
                try {
                pst = connDbc.prepareStatement(droptrig2);
                pst.execute();
                } catch (SQLException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String droptrig3 ="DROP TRIGGER TRIG1 ON uyeler ;\n";
                try {
                pst = connDbc.prepareStatement(droptrig3);
                pst.execute();
                } catch (SQLException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String droptrig4 ="DROP TRIGGER TRIG2 ON sınıflar ;\n";
                try {
                pst = connDbc.prepareStatement(droptrig4);
                pst.execute();
                } catch (SQLException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                String droptrig5 ="DROP TRIGGER OGRSAYISI_TRIG ON uyeler \n";
                try {
                pst = connDbc.prepareStatement(droptrig5);
                pst.execute();
                } catch (SQLException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }  
        );
    }
    
    public void UyelerVeriAl(){
        try {
            String sqlSelect="select * from uyeler";
            pst = connDbc.prepareStatement(sqlSelect);
            rst = pst.executeQuery();
            while(rst.next()){  
                
                String fname =rst.getString("fname");
                String lname =rst.getString("lname");
                String KayıtID =rst.getString("kayıtid");                      
                int    Boy =rst.getInt("boy");
                Date bdate =rst.getDate("bdate");
                String sid = rst.getString("sid");
                String srapor;
                if(rst.getBoolean("srapor")){
                    srapor="Uygun";
                }else{
                    srapor="Uygun Değil";
                }
                
                
                DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
                Object[] obj = {fname ,lname ,KayıtID ,Boy ,bdate ,sid ,srapor};
                dftable.addRow(obj);
                
            }
            System.out.println("Uyeler tabloya alındı");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void Fonksiyon1Olustur(){
        String Function = "CREATE OR REPLACE FUNCTION seviye_goster(seviye varchar(12))\n" +
                          "RETURNS text[] AS $$\n" +
                          "DECLARE\n" +
                          "    ogr_cursor CURSOR FOR SELECT * \n" +
                          "                          FROM ogretmenler\n" +
                          "                          WHERE seviye=oyıldız;\n" +
                          "    idler text[];\n" +
                          "    i integer;\n" +
                          "BEGIN\n" +
                          "    i := 1;\n" +
                          "    FOR ogr_record IN ogr_cursor LOOP\n" +
                          "		idler[i] := ogr_record;\n" +
                          "    i := i + 1;\n" +
                          "    END LOOP;\n" +
                          "    RETURN idler;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';";
               
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(Function);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Fonksiyon1 Oluşturuldu!");
    }
      
    public void Fonksiyon2Olustur(){
        String Function = "CREATE OR REPLACE FUNCTION seviye_goster1(seviye varchar(12))\n" +
                          "RETURNS text[] AS $$\n" +
                          "DECLARE\n" +
                          "    dalısyeri_cursor CURSOR FOR SELECT * \n" +
                          "                          FROM dalısyeri\n" +
                          "                          WHERE seviye=hyıldız;\n" +
                          "    dalısyerleri text[];\n" +
                          "    i integer;\n" +
                          "BEGIN\n" +
                          "    i := 1;\n" +
                          "    FOR dalısyeri_record IN dalısyeri_cursor LOOP\n" +
                          "		dalısyerleri[i] := dalısyeri_record;\n" +
                          "    i := i + 1;\n" +
                          "    END LOOP;\n" +
                          "    RETURN dalısyerleri;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';";
               
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(Function);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Fonksiyon2 Oluşturuldu!");
    }
    
    public void Fonksiyon3Olustur(){
        String Function = "CREATE OR REPLACE FUNCTION sınıfbulmafonk (seviye varchar(12))\n" +
                          "RETURNS text[] AS $$\n" +
                          "DECLARE\n" +
                          "sınıf_cursor CURSOR FOR SELECT sid,yıldız\n" +
                          "FROM uyeler\n" +
                          "left join sınıflar \n" +
                          "on sınıfid = sid\n" +
                          "left join ogretmenler\n" +
                          "on dalısegid=personalid\n" +
                          "where dalısegid != '' AND yıldız = seviye\n" +
                          "GROUP BY sid,kapasite,yıldız,dalısegid\n" +
                          "HAVING count(*)<kapasite;\n" +
                          "sınıf text[];\n" +
                          "i integer;\n" +
                          "BEGIN\n" +
                          "i := 1;\n" +
                          "FOR sınıf_record IN sınıf_cursor LOOP\n" +
                          "sınıf[i] := sınıf_record;\n" +
                          "i := i + 1;\n" +
                          "END LOOP;\n" +
                          "RETURN sınıf;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';";
               
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(Function);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Fonksiyon3 Oluşturuldu!");
    }
    
    public void CreateSequence(){
        
        try {
            String maxsınıfid = "SELECT max(sınıfid) FROM sınıflar";
            pst = connDbc.prepareStatement(maxsınıfid);
            rst = pst.executeQuery();
            rst.next();
            String max = rst.getString("max");
            int nmax = parseInt(max) + 1;
            String seq = "CREATE SEQUENCE seq "
                    + " minvalue " + nmax +
                    " increment by 1";
            System.out.println(seq);
            pst = connDbc.prepareStatement(seq);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Sequence Inserted!");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public void ÖğretmenlerVeriAl(){
        try {
            String sqlSelect="select * from ogretmenler";
            pst = connDbc.prepareStatement(sqlSelect);
            rst = pst.executeQuery();
            while(rst.next()){  
                
                String fname =rst.getString("fname");
                String lname =rst.getString("lname");
                String PersonalID =rst.getString("personalid");
                int    ogrenciSayisi =rst.getInt("ogrsayisi");
                String Yıldız =rst.getString("oyıldız");
                String havuzkey = rst.getString("havuzkey");
                
                DefaultTableModel dftable = (DefaultTableModel) ÖğretmenlerTable.getModel();
                Object[] obj = {fname ,lname ,PersonalID ,ogrenciSayisi ,Yıldız ,havuzkey};
                dftable.addRow(obj);
                
            }
            System.out.println("Ogretmenler tabloya alındı");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    public void viewSınıflarVeriAl(){
        try {
            String createview="CREATE VIEW v_sınıflar AS SELECT * FROM sınıflar ";
            pst = connDbc.prepareStatement(createview);
            pst.execute();
            
            String Selectview="SELECT * FROM v_sınıflar";
            pst = connDbc.prepareStatement(Selectview);
            rst =pst.executeQuery();
            while(rst.next()){  
                
                String sınıfid =rst.getString("sınıfid");          
                int    kapasite =rst.getInt("kapasite");
                String Yıldız =rst.getString("yıldız");
                String dalısegid = rst.getString("dalısegid");
                
                DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
                Object[] obj = {sınıfid ,kapasite ,Yıldız ,dalısegid};
                dftable.addRow(obj);
            }
            String dropview ="DROP VIEW v_sınıflar";
            pst = connDbc.prepareStatement(dropview);
            pst.execute();
            System.out.println("Sınıflar tabloya alındı");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void DalışYeriVeriAl(){
        try {
            String sqlSelect="select * from dalısyeri";
            pst = connDbc.prepareStatement(sqlSelect);
            rst = pst.executeQuery();
            while(rst.next()){  
                
                String dname =rst.getString("dname");          
                String havuzid =rst.getString("havuzid");
                int    derinlik =rst.getInt("derinlik");
                String çeşitlilik = rst.getString("çeşitlilik");
                String Yıldız = rst.getString("hyıldız");
                
                DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
                Object[] obj = {dname ,havuzid ,derinlik ,çeşitlilik ,Yıldız};
                dftable.addRow(obj);
                
            }   
            System.out.println("Dalışyerleri tabloya alındı");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public void Trigger1Olustur(){
         String trigger1 ="CREATE OR REPLACE FUNCTION sınıf_ogretmen_uyum()\n" +
                          "RETURNS TRIGGER AS $$\n" +
                          "BEGIN\n" +
                          "    IF (NEW.dalısegid ISNULL) THEN\n"   +
                          "    RETURN NEW;\n"   +
                          "    END IF;\n"   +
                          "    IF ( NEW.yıldız IN (SELECT oyıldız FROM ogretmenler WHERE NEW.dalısegid = personalid))THEN\n" +
                          "        RETURN NEW;\n" +
                          "    ELSE \n" +
                          "		RAISE EXCEPTION 'Seviyeler uyumsuz lütfen başka bir hoca atayınız !';\n" +
                          "        RETURN NULL;\n" +
                          "    END IF;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';\n" +
                          "\n" +
                          "CREATE TRIGGER T_yıldızuyum1\n" +
                          "BEFORE INSERT OR UPDATE\n" +
                          "ON sınıflar\n" +
                          "FOR EACH ROW EXECUTE PROCEDURE sınıf_ogretmen_uyum();";     
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(trigger1);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Trigger1 Oluşturuldu!");
    }
    
    public void Trigger2Olustur(){
         String trigger2 ="CREATE OR REPLACE FUNCTION ogretmen_dalısyeri_uyum()\n" +
                          "RETURNS TRIGGER AS $$\n" +
                          "BEGIN\n" +
                          "    IF ( NEW.oyıldız IN (SELECT hyıldız FROM dalısyeri WHERE NEW.havuzkey = havuzid))THEN\n" +
                          "        RETURN NEW;\n" +
                          "    ELSE\n" +
                          "		RAISE EXCEPTION 'Seviyeler uyumsuz lütfen başka bir DAlış Yeri seçiniz !';\n" +
                          "        RETURN NULL;\n" +
                          "    END IF;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';\n" +
                          "\n" +
                          "CREATE TRIGGER T_yıldızuyum2\n" +
                          "BEFORE INSERT OR UPDATE\n" +
                          "ON ogretmenler\n" +
                          "FOR EACH ROW EXECUTE PROCEDURE ogretmen_dalısyeri_uyum();";     
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(trigger2);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Trigger2 Oluşturuldu!");
    }

    public void Trigger3Olustur(){
        String trigger3 ="CREATE OR REPLACE FUNCTION UYE_EKLEME_SINIF()\n" +
                         "RETURNS TRIGGER AS $$\n" +
                         "DECLARE \n" +
                         "	deger int;\n" +
                         "	kap int;\n" +
                         "      sınıf text[];\n" +
                         "BEGIN \n" +
                         "      SELECT sid INTO sınıf FROM uyeler WHERE kayıtid=NEW.kayıtid;\n" +
                         "    IF (sınıf = null) THEN \n"  +
                         "      RETURN NEW;\n"  +
                         "    END IF;\n"    +
                         "	SELECT count(*) INTO deger FROM uyeler WHERE sid=NEW.sid;\n" +
                         "	SELECT kapasite INTO kap FROM sınıflar WHERE sınıfid=NEW.sid;\n" +
                         "    IF (deger < kap) THEN\n" +
                         "		RETURN NEW;\n" +
                         "		\n" +
                         "    ELSE \n" +
                         "		RAISE EXCEPTION ' Sınıf kapasitesi doludur lütfen başka bir sınıf seçiniz !';\n" +
                         "        RETURN NULL;\n" +
                         "    END IF;\n" +
                         "END;\n" +
                         "$$ LANGUAGE 'plpgsql';\n" +
                         "\n" +
                         "\n" +
                         "CREATE TRIGGER TRIG1\n" +
                         "BEFORE INSERT OR UPDATE\n" +
                         "ON uyeler\n" +
                         "FOR EACH ROW EXECUTE PROCEDURE UYE_EKLEME_SINIF();";     
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(trigger3);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Trigger3 Oluşturuldu!");
               
    }
       
    public void Trigger4Olustur(){
        String trigger4 ="CREATE OR REPLACE FUNCTION SINIF_MİNMAX_SINIF()\n" +
                         "RETURNS TRIGGER AS $$\n" +
                         "DECLARE \n" +
                         "	deger int;\n" +
                         "BEGIN\n" +
                         "	SELECT count(*) into deger FROM sınıflar ;\n" +
                         "	IF(TG_OP ='DELETE')THEN\n" +
                         "		IF(deger > 3) THEN\n" +
                         "			RETURN OLD;\n" +
                         "		ELSE\n" +
                         "			RAISE EXCEPTION ' Dalış Okulundaki Sınıf Sayısı 3 ve 16 Arasında Olmalıdır!';\n" +
                         "			RETURN NULL;\n" +
                         "		END IF;	\n" +
                         "	END IF;	\n" +
                         "    IF (TG_OP ='INSERT' ) THEN\n" +
                         "		IF(deger < 16) THEN \n" +
                         "			RETURN NEW;\n" +
                         "		ELSE\n" +
                         "                      RAISE EXCEPTION ' Dalış Okulundaki Sınıf Sayısı 3 ve 16 Arasında Olmalıdır!';\n" +
                         "			RETURN NULL;\n" +
                         "		END IF;	\n" +
                         "	END IF;	\n" +
                         "END;\n" +
                         "$$ LANGUAGE 'plpgsql';\n" +
                         "\n" +
                         "\n" +
                         "CREATE TRIGGER TRIG2\n" +
                         "BEFORE DELETE OR INSERT\n" +
                         "ON sınıflar\n" +
                         "FOR EACH ROW EXECUTE PROCEDURE SINIF_MİNMAX_SINIF()";     
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(trigger4);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Trigger4 Oluşturuldu!");
               
    }
    
    public void Ogr_Sayisi_TriggerOlustur(){
         String trigger5 ="CREATE OR REPLACE FUNCTION OGR_SAYISI_TRIG_F()\n" +
                          "RETURNS TRIGGER AS $$\n" +
                          "DECLARE \n" +
                          "	id char(9);\n" +         
                          "	eskiid char(9);\n" +
                          "BEGIN\n" +
                          "	IF (TG_OP ='INSERT') THEN\n" +
                          "		SELECT dalısegid into id FROM sınıflar,uyeler WHERE NEW.sid=sınıfid;\n" +
                          "		UPDATE ogretmenler SET ogrsayisi = ogrsayisi + 1 WHERE personalid = id;\n" +
                          "             RETURN NULL;\n                            " +
                          "    END IF;\n" +
                          "	IF (TG_OP ='UPDATE') THEN\n" +
                          "		SELECT dalısegid into id FROM sınıflar,uyeler WHERE NEW.sid=sınıfid;\n" +
                          "		SELECT dalısegid into eskiid FROM sınıflar,uyeler WHERE OLD.sid=sınıfid;\n" +
                          "		UPDATE ogretmenler SET ogrsayisi = ogrsayisi + 1 WHERE personalid = id;\n" +
                          "		UPDATE ogretmenler SET ogrsayisi = ogrsayisi - 1 WHERE personalid = eskiid;\n" +
                          "             RETURN NULL;\n                            " +
                          "    END IF;\n" +
                          "	IF (TG_OP ='DELETE') THEN\n" +
                          "		SELECT dalısegid into id FROM sınıflar,uyeler WHERE OLD.sid=sınıfid;\n" +
                          "		UPDATE ogretmenler SET ogrsayisi = ogrsayisi - 1 WHERE personalid = id;\n" +
                          "             RETURN NULL;\n                            " +
                          "    END IF;\n" +
                          "END;\n" +
                          "$$ LANGUAGE 'plpgsql';\n" +
                          "CREATE TRIGGER OGRSAYISI_TRIG\n" +
                          "AFTER DELETE OR UPDATE OR INSERT\n" +
                          "ON uyeler\n" +
                          "FOR EACH ROW EXECUTE PROCEDURE OGR_SAYISI_TRIG_F()";     
        try {
            st=connDbc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            st.execute(trigger5);
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("OgrSayısıTrigger Oluşturuldu!");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        AnasayfaPanel = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        ÜyelerimizTab = new javax.swing.JTabbedPane();
        UyelerBilgilerPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ÜyelerTable = new javax.swing.JTable();
        ÜyeBulma = new javax.swing.JButton();
        jTextField61 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jTextField66 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTextField85 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel101 = new javax.swing.JLabel();
        TümÜyeleriGösterme = new javax.swing.JButton();
        UyelerİşlemlerPanel = new javax.swing.JPanel();
        ÜyeEkle = new javax.swing.JButton();
        jTextField67 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        ÜyeSilme = new javax.swing.JButton();
        jTextField72 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jTextField76 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        ÜyeGüncelleme = new javax.swing.JButton();
        jTextField77 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel93 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel94 = new javax.swing.JLabel();
        jTextField82 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTextField84 = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jTextField86 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jTextField87 = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jTextField88 = new javax.swing.JTextField();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jTextField90 = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jTextField91 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jTextField93 = new javax.swing.JTextField();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jTextField94 = new javax.swing.JTextField();
        jCheckBox11 = new javax.swing.JCheckBox();
        jLabel121 = new javax.swing.JLabel();
        jCheckBox12 = new javax.swing.JCheckBox();
        jSeparator15 = new javax.swing.JSeparator();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel125 = new javax.swing.JLabel();
        UygunSınıfBulma = new javax.swing.JButton();
        jLabel122 = new javax.swing.JLabel();
        ÖğretmenlerTab = new javax.swing.JTabbedPane();
        ÖğretmenlerBilgilerPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ÖğretmenlerTable = new javax.swing.JTable();
        ÖğretmenBulma = new javax.swing.JButton();
        jTextField21 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        TümÖğretmenleriGösterme = new javax.swing.JButton();
        ÖğretmenlerİşlemlerPanel = new javax.swing.JPanel();
        ÖğretmenEkleme = new javax.swing.JButton();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        ÖğretmenSilme = new javax.swing.JButton();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        ÖğretmenGüncelleme = new javax.swing.JButton();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel67 = new javax.swing.JLabel();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jTextField60 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        UygunDalışYeriBulma = new javax.swing.JButton();
        jTextField95 = new javax.swing.JTextField();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jTextField96 = new javax.swing.JTextField();
        jTextField97 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        jTextField99 = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jTextField100 = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        SınıflarTab = new javax.swing.JTabbedPane();
        SınıflarBilgilerPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SınıflarTable = new javax.swing.JTable();
        SınıfBulma = new javax.swing.JButton();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        TümSınıflarıGösterme = new javax.swing.JButton();
        SınfılarİşlemlerPanel = new javax.swing.JPanel();
        SınıfEkleme = new javax.swing.JButton();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        SınıfSilme = new javax.swing.JButton();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        SınıfGüncelleme = new javax.swing.JButton();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel46 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField62 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel123 = new javax.swing.JLabel();
        UygunÖğretmenBulma = new javax.swing.JButton();
        jLabel129 = new javax.swing.JLabel();
        DalışyerleriTab = new javax.swing.JTabbedPane();
        DalşışyeriBilgilerPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DalışyeriTable = new javax.swing.JTable();
        DalışYeriBulma = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        TümDalısyerleriniGösterme = new javax.swing.JButton();
        DalışyeriİşlemlerPanel = new javax.swing.JPanel();
        DalışyeriEkleme = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        DalışyeriSilme = new javax.swing.JButton();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        DalışyeriGüncelleme = new javax.swing.JButton();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jTextField73 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jTextField75 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jTextField80 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DALIŞ OKULU BİLGİ SİSTEMİ");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane1.setAlignmentX(0.0F);
        jTabbedPane1.setAlignmentY(0.0F);

        AnasayfaPanel.setBackground(new java.awt.Color(153, 153, 153));
        AnasayfaPanel.setLayout(null);

        jLabel114.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 28)); // NOI18N
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/packages/pngkit_diver-png_386455.png"))); // NOI18N
        jLabel114.setText("            ADAŞ DALIŞ OKULU");
        AnasayfaPanel.add(jLabel114);
        jLabel114.setBounds(380, 240, 1570, 460);

        jTabbedPane1.addTab("ANASAYFA", AnasayfaPanel);

        UyelerBilgilerPanel.setBackground(new java.awt.Color(91, 108, 187));
        UyelerBilgilerPanel.setForeground(new java.awt.Color(91, 108, 187));

        ÜyelerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "İsim", "Soyisim", "Kayıt ID", "Boy", "Doğum Tarihi", "Sınıfı", "Sağlık Durumu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ÜyelerTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(ÜyelerTable);

        ÜyeBulma.setBackground(new java.awt.Color(204, 204, 204));
        ÜyeBulma.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÜyeBulma.setText("Üye Bilgilerini Göster");
        ÜyeBulma.setBorder(null);
        ÜyeBulma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ÜyeBulma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ÜyeBulmaActionPerformed(evt);
            }
        });

        jTextField61.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField63.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField65.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel70.setText("Boy");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel71.setText("Kayıt ID");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel72.setText("Doğum Tarihi");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel73.setText("Sağlık Durumu");

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel74.setText("Sınıf ID");

        jTextField66.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel75.setText("Soyisim");

        jTextField85.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel97.setText("İsim");

        jCheckBox1.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox1.setText("Uygun");

        jCheckBox2.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox2.setText("Uygun Değil");

        jDateChooser1.setDateFormatString("dd.MM.yyyy");

        jLabel101.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setText("ÜYE ARAMA");

        TümÜyeleriGösterme.setBackground(new java.awt.Color(204, 204, 204));
        TümÜyeleriGösterme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        TümÜyeleriGösterme.setText("Tüm Üyelerin Bilgilerini Göster");
        TümÜyeleriGösterme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TümÜyeleriGöstermeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout UyelerBilgilerPanelLayout = new javax.swing.GroupLayout(UyelerBilgilerPanel);
        UyelerBilgilerPanel.setLayout(UyelerBilgilerPanelLayout);
        UyelerBilgilerPanelLayout.setHorizontalGroup(
            UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UyelerBilgilerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UyelerBilgilerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ÜyeBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField61)
                                .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(UyelerBilgilerPanelLayout.createSequentialGroup()
                                    .addComponent(jCheckBox1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox2))
                                .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TümÜyeleriGösterme)))
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1283, Short.MAX_VALUE))
        );
        UyelerBilgilerPanelLayout.setVerticalGroup(
            UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UyelerBilgilerPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField66, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ÜyeBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(TümÜyeleriGösterme, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        ÜyelerimizTab.addTab("BİLGİLER", UyelerBilgilerPanel);

        UyelerİşlemlerPanel.setBackground(new java.awt.Color(91, 108, 187));

        ÜyeEkle.setBackground(new java.awt.Color(91, 240, 12));
        ÜyeEkle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÜyeEkle.setText("EKLE");
        ÜyeEkle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÜyeEkleMouseClicked(evt);
            }
        });

        jTextField67.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField69.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField71.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel76.setText("Boy");

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel77.setText("Kayıt ID");

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel78.setText("Doğum Tarihi");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel79.setText("Sağlık Durumu");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel80.setText("Sınıfı");

        ÜyeSilme.setBackground(new java.awt.Color(255, 36, 50));
        ÜyeSilme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÜyeSilme.setText("SİL");
        ÜyeSilme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÜyeSilmeMouseClicked(evt);
            }
        });

        jTextField72.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField74.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField76.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel81.setText("Boy");

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel82.setText("Kayıt ID");

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel83.setText("Doğum Tarihi");

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel84.setText("Sağlık Durumu");

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel85.setText("Sınıfı");

        ÜyeGüncelleme.setBackground(new java.awt.Color(235, 191, 37));
        ÜyeGüncelleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÜyeGüncelleme.setText("GÜNCELLE");
        ÜyeGüncelleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÜyeGüncellemeMouseClicked(evt);
            }
        });

        jTextField77.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField79.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField81.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel86.setText("Boy");

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel87.setText("Kayıt ID");

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel88.setText("Doğum Tarihi");

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel89.setText("Sağlık Durumu");

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel90.setText("Sınıfı");

        jLabel91.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("ÜYE GÜNCELLEME");

        jLabel92.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("ÜYE EKLEME");

        jLabel93.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("ÜYE SİLME");

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel94.setText("Soyisim");

        jTextField82.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField83.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel95.setText("İsim");

        jTextField84.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel96.setText("İsim");

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel98.setText("İsim");

        jTextField86.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel99.setText("Soyisim");

        jTextField87.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel100.setText("Soyisim");

        jTextField88.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jDateChooser2.setDateFormatString("dd.MM.yyyy");

        jCheckBox3.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox3.setText("Uygun");

        jCheckBox4.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox4.setText("Uygun Değil");

        jCheckBox5.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox5.setText("Uygun Değil");

        jCheckBox6.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox6.setText("Uygun ");

        jDateChooser3.setDateFormatString("dd.MM.yyyy");

        jDateChooser4.setDateFormatString("dd.MM.yyyy");

        jCheckBox7.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox7.setText("Uygun");

        jCheckBox8.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox8.setText("Uygun Değil");

        jTextField90.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel115.setText("Yeni İsim");

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel116.setText("Yeni Soyisim");

        jTextField91.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField92.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel117.setText("Yeni Kayıt ID");

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel118.setText("Yeni Boy");

        jTextField93.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jDateChooser5.setDateFormatString("dd.MM.yyyy");

        jLabel119.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel119.setText("Yeni Doğum Tarihi");

        jLabel120.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel120.setText("Yeni Sınıfı");

        jTextField94.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jCheckBox11.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox11.setText("Uygun");

        jLabel121.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel121.setText("Yeni Sağlık Durumu");

        jCheckBox12.setBackground(new java.awt.Color(91, 108, 187));
        jCheckBox12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox12.setText("Uygun Değil");

        jComboBox4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Başlangıç", "Orta", "İleri" }));

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("Seviye");

        UygunSınıfBulma.setBackground(new java.awt.Color(204, 204, 204));
        UygunSınıfBulma.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        UygunSınıfBulma.setText("Sınıf Bul");
        UygunSınıfBulma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UygunSınıfBulmaActionPerformed(evt);
            }
        });

        jLabel122.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("Uygun Sınıf Bulma");

        javax.swing.GroupLayout UyelerİşlemlerPanelLayout = new javax.swing.GroupLayout(UyelerİşlemlerPanel);
        UyelerİşlemlerPanel.setLayout(UyelerİşlemlerPanelLayout);
        UyelerİşlemlerPanelLayout.setHorizontalGroup(
            UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(UygunSınıfBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ÜyeEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField69, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(jTextField67)
                                        .addComponent(jTextField82)
                                        .addComponent(jTextField86)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UyelerİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jCheckBox3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckBox4))
                                        .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ÜyeSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, UyelerİşlemlerPanelLayout.createSequentialGroup()
                                    .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, UyelerİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jCheckBox6)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox5))
                                .addComponent(jTextField83, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField72, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField74, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField87, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel119, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel120, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel121, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel118, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel117, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField93, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(jTextField90)
                                    .addComponent(jTextField92)
                                    .addComponent(jTextField91)
                                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                        .addComponent(jCheckBox11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox12))
                                    .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                            .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ÜyeGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField79, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(jTextField84)
                                .addComponent(jTextField77)
                                .addComponent(jTextField88)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                    .addComponent(jCheckBox7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox8))
                                .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        UyelerİşlemlerPanelLayout.setVerticalGroup(
            UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox5)
                                    .addComponent(jCheckBox6))
                                .addGap(32, 32, 32)
                                .addComponent(ÜyeSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox4))
                                .addGap(32, 32, 32)
                                .addComponent(ÜyeEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(83, 83, 83)
                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UygunSınıfBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UyelerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UyelerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox11)
                    .addComponent(jCheckBox12))
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(ÜyeGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ÜyelerimizTab.addTab("İŞLEMLER", UyelerİşlemlerPanel);

        jTabbedPane1.addTab("ÜYELERİMİZ", ÜyelerimizTab);

        ÖğretmenlerBilgilerPanel.setBackground(new java.awt.Color(100, 193, 21));

        ÖğretmenlerTable.setAutoCreateRowSorter(true);
        ÖğretmenlerTable.setBackground(new java.awt.Color(250, 250, 250));
        ÖğretmenlerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "İsim", "Soyisim", "Personal ID", "Öğrenci Sayısı", "Seviye", "Eğitim Yeri"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ÖğretmenlerTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(ÖğretmenlerTable);

        ÖğretmenBulma.setBackground(new java.awt.Color(204, 204, 204));
        ÖğretmenBulma.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        ÖğretmenBulma.setText("Öğretmen Bilgileri Göster");
        ÖğretmenBulma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÖğretmenBulmaMouseClicked(evt);
            }
        });

        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField26.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField31.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField36.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField41.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Personal ID");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Soyisim");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Öğrenci Sayısı");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Eğitim Yeri ID");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Seviye");

        jTextField57.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("İsim");

        jLabel102.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("ÖĞRETMEN ARAMA");

        TümÖğretmenleriGösterme.setBackground(new java.awt.Color(204, 204, 204));
        TümÖğretmenleriGösterme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        TümÖğretmenleriGösterme.setText("Tüm Öğretmen Bilgilerini Göster");
        TümÖğretmenleriGösterme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TümÖğretmenleriGöstermeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ÖğretmenlerBilgilerPanelLayout = new javax.swing.GroupLayout(ÖğretmenlerBilgilerPanel);
        ÖğretmenlerBilgilerPanel.setLayout(ÖğretmenlerBilgilerPanelLayout);
        ÖğretmenlerBilgilerPanelLayout.setHorizontalGroup(
            ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ÖğretmenlerBilgilerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ÖğretmenlerBilgilerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(ÖğretmenlerBilgilerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TümÖğretmenleriGösterme)
                            .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ÖğretmenBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField31)
                                .addComponent(jTextField21)
                                .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(76, 76, 76)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1297, Short.MAX_VALUE))
        );
        ÖğretmenlerBilgilerPanelLayout.setVerticalGroup(
            ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ÖğretmenlerBilgilerPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField57, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ÖğretmenlerBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ÖğretmenBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(TümÖğretmenleriGösterme, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(295, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );

        ÖğretmenlerTab.addTab("BİLGİLER", ÖğretmenlerBilgilerPanel);

        ÖğretmenlerİşlemlerPanel.setBackground(new java.awt.Color(100, 193, 21));

        ÖğretmenEkleme.setBackground(new java.awt.Color(91, 240, 12));
        ÖğretmenEkleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÖğretmenEkleme.setText("EKLE");
        ÖğretmenEkleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÖğretmenEklemeMouseClicked(evt);
            }
        });

        jTextField42.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField43.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField44.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField46.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Personal ID");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Soyisim");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("Eğitim Yeri ID");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("Seviye");

        ÖğretmenSilme.setBackground(new java.awt.Color(255, 36, 50));
        ÖğretmenSilme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÖğretmenSilme.setText("SİL");
        ÖğretmenSilme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ÖğretmenSilmeActionPerformed(evt);
            }
        });

        jTextField47.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField48.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField49.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField50.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField51.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Personal ID");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Soyisim");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("Öğrenci Sayısı");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel56.setText("Eğitim Yeri ID");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setText("Seviye");

        ÖğretmenGüncelleme.setBackground(new java.awt.Color(235, 191, 37));
        ÖğretmenGüncelleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ÖğretmenGüncelleme.setText("GÜNCELLE");
        ÖğretmenGüncelleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ÖğretmenGüncellemeMouseClicked(evt);
            }
        });

        jTextField52.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField53.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField54.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField55.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField56.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Personal ID");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("Soyisim");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Öğrenci Sayısı");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setText("Eğitim Yeri ID");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("Seviye");

        jLabel63.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("ÖĞRETMEN GÜNCELLEME");

        jLabel64.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("ÖĞRETMEN EKLEME");

        jLabel65.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("ÖĞRETMEN SİLME");

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel67.setText("İsim");

        jTextField58.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField59.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setText("İsim");

        jTextField60.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setText("İsim");

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Seviye");

        jComboBox3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Başlangıç", "Orta", "İleri" }));

        UygunDalışYeriBulma.setBackground(new java.awt.Color(204, 204, 204));
        UygunDalışYeriBulma.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        UygunDalışYeriBulma.setText("Dalış Yeri Bul");
        UygunDalışYeriBulma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UygunDalışYeriBulmaActionPerformed(evt);
            }
        });

        jTextField95.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel126.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel126.setText("Yeni İsim");

        jLabel127.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel127.setText("Yeni Soyisim");

        jTextField96.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField97.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel128.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel128.setText("Yeni Personal ID");

        jTextField99.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel130.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel130.setText("Yeni Seviye");

        jLabel131.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel131.setText("Yeni Eğitim Yeri ID");

        jTextField100.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Uygun Dalış Yeri Bulma");

        javax.swing.GroupLayout ÖğretmenlerİşlemlerPanelLayout = new javax.swing.GroupLayout(ÖğretmenlerİşlemlerPanel);
        ÖğretmenlerİşlemlerPanel.setLayout(ÖğretmenlerİşlemlerPanelLayout);
        ÖğretmenlerİşlemlerPanelLayout.setHorizontalGroup(
            ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ÖğretmenEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField44)
                                .addComponent(jTextField42)
                                .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(UygunDalışYeriBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ÖğretmenSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField59, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField47, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField49, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField54, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jTextField60)
                            .addComponent(jTextField52)))
                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel128, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField97)
                                .addComponent(jTextField95)
                                .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel130, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel131, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ÖğretmenGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        ÖğretmenlerİşlemlerPanelLayout.setVerticalGroup(
            ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(ÖğretmenSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(344, 344, 344))
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(UygunDalışYeriBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(ÖğretmenGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ÖğretmenlerİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ÖğretmenlerİşlemlerPanelLayout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(ÖğretmenEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(392, 392, 392))))
        );

        ÖğretmenlerTab.addTab("İŞLEMLER", ÖğretmenlerİşlemlerPanel);

        jTabbedPane1.addTab("ÖĞRETMENLER", ÖğretmenlerTab);

        SınıflarBilgilerPanel.setBackground(new java.awt.Color(240, 211, 104));

        SınıflarTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sınıf ID", "Kapasite", "Seviye", "Öğretmen ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(SınıflarTable);

        SınıfBulma.setBackground(new java.awt.Color(204, 204, 204));
        SınıfBulma.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SınıfBulma.setText("Sınıf Bilgilerini Göster");
        SınıfBulma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SınıfBulmaMouseClicked(evt);
            }
        });

        jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Sınıf İD");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Kapasite");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Öğretmen ID");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Seviye");

        jLabel103.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("SINIF ARAMA");

        TümSınıflarıGösterme.setBackground(new java.awt.Color(204, 204, 204));
        TümSınıflarıGösterme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        TümSınıflarıGösterme.setText("Tüm Sınıf Bilgilerini Göster");
        TümSınıflarıGösterme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TümSınıflarıGöstermeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SınıflarBilgilerPanelLayout = new javax.swing.GroupLayout(SınıflarBilgilerPanel);
        SınıflarBilgilerPanel.setLayout(SınıflarBilgilerPanelLayout);
        SınıflarBilgilerPanelLayout.setHorizontalGroup(
            SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SınıflarBilgilerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SınıflarBilgilerPanelLayout.createSequentialGroup()
                        .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SınıflarBilgilerPanelLayout.createSequentialGroup()
                                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SınıfBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SınıflarBilgilerPanelLayout.createSequentialGroup()
                        .addComponent(TümSınıflarıGösterme)
                        .addGap(77, 77, 77)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1378, Short.MAX_VALUE))
        );
        SınıflarBilgilerPanelLayout.setVerticalGroup(
            SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(SınıflarBilgilerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SınıflarBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SınıfBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(TümSınıflarıGösterme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(415, Short.MAX_VALUE))
        );

        SınıflarTab.addTab("BİLGİLER", SınıflarBilgilerPanel);

        SınfılarİşlemlerPanel.setBackground(new java.awt.Color(240, 211, 104));

        SınıfEkleme.setBackground(new java.awt.Color(91, 240, 12));
        SınıfEkleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SınıfEkleme.setText("EKLE");
        SınıfEkleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SınıfEklemeMouseClicked(evt);
            }
        });

        jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField28.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField29.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Sınıf ID");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Kapasite");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Öğretmen ID");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Seviye");

        SınıfSilme.setBackground(new java.awt.Color(255, 36, 50));
        SınıfSilme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SınıfSilme.setText("SİL");
        SınıfSilme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SınıfSilmeMouseClicked(evt);
            }
        });

        jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField33.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField34.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField35.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Sınıf ID");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Kapasite");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Öğretmen ID");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Seviye");

        SınıfGüncelleme.setBackground(new java.awt.Color(235, 191, 37));
        SınıfGüncelleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SınıfGüncelleme.setText("GÜNCELLE");
        SınıfGüncelleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SınıfGüncellemeMouseClicked(evt);
            }
        });

        jTextField37.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField38.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField39.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField40.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Sınıf ID");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Kapasite");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Öğretmen ID");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Seviye");

        jLabel44.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("SINIF GÜNCELLEME");

        jLabel45.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("SINIF EKLEME");

        jLabel46.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("SINIF SİLME");

        jTextField62.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel105.setText("Yeni Öğretmen ID");

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel106.setText("Yeni Seviye");

        jTextField64.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField68.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel107.setText("Yeni Kapasite");

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel108.setText("Yeni Sınıf ID");

        jTextField70.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Başlangıç", "Orta", "İleri" }));

        jLabel123.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Seviye");

        UygunÖğretmenBulma.setBackground(new java.awt.Color(204, 204, 204));
        UygunÖğretmenBulma.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        UygunÖğretmenBulma.setText("Oğretmen Bul");
        UygunÖğretmenBulma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UygunÖğretmenBulmaActionPerformed(evt);
            }
        });

        jLabel129.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText("Uygun Öğretmen Bulma");

        javax.swing.GroupLayout SınfılarİşlemlerPanelLayout = new javax.swing.GroupLayout(SınfılarİşlemlerPanel);
        SınfılarİşlemlerPanel.setLayout(SınfılarİşlemlerPanelLayout);
        SınfılarİşlemlerPanelLayout.setHorizontalGroup(
            SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SınfılarİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SınıfEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UygunÖğretmenBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SınfılarİşlemlerPanelLayout.createSequentialGroup()
                            .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SınıfSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1))
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel105))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SınıfGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator14))
                .addContainerGap(450, Short.MAX_VALUE))
        );
        SınfılarİşlemlerPanelLayout.setVerticalGroup(
            SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(SınıfSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addComponent(SınıfEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SınıfGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SınfılarİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(SınfılarİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UygunÖğretmenBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(217, Short.MAX_VALUE))
        );

        SınıflarTab.addTab("İŞLEMLER", SınfılarİşlemlerPanel);

        jTabbedPane1.addTab("SINIFLAR", SınıflarTab);

        DalşışyeriBilgilerPanel.setBackground(new java.awt.Color(136, 88, 54));

        DalışyeriTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dalış Yeri İsmi", "Dalış Yeri İD", "Derinlik", "Çeşitlilik", "Seviye"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(DalışyeriTable);

        DalışYeriBulma.setBackground(new java.awt.Color(204, 204, 204));
        DalışYeriBulma.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        DalışYeriBulma.setText("Dalış Yerleri Bilgilerini Göster");
        DalışYeriBulma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DalışYeriBulmaMouseClicked(evt);
            }
        });
        DalışYeriBulma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DalışYeriBulmaActionPerformed(evt);
            }
        });

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Dalış Yeri İD");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Dalış Yeri İsmi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Derinlik");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Seviye");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Çeşitlilik");

        jLabel104.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setText("DALIŞ YERİ ARAMA");

        TümDalısyerleriniGösterme.setBackground(new java.awt.Color(204, 204, 204));
        TümDalısyerleriniGösterme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        TümDalısyerleriniGösterme.setText("Tüm Dalış Yeri Bilgilerini Göster");
        TümDalısyerleriniGösterme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TümDalısyerleriniGöstermeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DalşışyeriBilgilerPanelLayout = new javax.swing.GroupLayout(DalşışyeriBilgilerPanel);
        DalşışyeriBilgilerPanel.setLayout(DalşışyeriBilgilerPanelLayout);
        DalşışyeriBilgilerPanelLayout.setHorizontalGroup(
            DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DalşışyeriBilgilerPanelLayout.createSequentialGroup()
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalşışyeriBilgilerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(DalşışyeriBilgilerPanelLayout.createSequentialGroup()
                                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DalışYeriBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                                    .addComponent(jTextField1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DalşışyeriBilgilerPanelLayout.createSequentialGroup()
                        .addContainerGap(83, Short.MAX_VALUE)
                        .addComponent(TümDalısyerleriniGösterme)
                        .addGap(42, 42, 42)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1372, Short.MAX_VALUE))
        );
        DalşışyeriBilgilerPanelLayout.setVerticalGroup(
            DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(DalşışyeriBilgilerPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DalşışyeriBilgilerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DalışYeriBulma, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(TümDalısyerleriniGösterme, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212))
        );

        DalışyerleriTab.addTab("BİLGİLER", DalşışyeriBilgilerPanel);

        DalışyeriİşlemlerPanel.setBackground(new java.awt.Color(136, 88, 54));

        DalışyeriEkleme.setBackground(new java.awt.Color(91, 240, 12));
        DalışyeriEkleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        DalışyeriEkleme.setText("EKLE");
        DalışyeriEkleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DalışyeriEklemeMouseClicked(evt);
            }
        });

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Dalış Yeri İD");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Dalış Yeri İsmi");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Derinlik");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Seviye");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Çeşitlilik");

        DalışyeriSilme.setBackground(new java.awt.Color(255, 36, 50));
        DalışyeriSilme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        DalışyeriSilme.setText("SİL");
        DalışyeriSilme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DalışyeriSilmeMouseClicked(evt);
            }
        });

        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Dalış Yeri İD");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Dalış Yeri İsmi");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Derinlik");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Seviye");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Çeşitlilik");

        DalışyeriGüncelleme.setBackground(new java.awt.Color(235, 191, 37));
        DalışyeriGüncelleme.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        DalışyeriGüncelleme.setText("GÜNCELLE");
        DalışyeriGüncelleme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DalışyeriGüncellemeMouseClicked(evt);
            }
        });

        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField19.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Dalış Yeri İD");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Dalış Yeri İsmi");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Derinlik");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Seviye");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Çeşitlilik");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("DALIŞ YERİ GÜNCELLEME");

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("DALIŞ YERİ EKLEME");

        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("DALIŞ YERİ SİLME");

        jTextField73.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel109.setText("Yeni Dalış Yeri İD");

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel110.setText("Yeni Dalış Yeri İsmi");

        jTextField75.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField78.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel111.setText("Yeni Derinlik");

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel112.setText("Yeni Çeşitlilik");

        jTextField80.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField89.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel113.setText("Yeni Seviye");

        javax.swing.GroupLayout DalışyeriİşlemlerPanelLayout = new javax.swing.GroupLayout(DalışyeriİşlemlerPanel);
        DalışyeriİşlemlerPanel.setLayout(DalışyeriİşlemlerPanelLayout);
        DalışyeriİşlemlerPanelLayout.setHorizontalGroup(
            DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DalışyeriEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2)
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                    .addComponent(jSeparator1)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(89, 89, 89)
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                            .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DalışyeriSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField11)
                                .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89)
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE))
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)))
                                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField75, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator13)
                                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField16)
                                        .addComponent(jTextField18, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(jTextField73))
                                    .addGap(211, 211, 211))
                                .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel110)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(DalışyeriGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(139, 139, 139))
        );
        DalışyeriİşlemlerPanelLayout.setVerticalGroup(
            DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(DalışyeriEkleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))
                            .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DalışyeriİşlemlerPanelLayout.createSequentialGroup()
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DalışyeriİşlemlerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(DalışyeriGüncelleme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(DalışyeriSilme, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        DalışyerleriTab.addTab("İŞLEMLER", DalışyeriİşlemlerPanel);

        jTabbedPane1.addTab("DALIŞ YERLERİ", DalışyerleriTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MENU");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DalışYeriBulmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DalışYeriBulmaMouseClicked
                                /******************SHOW DALIŞYERİ******************/
                                ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
                                
                                String dname =jTextField1.getText();
                                if(!"".equals(dname)){
                                sqlQuery.add(" SELECT * FROM dalısyeri WHERE dname ='" + dname + "'");
                                }
                                String havuzid =jTextField4.getText();
                                //int kapasite = Integer.parseInt(jTextField24.getText());
                                //if (kapasite != 0){
                                //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
                                //}
                                if(!"".equals(havuzid)){
                                sqlQuery.add(" SELECT * FROM dalısyeri WHERE havuzid='" + havuzid + "'");
                                }
                                Boolean check=false;
                                String koşul = "";
                                String derinlik =jTextField5.getText();
                                char charderinlik[] = derinlik.toCharArray();
                                derinlik="";
                                for (int i = 0; i < charderinlik.length; i++) {

                                    if(!Character.isDigit(charderinlik[i])){
                                        koşul += charderinlik[i];
                                        check=true;
                                    }
                                    else{
                                        derinlik+= charderinlik[i];
                                    }
                                }
                                if(!"".equals(derinlik)){
                                    if(check){
                                    sqlQuery.add("SELECT * FROM dalısyeri WHERE derinlik " + koşul + " " + parseInt(derinlik));      
                                    }
                                    else{
                                    sqlQuery.add(" SELECT * FROM dalısyeri WHERE derinlik = " + parseInt(derinlik));   
                                    }
                                }
                                /*String derinlik =jTextField5.getText();
                                if(!"".equals(derinlik))
                                sqlQuery.add(" SELECT * FROM dalısyeri WHERE derinlik=" + parseInt(derinlik));*/
                                String çeşitlilik =jTextField6.getText();
                                if(!"".equals(çeşitlilik)){
                                sqlQuery.add(" SELECT * FROM dalısyeri WHERE çeşitlilik = '" + çeşitlilik +"'");
                                }
                                String hyıldız =jTextField3.getText();
                                if(!"".equals(hyıldız)){
                                sqlQuery.add(" SELECT * FROM dalısyeri WHERE hyıldız = '" + hyıldız +"'");
                                }
                                String Query =sqlQuery.get(0);
                                ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
                                for(String s :subsqlQuery){
                                Query = Query+ " INTERSECT "+ s;
                                }
                                System.out.println(Query);
                                try {
                                st=connDbc.createStatement();
                                
                                DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
                                dftable.setRowCount(0);
                                
                                
                                //pst=connDbc.prepareStatement(Query);
                                
                                rst=st.executeQuery(Query);
                                
                                
                                while(rst.next()){
                                
                                String tdname =rst.getString("dname");
                                String thavuzid =rst.getString("havuzid");
                                int    tderinlik =rst.getInt("derinlik");
                                String tçeşitlilik = rst.getString("çeşitlilik");
                                String thyıldız = rst.getString("hyıldız");
                                
                                Object[] obj = {tdname ,thavuzid ,tderinlik ,tçeşitlilik,thyıldız};
                                dftable.addRow(obj);
                                }
                                JOptionPane.showMessageDialog(null,"Values showed in table!" );
                                jTextField1.setText("");
                                jTextField3.setText("");
                                jTextField4.setText("");
                                jTextField5.setText("");
                                jTextField6.setText("");
                                } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,ex );
                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                }
    }//GEN-LAST:event_DalışYeriBulmaMouseClicked

    private void DalışyeriEklemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DalışyeriEklemeMouseClicked
                            /******************INSERT DALISYERİ*********************/
        try {
            String sqlQuery="INSERT INTO dalısyeri(dname, havuzid, derinlik, çeşitlilik, hyıldız)    VALUES (?, ?, ?, ?, ?);";
            pst=connDbc.prepareStatement(sqlQuery);
            pst.setString(1, jTextField2.getText());
            pst.setString(2, jTextField8.getText());
            pst.setInt(3, parseInt(jTextField9.getText()));
            pst.setString(4, jTextField10.getText());
            pst.setString(5, jTextField7.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Value İnserted!" );
            DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
            dftable.setRowCount(0);
            DalışYeriVeriAl();
            jTextField2.setText("");
            jTextField8.setText("");
            jTextField9.setText("");
            jTextField10.setText("");
            jTextField7.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DalışyeriEklemeMouseClicked

    private void DalışyeriSilmeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DalışyeriSilmeMouseClicked
        /******************DELETE DALISYERİ*********************/
        try {
            String Query="DELETE FROM dalısyeri WHERE ";
            int where=0;
            String dname =jTextField11.getText();
            if(!dname.equals("")){
                where++;
                Query += " dname='" + dname + "' ";
            }
            String havuzid =jTextField13.getText();
            if(!havuzid.equals("")){
                if(where!=0){
                    Query += " AND ";
                }
                where++;
                Query += " havuzid = '" + havuzid + "' ";
            }
            Boolean check=false;
            String koşul = "";
            String derinlik =jTextField14.getText();
            char charderinlik[] = derinlik.toCharArray();
            derinlik="";
            for (int i = 0; i < charderinlik.length; i++) {
            if(!Character.isDigit(charderinlik[i])){
                koşul += charderinlik[i];
                check=true;
            }
            else{
            derinlik+= charderinlik[i];
            }
            }
            if(!"".equals(derinlik)){
                if(where!=0){
                    Query += " AND ";
                }
                where++;
                if(check){
                    Query += (" derinlik " + koşul + " " + parseInt(derinlik));      
                }
                else{
                    Query += (" derinlik = " + parseInt(derinlik));   
                }
            }
            /*String derinlik =jTextField14.getText();
            if(!derinlik.equals("")){
            if(where!=0){
            Query += " AND ";
            }
            where++;
            Query += " derinlik = " + parseInt(derinlik);
            }*/
            String çeşitlilik =jTextField15.getText();
            if(!çeşitlilik.equals("")){
                if(where!=0){
                    Query += " AND ";
                }
                where++;
                Query += " çeşitlilik = '" + çeşitlilik + "' ";
            }
            String hyıldız =jTextField12.getText();
            if(!hyıldız.equals("")){
                if(where!=0){
                    Query += " AND ";
                }
                where++;
                Query += " hyıldız = '" + hyıldız + "' ";
            }
            System.out.println(Query);
            st=connDbc.createStatement();
            st.executeUpdate(Query);
            
            JOptionPane.showMessageDialog(null,"Value Deleted!" );
            
            
            DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
            dftable.setRowCount(0);
            DalışYeriVeriAl();
            
            jTextField11.setText("");
            jTextField12.setText("");
            jTextField13.setText("");
            jTextField14.setText("");
            jTextField15.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DalışyeriSilmeMouseClicked

    private void SınıfBulmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SınıfBulmaMouseClicked
        /******************SHOW SINIFLAR*********************/
        
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String sınıfid =jTextField23.getText();
        if(!"".equals(sınıfid)){
            sqlQuery.add("SELECT * FROM sınıflar WHERE sınıfid ='" + sınıfid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String kapasite =jTextField24.getText();
        char charkapasite[] = kapasite.toCharArray();
        kapasite="";
        for (int i = 0; i < charkapasite.length; i++) {
             
            if(!Character.isDigit(charkapasite[i])){
                koşul += charkapasite[i];
                check=true;
            }
            else{
                kapasite+= charkapasite[i];
            }
        }
        if(!"".equals(kapasite)){
            if(check){
            sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite" + koşul + " " + parseInt(kapasite));      
            }
            else{
            sqlQuery.add(" SELECT * FROM sınıflar WHERE kapasite = " + parseInt(kapasite));   
            }
        }
        String seviye =jTextField25.getText();
        if(!"".equals(seviye))
            sqlQuery.add("SELECT * FROM sınıflar WHERE yıldız='" + seviye + "'");
        String dalısegid =jTextField22.getText();
        if(!"".equals(dalısegid)){
            if("null".equals(dalısegid)){
                sqlQuery.add("SELECT * FROM sınıflar WHERE dalısegid ISNULL  ");
            }
            else{
                sqlQuery.add("SELECT * FROM sınıflar WHERE dalısegid ='" + dalısegid +"'");
            }
        }
            
         
        String Query =sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s :subsqlQuery){
                Query = Query+ " INTERSECT "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
            dftable.setRowCount(0);
            
            
            //pst=connDbc.prepareStatement(Query);
            
            rst=st.executeQuery(Query);
            
            
             while(rst.next()){  
                
                String tsınıfid =rst.getString("sınıfid");          
                int    tkapasite =rst.getInt("kapasite");
                String tYıldız =rst.getString("yıldız");
                String tdalısegid = rst.getString("dalısegid");
                
                Object[] obj = {tsınıfid ,tkapasite ,tYıldız ,tdalısegid};
                dftable.addRow(obj);
            }    
            JOptionPane.showMessageDialog(null,"Values showed in table!" );
            jTextField23.setText("");
            jTextField24.setText("");
            jTextField25.setText("");
            jTextField22.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SınıfBulmaMouseClicked

    private void SınıfEklemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SınıfEklemeMouseClicked
            /******************INSERT SINIFLAR*********************/
        try {
            
            String sqlQuery="INSERT INTO sınıflar(sınıfid, kapasite, yıldız, dalısegid)    VALUES (?, ?, ?, ?);";
            pst=connDbc.prepareStatement(sqlQuery);
            pst.setString(1, jTextField28.getText());
            if(jTextField28.getText().equals("auto")){
            String nextseq ="SELECT nextval('seq')";
            st=connDbc.createStatement();
            rst = st.executeQuery(nextseq);
            rst.next();
            pst.setString(1, String.valueOf(rst.getInt("nextval")));        
            }
            pst.setInt(2, parseInt(jTextField29.getText()));
            pst.setString(3, jTextField30.getText());
            String ogretmenid=jTextField27.getText();
            if(!"".equals(ogretmenid)){
            pst.setString(4, ogretmenid);
            }
            else{
            pst.setString(4, null );
            }
            //System.out.println(sqlQuery);
            //String sqlQuery="INSERT INTO sınıflar(sınıfid, kapasite, yıldız, dalısegid)    VALUES ('1010', 7, 'Orta', null);";
            pst.executeUpdate();
            System.out.println(pst.toString());
            JOptionPane.showMessageDialog(null,"Value İnserted!" );
            DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
            dftable.setRowCount(0);
            viewSınıflarVeriAl();
            jTextField28.setText("");
            jTextField29.setText("");
            jTextField30.setText("");
            jTextField27.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SınıfEklemeMouseClicked

    private void SınıfSilmeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SınıfSilmeMouseClicked
                /******************DELETE SINIFLAR*********************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String sınıfid =jTextField33.getText();
        if(!"".equals(sınıfid)){
            sqlQuery.add(" sınıfid='" + sınıfid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String kapasite =jTextField34.getText();
        char charkapasite[] = kapasite.toCharArray();
        kapasite="";
        for (int i = 0; i < charkapasite.length; i++) {
             
            if(!Character.isDigit(charkapasite[i])){
                koşul += charkapasite[i];
                check=true;
            }
            else{
                kapasite+= charkapasite[i];
            }
        }
        if(!"".equals(kapasite)){
            if(check){
            sqlQuery.add(" kapasite " + koşul + " " + parseInt(kapasite));      
            }
            else{
            sqlQuery.add("  kapasite = " + parseInt(kapasite));   
            }
        }
        /*String kapasites = jTextField34.getText();
        if(!"".equals(kapasites)){
        int kapasite = parseInt(kapasites);
        sqlQuery.add(" kapasite = " + kapasite);
        }*/
        String seviye =jTextField35.getText();
        if(!"".equals(seviye))
            sqlQuery.add(" yıldız='" + seviye + "'");
        String dalısegid =jTextField32.getText();
        if(!"".equals(dalısegid))
            sqlQuery.add(" dalısegid='" + dalısegid +"'");
        String Query ="DELETE FROM sınıflar WHERE "+sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s : subsqlQuery){
                Query = Query+ " AND "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            
            
            
            //pst=connDbc.prepareStatement(Query);
            
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null,"Value Deleted!" );
            
            
            DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
            dftable.setRowCount(0);
            viewSınıflarVeriAl();
            jTextField33.setText("");
            jTextField34.setText("");
            jTextField35.setText("");
            jTextField32.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SınıfSilmeMouseClicked

    private void SınıfGüncellemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SınıfGüncellemeMouseClicked
        /******************UPDATE SINIFLAR*********************/
        int updated=0;
        int where =0;
        String Query ="UPDATE sınıflar SET ";
        String Ysınıfid =jTextField70.getText();
        if(!"".equals(Ysınıfid)){
            Query+= (" sınıfid ='" + Ysınıfid + "'");
            updated++;
        }
        String Ykapasites =jTextField68.getText();
        //int kapasite = Integer.parseInt(jTextField24.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(Ykapasites)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            int ykapasite = parseInt(Ykapasites);
            Query+= (" kapasite =" + ykapasite);
            
        }
        String Yseviye =jTextField64.getText();
        if(!"".equals(Yseviye)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" yıldız='" + Yseviye + "'");
        }
        String Ydalısegid =jTextField62.getText();
        if(!"".equals(Ydalısegid)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" dalısegid='" + Ydalısegid +"'");
        }
        Query += " WHERE ";
        
        String sınıfid =jTextField38.getText();
        if(!"".equals(sınıfid)){
            Query+= (" sınıfid ='" + sınıfid + "'");
            where++;
        }
        Boolean check=false;
        String koşul = "";
        String kapasite =jTextField39.getText();
        char charkapasite[] = kapasite.toCharArray();
        kapasite="";
        for (int i = 0; i < charkapasite.length; i++) {
             
            if(!Character.isDigit(charkapasite[i])){
                koşul += charkapasite[i];
                check=true;
            }
            else{
                kapasite+= charkapasite[i];
            }
        }
        if(!"".equals(kapasite)){
            if(where!=0){
            Query+= " AND ";
            }
            where++;
            if(check){
            Query+= (" kapasite " + koşul + " " + parseInt(kapasite));      
            }
            else{
            Query+= ("  kapasite = " + parseInt(kapasite));   
            }
        }
        /*String kapasites = jTextField39.getText();
        if(!"".equals(kapasites)){
        if(where!=0){
        Query+= " AND ";
        }
        where++;
        int kapasite = parseInt(kapasites);
        Query+=(" kapasite=" + kapasite);
        }*/
        String seviye =jTextField40.getText();
        if(!"".equals(seviye)){
            if(where!=0){
                Query+= " AND ";
            }
            where++;
            Query+=(" yıldız='" + seviye + "'");
        }
        String dalısegid =jTextField37.getText();
        if(!"".equals(dalısegid)){
            if(where!=0){
                Query+= " AND ";
            }
            where++;
            Query+=(" dalısegid='" + dalısegid +"'");        
        }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            

            
            
            //pst=connDbc.prepareStatement(Query);
            
            st.executeUpdate(Query);
            
            DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
            dftable.setRowCount(0);
            viewSınıflarVeriAl();
            jTextField70.setText("");
            jTextField68.setText("");
            jTextField64.setText("");
            jTextField62.setText("");
            jTextField37.setText("");
            jTextField38.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            JOptionPane.showMessageDialog(null,"Values Updated!" );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SınıfGüncellemeMouseClicked

    private void ÖğretmenBulmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÖğretmenBulmaMouseClicked
        /************************** ÖĞRETMEN BULMA  **********************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String fname =jTextField57.getText();
        if(!"".equals(fname)){
            sqlQuery.add(" fname = '" + fname + "'");
        }
        String lname = jTextField21.getText();
        //int kapasite = Integer.parseInt(jTextField34.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(lname)){   
            sqlQuery.add(" lname='" + lname +"'");
        }
        String personalid =jTextField31.getText();
        if(!"".equals(personalid)){
            sqlQuery.add(" personalid ='" + personalid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String ogrsayisi =jTextField36.getText();
        char charogrs[] = ogrsayisi.toCharArray();
        ogrsayisi="";
        for (int i = 0; i < charogrs.length; i++) {
             
            if(!Character.isDigit(charogrs[i])){
                koşul += charogrs[i];
                check=true;
            }
            else{
                ogrsayisi+= charogrs[i];
            }
        }
        if(!"".equals(ogrsayisi)){
            if(check){
            sqlQuery.add(" ogrsayisi " + koşul + " " + parseInt(ogrsayisi));      
            }
            else{
            sqlQuery.add(" ogrsayisi = " + parseInt(ogrsayisi));   
            }
        }
        String oyıldız =jTextField41.getText();
        if(!"".equals(oyıldız)){
            sqlQuery.add(" oyıldız ='" + oyıldız + "'");
        }
        String havuzkey =jTextField26.getText();
        if(!"".equals(havuzkey)){
            sqlQuery.add(" havuzkey='" + havuzkey + "'");
        }
       
        String Query ="SELECT * FROM ogretmenler WHERE "+sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s : subsqlQuery){
                Query = Query+ " INTERSECT SELECT * FROM ogretmenler WHERE "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            
            //java.sql.Date sqldate = new java.sql.Date(utilStartDate.getTime());
            
            //pst=connDbc.prepareStatement(Query);
            
            rst = st.executeQuery(Query);
            
            DefaultTableModel dftable = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable.setRowCount(0);
             while(rst.next()){  
                
                String tfname =rst.getString("fname");          
                String tlname =rst.getString("lname");
                String tpersonalid =rst.getString("personalid");
                int    togrsayisi =rst.getInt("ogrsayisi");
                String toyıldız = rst.getString("oyıldız");
                String thavuzkey = rst.getString("havuzkey");
                Object[] obj = {tfname ,tlname ,tpersonalid ,togrsayisi ,toyıldız ,thavuzkey};
                dftable.addRow(obj);
            }    
            JOptionPane.showMessageDialog(null,"Values showed in table!" );
            
            

            jTextField57.setText("");
            jTextField21.setText("");
            jTextField31.setText("");
            jTextField36.setText("");
            jTextField41.setText("");
            jTextField26.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ÖğretmenBulmaMouseClicked

    private void ÜyeEkleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÜyeEkleMouseClicked
        /******************INSERT UYELER*********************/
        try {
            String sqlQuery="INSERT INTO uyeler(fname, lname, kayıtid, boy, bdate, sid, srapor)    VALUES (?,?,?, ?, ?, ?, ?);";
            pst=connDbc.prepareStatement(sqlQuery);
            pst.setString(1, jTextField86.getText());
            pst.setString(2, jTextField82.getText());
            pst.setString(3, jTextField67.getText());
            pst.setInt(4, parseInt(jTextField69.getText()));
            java.util.Date utilStartDate = jDateChooser2.getDate();
            java.sql.Date sqldate = new java.sql.Date(utilStartDate.getTime());
            pst.setDate(5, sqldate);
            String sınıf =jTextField71.getText();
            if (!sınıf.equals("")){
            pst.setString(6, sınıf);
            }
            else{
                pst.setString(6, null);
            }
            Boolean durum=null;
            if(jCheckBox3.isSelected()){
                durum =true;
                pst.setBoolean(7, true);
               
                if(jCheckBox2.isSelected()){
                durum =false;
                JOptionPane.showMessageDialog(null,"Only select 1 Check Box!" );
            }
            }
            if(jCheckBox4.isSelected()){
                durum =false;
                pst.setBoolean(7, false);
                pst.setString(6, null);
            }
            
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Value İnserted!" );
            DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
            dftable.setRowCount(0);
            UyelerVeriAl();
            DefaultTableModel dftable1 = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable1.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField86.setText("");
            jTextField82.setText("");
            jTextField67.setText("");
            jTextField69.setText("");
            jTextField71.setText("");
            jDateChooser2.setCalendar(null);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ÜyeEkleMouseClicked

    private void ÜyeSilmeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÜyeSilmeMouseClicked
                /*********** DELETE UYELER **************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String fname =jTextField83.getText();
        if(!"".equals(fname)){
            sqlQuery.add(" fname = '" + fname + "'");
        }
        String lname = jTextField87.getText();
        //int kapasite = Integer.parseInt(jTextField34.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(lname)){   
            sqlQuery.add(" lname ='" + lname +"'");
        }
        String kayıtid =jTextField72.getText();
        if(!"".equals(kayıtid)){
            sqlQuery.add(" kayıtid='" + kayıtid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String boy =jTextField74.getText();
        char charboy[] = boy.toCharArray();
        boy="";
        for (int i = 0; i < charboy.length; i++) {
             
            if(!Character.isDigit(charboy[i])){
                koşul += charboy[i];
                check=true;
            }
            else{
                boy+= charboy[i];
            }
        }
        if(!"".equals(boy)){
            if(check){
            sqlQuery.add(" boy " + koşul + " " + parseInt(boy));      
            }
            else{
            sqlQuery.add(" boy = " + parseInt(boy));   
            }
        }
        /*String boy =jTextField74.getText();
        if(!"".equals(boy)){
        sqlQuery.add(" boy = " + parseInt(boy));
        }*/
        
        java.util.Date bdate = jDateChooser3.getDate();
        if(bdate != null){
            java.sql.Date sqldate = new java.sql.Date(bdate.getTime());
            sqlQuery.add(" bdate= '"+ sqldate + "'" );
        }
        String sid =jTextField76.getText();
        if(!"".equals(sid)){
            sqlQuery.add(" sid='" + sid + "'");
        }
        if(jCheckBox6.isSelected()){
            sqlQuery.add(" srapor= '1' ");
            if(jCheckBox5.isSelected()){
                sqlQuery.remove(" srapor= '1' ");
                JOptionPane.showMessageDialog(null,"Only select 1 Check Box!" );
                jCheckBox5.setSelected(false);
                jCheckBox6.setSelected(false);
                
                    throw new ArithmeticException("More then 1 checkbox selected "); 

            }
        }
        if(jCheckBox5.isSelected()){
            sqlQuery.add(" srapor= '0' ");
        }
       
        String Query ="DELETE FROM uyeler WHERE "+sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s : subsqlQuery){
                Query = Query+ " AND "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            
            
            
            //pst=connDbc.prepareStatement(Query);
            
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null,"Value Deleted!" );
            
            
            DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
            dftable.setRowCount(0);
            UyelerVeriAl();
            DefaultTableModel dftable1 = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable1.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField72.setText("");
            jTextField74.setText("");
            jTextField76.setText("");
            jTextField83.setText("");
            jTextField87.setText("");
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_ÜyeSilmeMouseClicked

    private void ÜyeGüncellemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÜyeGüncellemeMouseClicked
       /******************    UPDATE UYELER    *********************/
        int updated=0;
        int where =0;
        String Query ="UPDATE uyeler SET ";
        String Yfname =jTextField90.getText();
        if(!"".equals(Yfname)){
            Query+= (" fname ='" + Yfname + "'");
            updated++;
        }
        String Ylname =jTextField91.getText();
        if(!"".equals(Ylname)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" lname ='" + Ylname +"'");
        }
        String Ykayıtid =jTextField92.getText();
        if(!"".equals(Ykayıtid)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" kayıtid ='" + Ykayıtid +"'");
        }
        
        String Yboy =jTextField93.getText();
        if(!"".equals(Yboy)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            int yboy = parseInt(Yboy);
            Query+= (" boy =" + yboy); 
        }
        java.util.Date ybdate = jDateChooser5.getDate();
        if(ybdate != null){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            java.sql.Date sqldate = new java.sql.Date(ybdate.getTime());
            Query+= (" bdate ='" + sqldate +"'"); 
        }
        String Ysid =jTextField94.getText();
        if(!"".equals(Ysid)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" sid ='" + Ysid + "'");
        }
        if(jCheckBox11.isSelected()){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" srapor ='" + 1 + "'");
            if(jCheckBox12.isSelected()){
                Query.replace(" srapor =' 1 '", "");
                JOptionPane.showMessageDialog(null,"Only select 1 Check Box!" );
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                
                    throw new ArithmeticException("More then 1 checkbox selected "); 

            }
        }
        if(jCheckBox12.isSelected()){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" srapor =' 0 ' ");
        }
        Query += " WHERE ";
        
        String fname =jTextField84.getText();
        if(!"".equals(fname)){
            Query+= (" fname ='" + fname + "'");
            where++;
        }
        String lname =jTextField88.getText();
        if(!"".equals(lname)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" lname ='" + lname +"'");
        }
        String kayıtid =jTextField77.getText();
        if(!"".equals(kayıtid)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" kayıtid ='" + kayıtid +"'");
        }
        Boolean check=false;
        String koşul = "";
        String boy =jTextField79.getText();
        char charboy[] = boy.toCharArray();
        boy="";
        for (int i = 0; i < charboy.length; i++) {
             
            if(!Character.isDigit(charboy[i])){
                koşul += charboy[i];
                check=true;
            }
            else{
                boy+= charboy[i];
            }
        }
        if(!"".equals(boy)){
            if(where!=0){
            Query+= "  AND ";
            }
            where++;
            if(check){
            Query+=(" boy " + koşul + " " + parseInt(boy));      
            }
            else{
            Query+=(" boy = " + parseInt(boy));   
            }
        }
      
        java.util.Date bdate = jDateChooser4.getDate();
        if(bdate != null){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            java.sql.Date sqldate = new java.sql.Date(bdate.getTime());
            Query+= (" bdate ='" + sqldate + "'"); 
        }
        String sid =jTextField81.getText();
        if(!"".equals(sid)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" sid ='" + sid + "'");
        }
        if(jCheckBox7.isSelected()){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" srapor =' 1 ' ");
            if(jCheckBox8.isSelected()){
                Query.replace(" srapor =' 1 '", " " );
                JOptionPane.showMessageDialog(null,"Only select 1 Check Box!" );
                jCheckBox7.setSelected(false);
                jCheckBox8.setSelected(false);
                
                    throw new ArithmeticException("More then 1 checkbox selected "); 

            }
        }
        if(jCheckBox8.isSelected()){
            if(where!=0){
            Query+= " AND ";
            }
            where++;
            Query+= (" srapor =' 0 ' ");
        }
        
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
        
            
            st.executeUpdate(Query);
            
            DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
            dftable.setRowCount(0);
            UyelerVeriAl();
            DefaultTableModel dftable1 = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable1.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField90.setText("");
            jTextField91.setText("");
            jTextField92.setText("");
            jTextField93.setText("");
            jTextField94.setText("");
            jCheckBox11.setSelected(false);
            jCheckBox12.setSelected(false);
            jTextField84.setText("");
            jTextField88.setText("");
            jTextField77.setText("");
            jTextField79.setText("");
            jTextField81.setText("");
            jCheckBox7.setSelected(false);
            jCheckBox8.setSelected(false);
            JOptionPane.showMessageDialog(null,"Values Updated!" );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ÜyeGüncellemeMouseClicked

    private void ÖğretmenGüncellemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÖğretmenGüncellemeMouseClicked
         /******************    UPDATE OGRETMENLER    *********************/
        int updated=0;
        int where =0;
        String Query ="UPDATE ogretmenler SET ";
        String Yfname =jTextField95.getText();
        if(!"".equals(Yfname)){
            Query+= (" fname ='" + Yfname + "'");
            updated++;
        }
        String Ylname =jTextField96.getText();
        if(!"".equals(Ylname)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" lname ='" + Ylname +"'");
        }
        String Ypersonalid =jTextField97.getText();
        if(!"".equals(Ypersonalid)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" personalid ='" + Ypersonalid +"'");
        }
        
        
        String Yoyıldız =jTextField99.getText();
        if(!"".equals(Yoyıldız)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" oyıldız ='" + Yoyıldız + "'");
        }
        String Yhavuzkey =jTextField100.getText();
        if(!"".equals(Yhavuzkey)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" havuzkey ='" + Yhavuzkey + "'");
        }
        Query += " WHERE ";
        
        String fname =jTextField60.getText();
        if(!"".equals(fname)){
            Query+= (" fname ='" + fname + "'");
            where++;
        }
        String lname =jTextField52.getText();
        if(!"".equals(lname)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" lname ='" + lname +"'");
        }
        String personalid =jTextField77.getText();
        if(!"".equals(personalid)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+= (" personalid ='" + personalid +"'");
        }
        
        Boolean check=false;
        String koşul = "";
        String ogrsayisi =jTextField55.getText();
        char charogr[] = ogrsayisi.toCharArray();
        ogrsayisi="";
        for (int i = 0; i < charogr.length; i++) {
             
            if(!Character.isDigit(charogr[i])){
                koşul += charogr[i];
                check=true;
            }
            else{
                ogrsayisi+= charogr[i];
            }
        }
        if(!"".equals(ogrsayisi)){
            if(where!=0){
            Query+= "  AND  ";
            }
            if(check){
            Query+= (" ogrsayisi " + koşul + " " + parseInt(ogrsayisi));      
            }
            else{
            Query+= (" ogrsayisi = " + parseInt(ogrsayisi));   
            }
        }
        String oyıldız =jTextField56.getText();
        if(!"".equals(oyıldız)){
            if(updated!=0){
            Query+= " AND ";
            }
            updated++;
            Query+= (" oyıldız ='" + oyıldız + "'");
        }
        String havuzkey =jTextField53.getText();
        if(!"".equals(havuzkey)){
            if(updated!=0){
            Query+= " AND ";
            }
            updated++;
            Query+= (" havuzkey ='" + havuzkey + "'");
        }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
        
            
            st.executeUpdate(Query);
            
            
            DefaultTableModel dftable1 = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable1.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField60.setText("");
            jTextField52.setText("");
            jTextField54.setText("");
            jTextField55.setText("");
            jTextField56.setText("");
            jTextField53.setText("");
            jTextField95.setText("");
            jTextField96.setText("");
            jTextField97.setText("");
            jTextField99.setText("");
            jTextField100.setText("");

            JOptionPane.showMessageDialog(null,"Values Updated!" );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ÖğretmenGüncellemeMouseClicked

    private void ÖğretmenEklemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ÖğretmenEklemeMouseClicked
                        /******************INSERT OGRETMENLER *********************/
        try {
            String sqlQuery="INSERT INTO ogretmenler(fname, lname, personalid, ogrsayisi, oyıldız, havuzkey)    VALUES (?,?, ?, ?, ?, ?);";
            pst=connDbc.prepareStatement(sqlQuery);
            pst.setString(1, jTextField58.getText());
            pst.setString(2, jTextField42.getText());
            pst.setString(3, jTextField44.getText());
            pst.setInt(4, 0);
            pst.setString(5, jTextField46.getText());
            pst.setString(6, jTextField43.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Value İnserted!" );
            DefaultTableModel dftable = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField58.setText("");
            jTextField42.setText("");
            jTextField44.setText("");
            jTextField46.setText("");
            jTextField43.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ÖğretmenEklemeMouseClicked

    private void DalışyeriGüncellemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DalışyeriGüncellemeMouseClicked
        /*******************   UPDATE DALIŞYERİ *********************/
        String Query ="UPDATE dalısyeri SET ";
        int updated=0;
        int where =0;
        
        String Ydname =jTextField73.getText();
        if(!"".equals(Ydname)){
            updated++;
            Query+= (" dname ='" + Ydname + "'");
        }
        String Yhavuzid =jTextField75.getText();
        if(!"".equals(Yhavuzid)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" havuzid ='" + Yhavuzid + "'");
        }
        String Yderinlik =jTextField78.getText();
        if(!"".equals(Yderinlik)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" derinlik = " + parseInt(Yderinlik));
        }
        String Yçeşitlilik =jTextField80.getText();
        if(!"".equals(Yçeşitlilik)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" çeşitlilik ='" + Yçeşitlilik +"'");
        }
        String Yseviye =jTextField89.getText();
        if(!"".equals(Yseviye)){
            if(updated!=0){
            Query+= " , ";
            }
            updated++;
            Query+= (" hyıldız='" + Yseviye +"'");
        }
        Query += " WHERE ";
        
        String dname =jTextField16.getText();
        if(!"".equals(dname)){
            where++;
            Query+= (" dname ='" + dname + "' ");
        }
        String havuzid = jTextField18.getText();
        if(!"".equals(havuzid)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+=(" havuzid ='" + havuzid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String derinlik =jTextField19.getText();
        char charderinlik[] = derinlik.toCharArray();
        derinlik="";
        for (int i = 0; i < charderinlik.length; i++) {
            if(!Character.isDigit(charderinlik[i])){
                koşul += charderinlik[i];
                check=true;
            }
            else{
            derinlik+= charderinlik[i];
            }
            }
            if(!"".equals(derinlik)){
                if(where!=0){
                    Query += " AND ";
                }
                where++;
                if(check){
                    Query += (" derinlik " + koşul + " " + parseInt(derinlik));      
                }
            else{
                Query += (" derinlik = " + parseInt(derinlik));   
            }
        }
        String çeşitlilik =jTextField20.getText();
        if(!"".equals(çeşitlilik)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+=(" çeşitlilik='" + çeşitlilik +"'");        
        }
        String seviye =jTextField17.getText();
        if(!"".equals(seviye)){
            if(where!=0){
            Query+= "  AND  ";
            }
            where++;
            Query+=(" seviye ='" + seviye +"'");        
        }
        
        
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            

            
            
            //pst=connDbc.prepareStatement(Query);
            
            st.executeUpdate(Query);
            
            DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
            dftable.setRowCount(0);
            DalışYeriVeriAl();
            jTextField16.setText("");
            jTextField17.setText("");
            jTextField18.setText("");
            jTextField19.setText("");
            jTextField20.setText("");
            jTextField73.setText("");
            jTextField75.setText("");
            jTextField78.setText("");
            jTextField80.setText("");
            jTextField89.setText("");
            JOptionPane.showMessageDialog(null,"Values Updated!" );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }//GEN-LAST:event_DalışyeriGüncellemeMouseClicked

    private void TümSınıflarıGöstermeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TümSınıflarıGöstermeMouseClicked
        DefaultTableModel dftable = (DefaultTableModel) SınıflarTable.getModel();
        dftable.setRowCount(0);
        viewSınıflarVeriAl();
    }//GEN-LAST:event_TümSınıflarıGöstermeMouseClicked

    private void TümDalısyerleriniGöstermeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TümDalısyerleriniGöstermeActionPerformed
        DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
        dftable.setRowCount(0);
        DalışYeriVeriAl();
    }//GEN-LAST:event_TümDalısyerleriniGöstermeActionPerformed

    private void TümÖğretmenleriGöstermeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TümÖğretmenleriGöstermeMouseClicked
        DefaultTableModel dftable = (DefaultTableModel) ÖğretmenlerTable.getModel();
        dftable.setRowCount(0);
        ÖğretmenlerVeriAl();
    }//GEN-LAST:event_TümÖğretmenleriGöstermeMouseClicked

    private void TümÜyeleriGöstermeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TümÜyeleriGöstermeMouseClicked
        DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
        dftable.setRowCount(0);
        UyelerVeriAl();
    }//GEN-LAST:event_TümÜyeleriGöstermeMouseClicked

    private void DalışYeriBulmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DalışYeriBulmaActionPerformed
        /************************* DALIŞYERİ ARAMA *********************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String dname =jTextField1.getText();
        if(!"".equals(dname)){
            sqlQuery.add(" SELECT * FROM dalısyeri WHERE dname ='" + dname + "'");
        }
        String havuzid =jTextField4.getText();
        //int kapasite = Integer.parseInt(jTextField24.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(havuzid)){
            sqlQuery.add(" SELECT * FROM dalısyeri WHERE havuzid='" + havuzid + "'");
        }
        String derinlik =jTextField5.getText();
        if(!"".equals(derinlik))
            sqlQuery.add(" SELECT * FROM dalısyeri WHERE derinlik=" + parseInt(derinlik));
        String çeşitlilik =jTextField6.getText();
        if(!"".equals(çeşitlilik)){
            sqlQuery.add(" SELECT * FROM dalısyeri WHERE çeşitlilik = '" + çeşitlilik +"'");
        }
        String hyıldız =jTextField3.getText();
        if(!"".equals(hyıldız)){
            sqlQuery.add(" SELECT * FROM dalısyeri WHERE hyıldız = '" + hyıldız +"'");
        }
        String Query =sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s :subsqlQuery){
                Query = Query+ " INTERSECT "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            DefaultTableModel dftable = (DefaultTableModel) DalışyeriTable.getModel();
            dftable.setRowCount(0);
            
            
            //pst=connDbc.prepareStatement(Query);
            
            rst=st.executeQuery(Query);
            
            
             while(rst.next()){  
                
                String tdname =rst.getString("dname");          
                String thavuzid =rst.getString("havuzid");
                int    tderinlik =rst.getInt("derinlik");
                String tçeşitlilik = rst.getString("çeşitlilik");
                String thyıldız = rst.getString("hyıldız");
                
                Object[] obj = {tdname ,thavuzid ,tderinlik ,tçeşitlilik,thyıldız};
                dftable.addRow(obj);
            }    
            JOptionPane.showMessageDialog(null,"Values showed in table!" );
            jTextField1.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DalışYeriBulmaActionPerformed

    private void ÜyeBulmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ÜyeBulmaActionPerformed
        /************************** UYE ARAMA **********************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String fname =jTextField85.getText();
        if(!"".equals(fname)){
            sqlQuery.add(" fname = '" + fname + "'");
        }
        String lname = jTextField66.getText();
        //int kapasite = Integer.parseInt(jTextField34.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(lname)){   
            sqlQuery.add(" lname='" + lname +"'");
        }
        String kayıtid =jTextField61.getText();
        if(!"".equals(kayıtid)){
            sqlQuery.add(" yıldız='" + kayıtid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String boy =jTextField63.getText();
        char charboy[] = boy.toCharArray();
        boy="";
        for (int i = 0; i < charboy.length; i++) {
             
            if(!Character.isDigit(charboy[i])){
                koşul += charboy[i];
                check=true;
            
            }
            else{
                boy+= charboy[i];
            }
        }
        if(!"".equals(boy)){
            if(check){
            sqlQuery.add(" boy " + koşul + " " + parseInt(boy));      
            }
            else{
            sqlQuery.add(" boy = " + parseInt(boy));   
            }
        }
        java.util.Date bdate = jDateChooser1.getDate();
        if(bdate != null){
            java.sql.Date sqldate = new java.sql.Date(bdate.getTime());
            sqlQuery.add(" bdate= '"+ sqldate + "'" );
        }
        String sid =jTextField65.getText();
        if(!"".equals(sid)){
            sqlQuery.add(" sid='" + sid + "'");
        }
        if(jCheckBox1.isSelected()){
            sqlQuery.add(" srapor= '1' ");
            if(jCheckBox2.isSelected()){
                sqlQuery.remove(" srapor= '1' ");
                JOptionPane.showMessageDialog(null,"Only select 1 Check Box!" );
                jCheckBox1.setSelected(false);
                jCheckBox2.setSelected(false);
                
                    throw new ArithmeticException("More then 1 checkbox selected "); 

            }
        }
        if(jCheckBox2.isSelected()){
            sqlQuery.add(" srapor= '0' ");
        }
       
        String Query ="SELECT * FROM uyeler WHERE "+sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s : subsqlQuery){
                Query = Query+ " INTERSECT SELECT * FROM uyeler WHERE "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            
            //java.sql.Date sqldate = new java.sql.Date(utilStartDate.getTime());
            
            //pst=connDbc.prepareStatement(Query);
            
            rst = st.executeQuery(Query);
            
            DefaultTableModel dftable = (DefaultTableModel) ÜyelerTable.getModel();
            dftable.setRowCount(0);
             while(rst.next()){  
                
                String tfname =rst.getString("fname");          
                String tlname =rst.getString("lname");
                String tkayıtid =rst.getString("kayıtid");
                int    tboy =rst.getInt("boy");
                Date tbdate =rst.getDate("bdate");
                String tsid = rst.getString("sid");
                String tsrapor;
                if(rst.getBoolean("srapor")){
                    tsrapor="Uygun";
                }else{
                    tsrapor="Uygun Değil";
                }
                Object[] obj = {tfname ,tlname ,tkayıtid ,tboy ,tbdate ,tsid ,tsrapor};
                dftable.addRow(obj);
            }    
            JOptionPane.showMessageDialog(null,"Values showed in table!" );
            
            

            jTextField61.setText("");
            jTextField63.setText("");
            jTextField85.setText("");
            jTextField65.setText("");
            jTextField61.setText("");
            jDateChooser1.setDateFormatString("");
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }//GEN-LAST:event_ÜyeBulmaActionPerformed

    private void UygunÖğretmenBulmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UygunÖğretmenBulmaActionPerformed
                   /******************** UYGUN ÖĞRETMEN BULMA *****************/
        try {
        
        
        String seviye = (String) jComboBox2.getSelectedItem();
       
        String Query =" SELECT seviye_goster('" + seviye + "')";
        st=connDbc.createStatement();
        System.out.println(Query);
        rst=st.executeQuery(Query);
        rst.next();
        Array a = rst.getArray("seviye_goster");
        String[] ID = (String[])a.getArray();
        String Ogretmenler="";
        for(String s : ID){
                Ogretmenler +="\nUygun Öğretmen  : "+ s;
            }
        JOptionPane.showMessageDialog(null, "!Seviyeye Uygun Ogretmenler Aşağıda Gösterilmiştir!" + Ogretmenler);
        } catch (SQLException ex) {
        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UygunÖğretmenBulmaActionPerformed

    private void UygunDalışYeriBulmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UygunDalışYeriBulmaActionPerformed
                   /******************** UYGUN DALIŞ YERİ BULMA *****************/
        try {
        
        
        String seviye = (String) jComboBox3.getSelectedItem();
        
        String Query =" SELECT seviye_goster1('" + seviye + "')";
        st=connDbc.createStatement();
        System.out.println(Query);
        rst=st.executeQuery(Query);
        rst.next();
        Array a = rst.getArray("seviye_goster1");
        String[] ID = (String[])a.getArray();
        String dalış="";
        for(String s : ID){
                dalış +="\nUygun Dalış Yeri : "+ s;
            }
        JOptionPane.showMessageDialog(null, "!Seviyeye Uygun Dalış Yerleri Aşağıda Gösterilmiştir!" + dalış);
        } catch (SQLException ex) {
        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UygunDalışYeriBulmaActionPerformed

    private void ÖğretmenSilmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ÖğretmenSilmeActionPerformed
            /*********** DELETE OGRETMENLER **************/
        ArrayList<String> sqlQuery = new ArrayList<String>();//DELETE FROM sınıflar WHERE EXISTS (select * from sınıflar where sınıfid='1011' intersect select * from sınıflar where kapasite=7)
        
        String fname =jTextField59.getText();
        if(!"".equals(fname)){
            sqlQuery.add(" fname = '" + fname + "'");
        }
        String lname = jTextField47.getText();
        //int kapasite = Integer.parseInt(jTextField34.getText());
        //if (kapasite != 0){
        //    sqlQuery.add("SELECT * FROM sınıflar WHERE kapasite=" + kapasite);
        //}
        if(!"".equals(lname)){   
            sqlQuery.add(" lname ='" + lname +"'");
        }
        String personalid =jTextField49.getText();
        if(!"".equals(personalid)){
            sqlQuery.add(" personalid ='" + personalid + "'");
        }
        Boolean check=false;
        String koşul = "";
        String ogrsayisi =jTextField50.getText();
        char charogrs[] = ogrsayisi.toCharArray();
        ogrsayisi="";
        for (int i = 0; i < charogrs.length; i++) {
             
            if(!Character.isDigit(charogrs[i])){
                koşul += charogrs[i];
                check=true;
            }
            else{
                ogrsayisi+= charogrs[i];
            }
        }
        if(!"".equals(ogrsayisi)){
            if(check){
            sqlQuery.add(" ogrsayisi " + koşul + " " + parseInt(ogrsayisi));      
            }
            else{
            sqlQuery.add(" ogrsayisi = " + parseInt(ogrsayisi));   
            }
        }
        String oyıldız =jTextField51.getText();
        if(!"".equals(oyıldız)){
            sqlQuery.add(" oyıldız ='" + oyıldız + "'");
        }
        String havuzkey =jTextField48.getText();
        if(!"".equals(havuzkey)){
            sqlQuery.add(" havuzkey='" + havuzkey + "'");
        }
       
        String Query ="DELETE FROM ogretmenler WHERE "+sqlQuery.get(0);
        ArrayList<String> subsqlQuery = new ArrayList<String>(sqlQuery.subList(1, sqlQuery.size()));
            for(String s : subsqlQuery){
                Query = Query+ " AND "+ s;
            }
        System.out.println(Query);
        try {
            st=connDbc.createStatement();
            
            
            
            
            //pst=connDbc.prepareStatement(Query);
            
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null,"Value Deleted!" );
            
            
            DefaultTableModel dftable1 = (DefaultTableModel) ÖğretmenlerTable.getModel();
            dftable1.setRowCount(0);
            ÖğretmenlerVeriAl();
            jTextField59.setText("");
            jTextField47.setText("");
            jTextField49.setText("");
            jTextField50.setText("");
            jTextField51.setText("");
            jTextField48.setText("");
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex );
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
              
    }//GEN-LAST:event_ÖğretmenSilmeActionPerformed

    private void UygunSınıfBulmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UygunSınıfBulmaActionPerformed
                   /************* UYGUN SINIF BULMA ************/
        
        
        try {     
        String seviye = (String) jComboBox4.getSelectedItem();
        String Query =" SELECT sınıfbulmafonk('" + seviye + "')";
        st=connDbc.createStatement();
        System.out.println(Query);
        rst=st.executeQuery(Query);
        rst.next();
        Array a = rst.getArray("sınıfbulmafonk");
        String[] ID = (String[])a.getArray();
        String dalış="";
        for(String s : ID){
                dalış +="\nUygun Sınıf : "+ s;
            }
        JOptionPane.showMessageDialog(null, "!Seviyeye Uygun Sınıflar Aşağıda Gösterilmiştir!" + dalış);
        } catch (SQLException ex) {
        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UygunSınıfBulmaActionPerformed

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
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AnasayfaPanel;
    private javax.swing.JButton DalışYeriBulma;
    private javax.swing.JButton DalışyeriEkleme;
    private javax.swing.JButton DalışyeriGüncelleme;
    private javax.swing.JButton DalışyeriSilme;
    private javax.swing.JTable DalışyeriTable;
    private javax.swing.JPanel DalışyeriİşlemlerPanel;
    private javax.swing.JTabbedPane DalışyerleriTab;
    private javax.swing.JPanel DalşışyeriBilgilerPanel;
    private javax.swing.JPanel SınfılarİşlemlerPanel;
    private javax.swing.JButton SınıfBulma;
    private javax.swing.JButton SınıfEkleme;
    private javax.swing.JButton SınıfGüncelleme;
    private javax.swing.JButton SınıfSilme;
    private javax.swing.JPanel SınıflarBilgilerPanel;
    private javax.swing.JTabbedPane SınıflarTab;
    private javax.swing.JTable SınıflarTable;
    private javax.swing.JButton TümDalısyerleriniGösterme;
    private javax.swing.JButton TümSınıflarıGösterme;
    private javax.swing.JButton TümÖğretmenleriGösterme;
    private javax.swing.JButton TümÜyeleriGösterme;
    private javax.swing.JPanel UyelerBilgilerPanel;
    private javax.swing.JPanel UyelerİşlemlerPanel;
    private javax.swing.JButton UygunDalışYeriBulma;
    private javax.swing.JButton UygunSınıfBulma;
    private javax.swing.JButton UygunÖğretmenBulma;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField99;
    private javax.swing.JButton ÖğretmenBulma;
    private javax.swing.JButton ÖğretmenEkleme;
    private javax.swing.JButton ÖğretmenGüncelleme;
    private javax.swing.JButton ÖğretmenSilme;
    private javax.swing.JPanel ÖğretmenlerBilgilerPanel;
    private javax.swing.JTabbedPane ÖğretmenlerTab;
    private javax.swing.JTable ÖğretmenlerTable;
    private javax.swing.JPanel ÖğretmenlerİşlemlerPanel;
    private javax.swing.JButton ÜyeBulma;
    private javax.swing.JButton ÜyeEkle;
    private javax.swing.JButton ÜyeGüncelleme;
    private javax.swing.JButton ÜyeSilme;
    private javax.swing.JTable ÜyelerTable;
    private javax.swing.JTabbedPane ÜyelerimizTab;
    // End of variables declaration//GEN-END:variables
}
