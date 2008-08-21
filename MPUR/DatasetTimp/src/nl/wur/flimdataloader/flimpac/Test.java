package nl.wur.flimdataloader.flimpac;


/**
 *
 * @author Sergey
 */
public class Test {
    
    public static void main(String[] args){
        try{
            FlimImage f = new FlimImage();//("D:\\flimdata\\JW\\Data 2007\\Richard070605\\c-002.sdt");
            f.saveDataToASCIIFile("D:/tmp");

/*            
            byte[] temp = new byte[9];
            f.readFully(temp, 0, 9);
            System.out.println(temp);
            temp = new byte[11];
            f.readFully(temp, 0, 11);
            System.out.println(temp);
            temp = new byte[16];
            f.readFully(temp, 0, 16);
            System.out.println(temp);
            System.out.println(f.getStreamPosition());
            System.out.println(f.readShort());
  */
   /*           
            System.out.println(f.getStreamPosition());
            measinf.fread(f);
            System.out.println(f.getStreamPosition()); 
            
        
            Field[] fields =  MeasureInfo.class.getDeclaredFields();
         
            for (int i=0; i<fields.length; ++i){
                System.out.println(fields[i].getName()+" has type "+ fields[i].getType());                             
            }
            
               
            System.out.println(measinf.mod_ser_no); 
            System.out.println(measinf.date);
            System.out.println(measinf.time);
            System.out.println(measinf.meas_mode);
            System.out.println(measinf.cfd_ll);
            System.out.println(measinf.cfd_hf);
            System.out.println(measinf.cfd_lh);
            System.out.println((int)measinf.StopInfo.flags);
            System.out.println(measinf.StopInfo.min_sync_rate); 
            System.out.println(measinf.StopInfo.status);
             
            System.out.println(header.revision);
            System.out.println(header.info_offs);
            System.out.println(header.info_length);
            System.out.println(header.setup_offs);
            System.out.println(header.setup_length);
            System.out.println(header.data_block_offs);
            System.out.println(header.no_of_data_blocks);
            System.out.println(header.data_block_length);
            System.out.println(header.meas_desc_block_offs);
            System.out.println(header.no_of_meas_desc_blocks);
            System.out.println(header.meas_desc_block_length);
            System.out.println(header.header_valid);
            System.out.println(header.reserved1);
            System.out.println(header.reserved2);
*/
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        
        
        
    }

}
