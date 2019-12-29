import java.util.ArrayList;
import java.util.List;


public class ListTesting {
	public static void main(String[] args) {
		List<B> b2s, b3s;
		List<B> bs = getOriginalPoints();
		b2s = new ArrayList<>(bs);
		bs.set(2, new B(0, 0));
		
		System.out.println(bs);
		System.out.println(b2s);
	}
	
	public static List<B> getOriginalPoints() {
		float[][] givenPoints = new float[][] {{170.72087405249476f, 11.62830675020814f},
												{342.16378927230835f, 120.86650799028575f},
												{20.68306440487504f, 252.26895846426487f},
												{174.43447695113719f, 307.0640323217958f},
												{352.065247669816f, 164.1842482611537f}};
		List<B> points = new ArrayList<>(givenPoints.length);
		for(int i = 0; i < givenPoints.length; i++) {
			points.add(new B(givenPoints[i][0], givenPoints[i][1]));
		}
		return points;
	}
}
