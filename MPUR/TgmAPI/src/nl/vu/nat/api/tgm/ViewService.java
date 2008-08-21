/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.api.tgm;

import org.openide.filesystems.FileObject;


/**
 *
 * @author joris
 */
public interface ViewService {
    boolean isFitted (FileObject file);
    boolean isUpToDate (FileObject file);
    void view (FileObject file);

}
