/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.flimdataloader;

import junit.framework.TestCase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataObject;

public class SdtDataObjectTest extends TestCase {

    public SdtDataObjectTest(String testName) {
        super(testName);
    }

    public void testDataObject() throws Exception {
        FileObject root = Repository.getDefault().getDefaultFileSystem().getRoot();
        FileObject template = root.getFileObject("Templates/Other/SdtTemplate.sdt");
        assertNotNull("Template file shall be found", template);

        DataObject obj = DataObject.find(template);
        assertEquals("It is our data object", SdtDataObject.class, obj.getClass());
    }
}
