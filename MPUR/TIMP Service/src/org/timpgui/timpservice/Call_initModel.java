/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.timpservice;

import org.timpgui.timpinterface.*;
import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.FlimPanelModel;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;

/**
 *
 * @author kate
 */
public class Call_initModel {
  
   private static String get_kinpar(Tgm tgm) {
       Dat dat = tgm.getDat();
       KinparPanelModel kinparPanelModel = dat.getKinparPanel();
       
       String kinpar = "kinpar = c("; 
       double k; 
       for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
           if(i>0)
               kinpar = kinpar + ",";
           k = kinparPanelModel.getKinpar().get(i).getStart();
           kinpar = kinpar + Double.toString(k);
       }
       kinpar = kinpar + ")";
       return kinpar;
   }
   private static String get_measured_irf(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "measured_irf=";
      FlimPanelModel ff = dat.getFlimPanel();
      //System.out.println("DDD"+Current.GetcurrMIRF());
      if(ff.isMirf()) { 
          
          m = m; //+ Current.GetcurrMIRF();
      }
      else m = m + "vector()"; 
       
       return m; 
       
   }
   private static String get_convalg(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "convalg= ";
      FlimPanelModel ff = dat.getFlimPanel();
      m = m + Integer.toString(ff.getConvalg());
      if(ff.getConvalg() == 3) 
          m = m + ", reftau = " + Double.toString(ff.getReftau());
      return m; 
       
   }
   
   private static String get_irf(Tgm tgm) {
      Dat dat = tgm.getDat(); 
      String m = "irfpar = ";
      IrfparPanelModel ff = dat.getIrfparPanel();
      int count = 0;
       for (int i = 0; i < ff.getIrf().size(); i++) {
           if(count>0) 
               m = m  + ",";
           else m = m + "c("; 
           m = m + Double.toString(ff.getIrf().get(i));
           count++;
       }
       if(count>0)
           m = m + ")";
       else 
           m = m + "vector()";
       return m;  
   }
   
   private static String get_dispmufun(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "dispmufun = \"poly\"";
      IrfparPanelModel ff = dat.getIrfparPanel();
                
       int count=0;
       if(ff.getDispmufun().equals("poly")) {
            m = "dispmufun = \"poly\"";
       }
       else if (ff.getDispmufun().equals("discrete")) {
            m = "dispmufun = \"discrete\"";  
       }
       return m;  
   }
   private static String get_disptaufun(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "disptaufun = \"poly\"";
      IrfparPanelModel ff = dat.getIrfparPanel();
                
       int count=0;
       if(ff.getDisptaufun().equals("poly")) {
            m = "disptaufun = \"poly\"";
       }
       else if (ff.getDisptaufun().equals("discrete")) {
             m = "disptaufun = \"discrete\"";  
       }
       return m;  
   }
   private static String get_parmu(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "parmu = list(";
      IrfparPanelModel ff = dat.getIrfparPanel();
      
       if(ff.getParmu().trim().length()!=0) {
           m = m + "c(" + ff.getParmu() + ")";  
      }   
       m = m + ")";
       return m;  
   }
    private static String get_partau(Tgm tgm) {
      Dat dat = tgm.getDat();
      String m = "partau=";
      IrfparPanelModel ff = dat.getIrfparPanel();
           
       int count=0;
       
       if(ff.getPartau().trim().length() != 0) {
               if(count>0) 
                    m = m  + ",";
               else m = m + "c(" + ff.getPartau();
               count++;
      }   
      if(count>0) 
         m = m + ")";
       else m = m + "vector()";
       return m;  
   }
   private static String get_fixed(Tgm tgm) {
       Dat dat = tgm.getDat();
       KinparPanelModel kinparPanelModel = dat.getKinparPanel();
       String fixed = "fixed = list(";
       int count=0;
       for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
           if(kinparPanelModel.getKinpar().get(i).isFixed()) {
               if(count>0) 
                    fixed = fixed + ",";
               else fixed = fixed + "kinpar=c(";
               fixed = fixed + Integer.toString(i+1);
               count++;
           }
       }
       if(count>0)
           fixed = fixed + ")";
       fixed = fixed + ")";    
       // need to fill in other parameters here, once we have panels for them 
       return fixed;
   }
   private static String get_constrained(Tgm tgm) {
       Dat dat = tgm.getDat();
       KinparPanelModel kinparPanelModel = dat.getKinparPanel();
       String constrained = "constrained = list("; 
       double cc;
       int count=0;
       for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
           if(kinparPanelModel.getKinpar().get(i).isConstrained()){
               if(kinparPanelModel.getKinpar().get(i).getMin() != null) {
                   if(count > 0)
                       constrained = constrained + ",";
                   cc = kinparPanelModel.getKinpar().get(i).getMin();
                   constrained = constrained + "list(what=\"kinpar\", ind = "+ 
                                  Integer.toString(i+1) +", low=" + cc + ")";
                   count++;
               }
               else if(kinparPanelModel.getKinpar().get(i).getMax() != null) {
                    if(count > 0)
                       constrained = constrained + ",";
                    cc = kinparPanelModel.getKinpar().get(i).getMax();
                    constrained = constrained + "list(what=\"kinpar\", ind = "+ 
                                  Integer.toString(i+1) +", high=" + cc + ")";
                    count++;
               }
           }
       }
       constrained = constrained + ")";
        // need to fill in other parameters here, once we have panels for them 
       return constrained;
   }
   private static String get_seqmod(Tgm tgm) {
       Dat dat = tgm.getDat();
       KinparPanelModel kinparPanelModel = dat.getKinparPanel();  
       String x;
       if(kinparPanelModel.isSeqmod()) x = "TRUE";
       else x = "FALSE";
       return "seqmod ="  + x; 
   } 
   private static String get_positivepar(Tgm tgm) {
       Dat dat = tgm.getDat();
       KinparPanelModel kinparPanelModel = dat.getKinparPanel();
       int count = 0;
       String positivepar = "";
     
       if(kinparPanelModel.isPositivepar()) {
           
           count++;
           
           positivepar = "positivepar=c(\"kinpar\"";
       }
       // need to fill in other parameters here, once we have panels for them 
       //if(count>0) 
       //        positivepar = positivepar + ",";
       if(count<1)
           positivepar="positivepar=vector()";
       else 
           positivepar = positivepar + ")";
       return positivepar;
   }
   private static String get_cohspec(Tgm tgm) {
       Dat dat = tgm.getDat();
       CohspecPanelModel cohspecPanelModel = dat.getCohspecPanel();
       String cc = "cohspec=list(";
     
       if(cohspecPanelModel.getCohspec().isSet()) {
           cc = cc + "type =" + cohspecPanelModel.getCoh();
           if(cohspecPanelModel.getCoh().trim().length() != 0) {
               cc = cc + ", start=" + cohspecPanelModel.getCoh();
           }
       }
       
      cc = cc + ")";
      return cc;
   }
   private static String get_mod_type(Tgm tgm) {
       Dat dat = tgm.getDat();
       String mod_type = "mod_type = \"" + dat.getModType() + "\""; 
       return mod_type;
   }

   public String initModel(Tgm tgm) {
       //TODO: "temp" was  Current.getNameOfCurrentModel()
       String initModel = "temp" + " <- initModel(" +
                          get_kinpar(tgm) + "," + 
                          get_constrained(tgm) + "," + 
                          get_fixed(tgm) + "," +
                          get_seqmod(tgm) + "," + 
                          get_positivepar(tgm) + "," +
                          get_mod_type(tgm) + "," +
                          get_measured_irf(tgm) + "," +
                          get_parmu(tgm) + "," + 
                          get_dispmufun(tgm) + "," +
                          get_partau(tgm) + "," +
                          get_disptaufun(tgm) + "," + 
                          get_convalg(tgm) + "," + 
                          get_irf(tgm) +
                          ")";
       return initModel;
     
       
   }  

}
