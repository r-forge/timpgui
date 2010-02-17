/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.timpcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    private String[] theCommandLineArguments = new String[]{"/usr/lib64/R/bin/Rserve","--no-save"};

    @Override
    public void restored() {            
//            // Create a Callable returning the actual Process.
//            Callable<Process> processCallable = new ProcessLaunch(theCommandLineArguments);
//            // Create a descriptor for the UI representation of the process.
//            ExecutionDescriptor descriptor = new ExecutionDescriptor().controllable(true).frontWindow(true);
//            descriptor.showProgress(true);
//            descriptor.inputVisible(true);
//            // Create an instance of ExecutionService and run the Callable<Process>
//            ExecutionService exeService = ExecutionService.newService(processCallable,descriptor, "My Process");
//            // Run the process and return the exitcode.
//            Future<Integer> exitCode = exeService.run();
    }

    private class ProcessLaunch implements Callable<Process> {

        private final String[] commandLine;

        public ProcessLaunch(String... commandLine) {
            this.commandLine = commandLine;
        }

        public Process call() throws Exception {
            ProcessBuilder pb = new ProcessBuilder(commandLine);
            pb.directory(new File(System.getProperty("user.home"))); //NOI18N
            pb.redirectErrorStream(true);
            return pb.start();
        }
    }
}
