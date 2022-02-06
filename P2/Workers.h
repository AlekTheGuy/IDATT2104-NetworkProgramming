#ifndef WORKERS_H
#define WORKERS_H
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

#endif