#include <iostream>
#include <thread>
#include <vector>

using namespace std;

bool isPrime(int n) {
  if (n <= 1){
    return false;
  }

  for (int i = 2; i < n; i++){
    if (n % i == 0){
      return false;
    }
  }

  return true;
}

void getPrimesInRange(int start, int end, vector<int> *numberVector){

  for (size_t i = start; i < end; i++){
    if (isPrime(i))
    {
      numberVector->__emplace_back(i);
    }
  }
}

int main() {

  int startNumber = 0;
  int endNumber = 500000;
  int amountOfThreads = 6;
  int splitPoint = (endNumber-startNumber)/amountOfThreads;

  vector<int> numberVector[amountOfThreads];
  vector<thread> threadVector;

  for (int i = 0; i < amountOfThreads; i++){
    threadVector.emplace_back(getPrimesInRange, startNumber+(splitPoint*i), splitPoint + (splitPoint*i), &numberVector[i]);
  }

for (auto &thread : threadVector)
    thread.join();

/*
  for(int i=0; i < numberVector[0].size(); i++)
  cout << numberVector[0].at(i) << ' ';

  cout << endl << "" << endl;

  for(int i=0; i < numberVector[1].size(); i++)
  cout << numberVector[1].at(i) << ' ';

  cout << endl << "" << endl;

  for(int i=0; i < numberVector[2].size(); i++)
  cout << numberVector[2].at(i) << ' ';
*/

}
