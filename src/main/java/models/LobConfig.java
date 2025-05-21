package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LobConfig {
    @JsonProperty("lobName")
    @NotBlank(message = "LOB name cannot be blank")
    private String lobName;

    @JsonProperty("testTypes")
    @NotNull(message = "Test types cannot be null")
    private Map<String, TestTypeConfig> testTypes;

    @JsonProperty("environments")
    private Map<String, String> environments;

    public LobConfig() {
        this.testTypes = new HashMap<>();
        this.environments = new HashMap<>();
    }

    // Getters and setters
    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    public Map<String, TestTypeConfig> getTestTypes() {
        return testTypes;
    }

    public void setTestTypes(Map<String, TestTypeConfig> testTypes) {
        this.testTypes = testTypes;
    }

    public Map<String, String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(Map<String, String> environments) {
        this.environments = environments;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final LobConfig config;

        public Builder() {
            config = new LobConfig();
        }

        public Builder lobName(String lobName) {
            config.setLobName(lobName);
            return this;
        }

        public Builder addTestType(String name, TestTypeConfig typeConfig) {
            config.testTypes.put(name, typeConfig);
            return this;
        }

        public Builder addEnvironment(String name, String url) {
            config.environments.put(name, url);
            return this;
        }

        public LobConfig build() {
            return config;
        }
    }
}
