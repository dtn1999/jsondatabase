package shared;

import com.google.gson.JsonElement;

public class CommandResponse {
    private String response;
    private String reason;
    private JsonElement value;

    public CommandResponse(String response, String reason, JsonElement value){
        this.response = response;
        this.reason = reason;
        this.value = value;
    }

    public String getResponse(){
        return response;
    }

    public String getReason() {
        return reason;
    }

    public JsonElement getValue() {
        return value;
    }

    public static CommandResponseBuilder builder(){
        return new CommandResponseBuilder();
    }

    public static class CommandResponseBuilder {
        private String response;
        private String reason;
        private JsonElement value;

        public CommandResponseBuilder response(String response){
            this.response = response;
            return this;
        }
        public CommandResponseBuilder reason(String reason){
            this.reason = reason;
            return this;
        }
        public CommandResponseBuilder value(JsonElement value){
            this.value = value;
            return this;
        }
        public CommandResponse build(){
            return new CommandResponse(response, reason, value);
        }
    }
}
