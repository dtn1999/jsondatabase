package shared;

import com.beust.jcommander.Parameter;

public class InputArgs {
        @Parameter(names = "-t", description = "type of the command")
        private String type;
        @Parameter(names = "-k", description = "key where the command will produce a change")
        private String key;
        @Parameter(names = "-v", description = "Value to persist by the command")
        private String value;
        @Parameter(names = "-in", description = "file where to read command from")
        private String file;


        public String getType() {
            return type;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getFile() {
            return file;
        }
    }