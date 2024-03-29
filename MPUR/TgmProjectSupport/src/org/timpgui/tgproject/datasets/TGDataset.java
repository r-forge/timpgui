/*
 *  Puzzle-GIS - OpenSource mapping program
 *  http://docs.codehaus.org/display/PUZZLEGIS
 *  Copyright (C) 2007 Puzzle-GIS
 *  
 *  GPLv3 + Classpath exception
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.timpgui.tgproject.datasets;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;


/**
 * This interface allows to create some GIS "sources".<br>
 * A {@code GISSource} represents a GIS data in the project.
 * A {@code GISSource} could represent :
 * <ul>
 *  <li>A Shapefile,</li>
 *  <li>a Geotiff,</li>
 *  <li>a geodatabase table,</li>
 *  <li>...</li>
 * </ul>
 *  
 * @author  Johann Sorel (Puzzle-GIS)
 * @author  Thomas Bonavia (comments)
 */
public abstract class TGDataset {

    public static final String STATE_PROPERTY = "state";

    protected final PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
    protected final InstanceContent content = new InstanceContent();
    protected final Lookup lookup;

//    private final GISSourceInfo info;
    private TGDatasetState state = TGDatasetState.UNLOADED;
    
//    protected TGDataset(final GISSourceInfo info){
//        if(info == null || info.getID() < 0){
//            throw new IllegalArgumentException("SourceInfo can not be null or have an invalid ID number");
//        }
//        this.info = info;
//
//        lookup = new AbstractLookup(content);
//    }

    protected TGDataset(){
        lookup = new AbstractLookup(content);
    }
    
//    public final GISSourceInfo getInfo(){
//        return info;
//    }

    public TGDatasetState getState() {
        return state;
    }

    protected void setState(final TGDatasetState state){
        if(state == null) throw new NullPointerException("state can not be null");

        final TGDatasetState oldState = this.state;
        this.state = state;
        propertySupport.firePropertyChange(STATE_PROPERTY, oldState, this.state);
    }
    
    public abstract void unload();

    public abstract void load();

    public Lookup getLookup(){
        return lookup;
    }

    /**
     * Get the icon to use for each kind of {@code GISSource}.
     * @param   type The type of icon to use (allows to define multiple icons).
     * @return  A new {@code Image} representing the icon.
     */
    public abstract Image getIcon(int type);

    public void addPropertyChangeListener(PropertyChangeListener listener){
         propertySupport.addPropertyChangeListener(listener);
     }

    public void addPropertyChangeListener(String propName, PropertyChangeListener listener){
         propertySupport.addPropertyChangeListener(propName,listener);
     }

    public void removePropertyChangeListener(PropertyChangeListener listener){
         propertySupport.removePropertyChangeListener(listener);
     }

    public void removePropertyChangeListener(String propName, PropertyChangeListener listener){
         propertySupport.removePropertyChangeListener(propName,listener);
     }

 
}
