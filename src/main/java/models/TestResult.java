package models;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private final String testCaseId;
    private TestStatus status;
    private final List<String> errors;
    private final long startTime;
    private long endTime;

    public TestResult(String testCaseId) {
        this.testCaseId = testCaseId;
        this.status = TestStatus.RUNNING;
        this.errors = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
        if (status != TestStatus.RUNNING) {
            this.endTime = System.currentTimeMillis();
        }
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public TestStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public long getDuration() {
        return endTime - startTime;
    }
} 