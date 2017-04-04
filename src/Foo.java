class Foo {
	
	public static int partition(int[]a, int lo, int hi) {
		int i=lo, j = hi + 1;
		while(true) {
			while(a[++i] < a[lo]) {
				if (i==hi) break;
			}
			while(a[lo] < a[--j]) {
				if (j==lo) break;
			}
			if(i>=j) break;
			exchange(a, i, j);
		}
		exchange(a,j, lo);
		return j;
	}
	
	private static void exchange(int[] a, int i, int j) 
	{
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
  
    public static void main(String [] args) {
    	int [] a = new int[2];
    	a[0] = 0;
    	a[1] = 2;
    	//parition [A,C]
    	partition(a, 0, 1);
    }
}
