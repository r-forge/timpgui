/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.timpcontroller;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

//    private String[] theCommandLineArguments = new String[]{"R","--no-save"};

    @Override
    public void restored() {
        // By default, do nothing.
        // Put your startup code here.
//        //ExecutionDescriptor descriptor = new ExecutionDescriptor().controllable(true).frontWindow(true).preExecution(new SomeRunnableToCallBeforeStart()).postExecution(new SomeRunnableToCallAfterExit());
//        ExecutionDescriptor descriptor = new ExecutionDescriptor().controllable(true).frontWindow(true);
//        ExecutionService exeService = ExecutionService.newService(
//        new ProcessLaunch(theCommandLineArguments),
//        descriptor, "My Process");
//        Future<Integer> exitCode = exeService.run();

    }

//    private class ProcessLaunch implements Callable<Process> {
//
//        private final String[] commandLine;
//
//        public ProcessLaunch(String... commandLine) {
//            this.commandLine = commandLine;
//        }
//
//        public Process call() throws Exception {
//            ProcessBuilder pb = new ProcessBuilder(commandLine);
//            pb.directory(new File(System.getProperty("user.home"))); //NOI18N
//            pb.redirectErrorStream(true);
//            return pb.start();
//        }
//    }
}
