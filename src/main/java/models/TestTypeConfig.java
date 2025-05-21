package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestTypeConfig {
    @JsonProperty("type")
    @NotNull(message = "Test type cannot be null")
    private String type; // e.g., "api", "ui", "integration"

    @JsonProperty("priority")
    @Min(value = 1, message = "Priority must be at least 1")
    private int priority;

    @JsonProperty("parallel")
    private boolean parallel;

    @JsonProperty("retries")
    @Min(value = 0, message = "Retries cannot be negative")
    private int retries;

    @JsonProperty("timeoutSeconds")
    @Min(value = 1, message = "Timeout must be at least 1 second")
    private int timeoutSeconds;

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final TestTypeConfig config;

        public Builder() {
            config = new TestTypeConfig();
        }

        public Builder type(String type) {
            config.setType(type);
            return this;
        }

        public Builder priority(int priority) {
            config.setPriority(priority);
            return this;
        }

        public Builder parallel(boolean parallel) {
            config.setParallel(parallel);
            return this;
        }

        public Builder retries(int retries) {
            config.setRetries(retries);
            return this;
        }

        public Builder timeoutSeconds(int timeoutSeconds) {
            config.setTimeoutSeconds(timeoutSeconds);
            return this;
        }

        public TestTypeConfig build() {
            return config;
        }
    }
}
