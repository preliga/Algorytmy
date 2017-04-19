#include <iostream>

using namespace std;

void AlgRozEuklidesa(int x, int N){
	int u, v, d;

	int A = N, B = x, Ua = 0, Ub = 1;

	int q;

	while (B != 0){
		q = A / B;

		int temp = A;
		A = B;
		B = temp - q * B;

		temp = Ua;
		Ua = Ub;
		Ub = temp - q*Ub;
	}
	d = A;
	u = Ua;
	v = (d - x*u) / N;

	cout << "u = " << u << " v = " << v << " d = " << d << endl;
}
int main(){

	int x, N;

	cin >> x >> N;

	AlgRozEuklidesa(x, N);
	system("pause");
	return 0;
}