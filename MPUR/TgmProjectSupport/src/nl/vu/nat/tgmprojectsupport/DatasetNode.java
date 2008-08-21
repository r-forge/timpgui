/*
 * DatasetNode.java
 *
 * Created on December 10, 2006, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.vu.nat.tgmprojectsupport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Tim Boudreau
 */
public class DatasetNode extends AbstractNode { //BeanNode {
    //We want no icon at all, so just use a transparent image
    static final BufferedImage ICON = new BufferedImage (1, 1,
            BufferedImage.TYPE_INT_ARGB);
    static {
        Color c = new Color (255, 255, 255, 255);
        Graphics2D g = ICON.createGraphics();
        g.fillRect (0, 0, 1, 1);
        g.dispose();
    }
    
    public DatasetNode(Dataset dataset) { //throws Exception {
        super (Children.LEAF, Lookups.singleton(dataset));
        
        //If using BeanNode as superclass
        //super (card);
    }
    
    private Dataset getDataset() {
        return (Dataset) getLookup().lookup (Dataset.class);        
        //if using BeanNode as superclass
//        try {
//            InstanceCookie ck = (InstanceCookie) getCookie (
//                      InstanceCookie.class);
//            return (Card) ck.instanceCreate();
//        } catch (Exception e) {
//            throw new IllegalStateException (e);
//        }
    }
    
    @Override
    public Image getIcon (int type) {
        return ICON;
    }
    
    public String getDisplayName() {
        Dataset d = getDataset();
        return d.getName();
    }
    
    public String getHtmlDisplayName() {
        Dataset d = getDataset();
        String result = null;
        result = "<font color=#FF0000>" +
                            getDisplayName();
        return result;
    }
    
}
