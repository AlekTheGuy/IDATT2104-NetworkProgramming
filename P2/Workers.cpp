#include "Workers.h"

void Workers::post(std::function<void()> function) {
    {
        std::unique_lock<std::mutex> locker(tasksMutex);
        taskList.emplace_back(function);
        locker.unlock();
        isWaiting = false;
        cv.notify_one();
    }
}

Workers::Workers(int amountOfThreads) {
    this->amountOfThreads = amountOfThreads;
}

void Workers::startWorkers() {
    for (int i = 0; i < amountOfThreads; i++) {
        threadVector.emplace_back([this] {

            //Code inside thread
            while (runWorkers) {
                std::function<void()> task;
                {
                    std::unique_lock<std::mutex> locker(tasksMutex);
                    while (isWaiting){
                        cv.wait(locker);
                    }
                    isWaiting = true;
                    if (!taskList.empty()) {
                        task = *taskList.begin();
                        taskList.pop_front();
                    }
                    locker.unlock();
                    isWaiting = false;
                    cv.notify_one();
                }
                if (task) {
                    task();
                }
            }

        });
    }
    cv.notify_one();
}

void Workers::stopWorkers() {
    runWorkers = false;
    for (auto &thread: threadVector)
        thread.join();
}