package weekofcode23;

public class TimeForOperation {
	public static void main(String[] args) {
		double time1=System.currentTimeMillis();
		int n=10000;
		double max=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				double rand=Math.random();
				int randInt=2;
				if(rand>max){
					max=rand;
				}
			}
		}
		double time2=System.currentTimeMillis();
		
		System.out.println(max);
		System.out.println((time2-time1)/1000);
		
	}
	static private class Point{
		int x;
		int y;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		
	}
}
