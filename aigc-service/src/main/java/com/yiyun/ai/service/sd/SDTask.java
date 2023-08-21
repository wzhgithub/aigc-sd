package com.yiyun.ai.service.sd;

public interface SDTask extends Runnable {
    void updateStatus(TaskStatus status);

    enum TaskStatus {
        INITIAL, SUCCESS, FAILED, RUNNING;
    }
}
