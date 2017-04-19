#include <iostream>
#include <math.h>
using namespace std;

int NWD(int a, int b)
{
	int pom;

	while (b != 0)
	{
		pom = b;
		b = a%b;
		a = pom;
	}
	return a;
}

int POW(int a, int b, int m){
	return (int)pow(a, b) % m;
}

int AlgPollarda(int N, int n){
	int a = 2, d = 0;

	for (int j = 2; j < n; j++){

		a = POW(a, j, N);
		d = NWD(a - 1, N);
		if (1 < d && d < N)  return d;
		
	}
	return -1;
}
int main(){

	int N, n = 50;
	cin >> N;

	int x = AlgPollarda(N, n);
	if (x > 0) cout << "d: " << x << endl;
	else cout << "nie udalo sie" << endl;

	system("pause");
	return 0;
}