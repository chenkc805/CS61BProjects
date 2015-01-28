public class NBody {

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		In in = new In(filename);
		int numberOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();

		Planet[] universe = new Planet[numberOfPlanets];

		for (int i = 0; i < numberOfPlanets; i++) {
			universe[i] = NBody.getPlanet(in);
		} 
		for (double time = 0; time < T; time+=dt) {
			StdDraw.setScale(-radiusOfUniverse, radiusOfUniverse);
			StdDraw.picture(0,0,"images/starfield.jpg");
			for (int i = 0; i < numberOfPlanets; i++) {
				universe[i].setNetForce(universe);
				universe[i].update(dt);
				universe[i].draw();
			}
			StdDraw.show(10);
		}
		StdOut.printf("%d\n", numberOfPlanets);
		StdOut.printf("%.2e\n", radiusOfUniverse);
		for (int i = 0; i < numberOfPlanets; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            	universe[i].x, universe[i].y, universe[i].xVelocity, universe[i].yVelocity, universe[i].mass, universe[i].img);
		}
	}

	public static Planet getPlanet(In in) {
		double x = in.readDouble();
		double y = in.readDouble();
		double xVelocity = in.readDouble();
		double yVelocity = in.readDouble();
		double mass = in.readDouble();
		String img = in.readString();
		Planet aPlanet = new Planet(x, y, xVelocity, yVelocity, mass, img);
		return aPlanet;
	}
		
}