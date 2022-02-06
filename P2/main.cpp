#include <vector>
#include <thread>
#include <functional>
#include <list>
#include <iostream>
#include <chrono>

class Workers {
private:
    std::list<std::function<void()> > taskList;
    std::vector<std::thread> threadVector;
    bool runWorkers = true;
    std::mutex tasksMutex;
    std::condition_variable cv;
    int amountOfThreads;
    bool isWaiting;
public:
    explicit Workers(int);

    void post(std::function<void()>);

    void stopWorkers();

    void startWorkers();
};

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

int main() {

    using namespace std::this_thread; // sleep_for, sleep_until
    using namespace std::chrono; // nanoseconds, system_clock, seconds

    Workers workerThreads(2);
    Workers eventLoop(1);

    workerThreads.startWorkers();
    eventLoop.startWorkers();

    workerThreads.post([] {
        std::cout << "task " << 1
                  << " runs in thread "
                  << std::this_thread::get_id()
                  << std::endl;
    });

    workerThreads.post([] {
        std::cout << "task " << 2
                  << " runs in thread "
                  << std::this_thread::get_id()
                  << std::endl;
    });

    eventLoop.post([] {
        std::cout << "task " << 3
                  << " runs in thread "
                  << std::this_thread::get_id()
                  << std::endl;
    });

    eventLoop.post([] {
        std::cout << "task " << 4
                  << " runs in thread "
                  << std::this_thread::get_id()
                  << std::endl;
    });


/*
    for (int i = 0; i < 10; ++i) {
        workerThreads.post([i] {
            sleep_for(seconds(1));
            std::cout << "task " << i
                      << " runs in thread "
                      << std::this_thread::get_id()
                      << std::endl;
        });
    }
*/

    sleep_for(seconds(10));
    workerThreads.stopWorkers();

    return 0;
}
