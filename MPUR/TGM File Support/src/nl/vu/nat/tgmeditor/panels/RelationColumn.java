/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.tgmeditor.panels;

import javax.swing.table.TableColumn;

/**
 *
 * @author slapten
 */

public class RelationColumn extends TableColumn{
        RelationColumn(){
            super();
            super.width = 30;
            super.setPreferredWidth(30);
            super.setCellEditor(new JVectorCellEditor());
            super.setCellRenderer(new RelationCellRenderer());
        }

        RelationColumn(int modelIndex){
            super(modelIndex, 30);
            headerValue = String.valueOf(modelIndex+1);
            super.setCellEditor(new JVectorCellEditor());
            super.setCellRenderer(new RelationCellRenderer());

        }

        RelationColumn(int modelIndex, int width){
            super(modelIndex, width);
            headerValue = String.valueOf(modelIndex+1);
            super.setCellEditor(new JVectorCellEditor());
            super.setCellRenderer(new RelationCellRenderer());
        }

        
}
