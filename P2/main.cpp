#include "Workers.h"

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
