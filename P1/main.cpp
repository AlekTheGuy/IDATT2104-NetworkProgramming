#include <iostream>
#include <thread>
#include <vector>

using namespace std;

bool isPrime(int n) {
  if (n <= 1){
    return false;
  }

  for (int i = 2; i < n/2; i++){
    if (n % i == 0){
      return false;
    }
  }

  return true;
}

int main() {
  vector<thread> threads;

  int startNumber = 0;
  int endNumber = 100000;
  int amountOfThreads = 2;


  for (int i = 0; i < 10; i++) {
    threads.emplace_back([i] {                    // i is copied to the thread, do not capture i as reference (&i) as it might be freed before all the threads finishes.
      cout << "Output from thread " << i << endl; // The output might be interleaved since the string, integer and newline are output in succession.
    });
  }

  for (auto &thread : threads)
    thread.join();
}
