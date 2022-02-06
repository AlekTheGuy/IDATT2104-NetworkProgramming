#include <iostream>
#include <thread>
#include <vector>

using namespace std;

//Checks if a single number is prime.
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

//Uses isPrime() to add all prime numbers between start and end to the vector.
void getPrimesInRange(int start, int end, vector<int> *numberVector){

  for (size_t i = start; i < end; i++){
    if (isPrime(i))
    {
      numberVector->__emplace_back(i);
    }
  }
}

int main() {

  //Changeable variables
  int startNumber = 100;
  int endNumber = 200;
  int amountOfThreads = 3;

  //Numbers per thread
  int splitPoint = (endNumber-startNumber)/amountOfThreads;

  vector<int> numberVector[amountOfThreads];
  vector<thread> threadVector;
  vector<int> primes;

  //create all threads
  for (int i = 0; i < amountOfThreads; i++){
    threadVector.emplace_back(getPrimesInRange, startNumber+(splitPoint*i), startNumber + (splitPoint*(i+1)), &numberVector[i]);
  }

  //Wait for threads to finish
  for (auto &thread : threadVector)
    thread.join();

  //Combine all vectors into one
  for (int i = 0; i < amountOfThreads; i++){
    primes.insert(primes.end(), numberVector[i].begin(), numberVector[i].end());
  }
  
  //All prints are under this comment.
  for(int i=0; i < numberVector[0].size(); i++)
  cout << numberVector[0].at(i) << ' ';

  cout << endl << "" << endl;

  for(int i=0; i < numberVector[1].size(); i++)
  cout << numberVector[1].at(i) << ' ';

  cout << endl << "" << endl;

  for(int i=0; i < numberVector[2].size(); i++)
  cout << numberVector[2].at(i) << ' ';

  cout << endl << "" << endl;
  cout << endl << "combined primes" << endl;

  for(int i=0; i < primes.size(); i++)
  cout << primes.at(i) << ' ';

}
