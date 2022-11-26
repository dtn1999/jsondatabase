package shared;

import com.google.gson.JsonElement;

public class CommandRequest {
    private String type;
    private JsonElement key;
    private JsonElement value;

    public CommandRequest(String type, JsonElement key, JsonElement value){
        this.type = type;
        this.key = key;
        this.value = value;
    }

   public static CommandRequestBuilder builder(){
      return new CommandRequestBuilder();
   }

    public String getType() {
        return type;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(JsonElement key) {
        this.key = key;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public static class CommandRequestBuilder {
        private String type;
        private JsonElement key;
        private JsonElement value;

        public CommandRequestBuilder type(String type){
            this.type = type;
            return this;
        }

        public CommandRequestBuilder key(JsonElement key){
            this.key = key;
            return this;
        }

        public CommandRequestBuilder value(JsonElement value){
            this.value = value;
            return this;
        }

        public CommandRequest build(){
            return new CommandRequest(type, key, value);
        }
    }
}
