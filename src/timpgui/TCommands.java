/*
 * TCommands.java
 *
 * Created on May 30, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package timpgui;

/**
 *
 * @author Joris
 */
public class TCommands {

    /**
     * Creates a new instance of TCommands
     */
    public TCommands() {
    }

    public static class readData {

        String dat, filenm, sep;

        public void setArg(String arg, String value) {
            boolean flag = true;
            if (flag) {
                if (arg == "dat") {
                    dat = value.toString();
                } else if (arg == "filenm") {
                    filenm = value.toString();
                } else if (arg == "sep") {
                    sep = value.toString();
                }
            } else {
            // flag not true
            }
        }

        public String getArg(String arg) {
            String value = null;
            boolean flag = true;
            if (flag) {
                if (arg == "dat") {
                    value = dat.toString();
                } else if (arg == "filenm") {
                    value = filenm.toString();
                } else if (arg == "sep") {
                    value = sep.toString();
                }
            } else {
            // flag not true
            }
            return value;
        }

        public String generateCode() {
            String code = "";
            if (getArg("dat").trim().length() != 0 && getArg("filenm").trim().length() != 0) {
                // check if path is valid?
                code = getArg("dat").concat(" <- readData(\""); //
                code = code + getArg("filenm").concat("\"");
                if (getArg("sep").trim().length() != 0) {
                    code = code.concat(", sep = ") + getArg("sep");
                }
                code = code.concat(")\n");
            } else {
                code = null;
                System.err.println("Data file is invalid.");
            }
            return code;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="preProcess">
    public static class preProcess {

        String data, sample, sample_time, sample_lambda, sel_time, sel_lambda,
                baselinetime, baselinelambda, scalx, scalx2, sel_lambda_ab,
                sel_time_ab, rm_x2, rm_x, svdResid, numV;

        private void setArg(String arg, String value) {
            boolean flag = true;
            if (flag) {
                if (arg == "data") {
                    data = value.toString();

                } else if (arg == "sample") {
                    sample = value.toString();
                }
            } else if (arg == "sample_time") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else if (arg == "etc") {
                data = value.toString();
            } else { // flag is off

            }
        }

        private void getArg() {
        }

        private void generateCode() {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="initModel">
    public static class initModel {

        String dat, mod_type;

        public void setArg(String arg, String value) {
            boolean flag = true;
            if (flag) {
                if (arg == "dat") {
                    dat = value.toString();
                } else if (arg == "mod_type") {
                    mod_type = value.toString();
                }
            } else {
            // flag not true
            }
        }

        public String getArg(String arg) {
            String value = null;
            boolean flag = true;
            if (flag) {
                if (arg == "dat") {
                    value = dat.toString();
                } else if (arg == "mod_type") {
                    value = mod_type.toString();
                }
            } else {
            // flag not true
            }
            return value;
        }

        public String generateCode() {
            String code = "";
            return code;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="fitModel">
    public static class fitModel {

        String data, modspec;

        public void setArg(String arg, String value) {
            boolean flag = true;
            if (flag) {
                if (arg == "data") {
                    data = value.toString();
                } else if (arg == "modspec") {
                    modspec = value.toString();
                }
            } else {
            // flag not true
            }
        }

        public String getArg(String arg) {
            String value = null;
            boolean flag = true;
            if (flag) {
                if (arg == "data") {
                    value = data.toString();
                } else if (arg == "modspec") {
                    value = modspec.toString();
                }
            } else {
            // flag not true
            }
            return value;
        }

        public String generateCode() {
            String code = "";
            return code;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="examineFit">
    public static class examineFit {

        String resultfitModel, opt;

        public void setArg(String arg, String value) {
            boolean flag = true;
            if (flag) {
                if (arg == "resultfitModel") {
                    resultfitModel = value.toString();
                } else if (arg == "opt") {
                    opt = value.toString();
                }
            } else {
            // flag not true
            }
        }

        public String getArg(String arg) {
            String value = null;
            boolean flag = true;
            if (flag) {
                if (arg == "resultfitModel") {
                    value = resultfitModel.toString();
                } else if (arg == "opt") {
                    value = opt.toString();
                }
            } else {
            // flag not true
            }
            return value;
        }

        public String generateCode() {
            String code = "";
            // Add code generation here
            return code;
        }
    }
}
