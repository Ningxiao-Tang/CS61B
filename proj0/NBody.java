public class NBody{
	public static double readRadius(String s){
		In in = new In(s);
		int numofPlanet = in.readInt();
		double r = in.readDouble();
		return r;
	}
	public static Planet[] readPlanets(String s) {
		In in = new In(s);
		int num = in.readInt();
		//System.out.println("number of planet" + num);
		double rad = in.readDouble();
		Planet[] Planets = new Planet[num];
		for (int i = 0; i < num; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planets[i] = new Planet(xP,yP, xV, yV, m, img);
		}
		return Planets;
	}

	public static void main(String[] args) {
		double T, dt;
		String filename;
		//In in = new In(args[1]);
		T = Double.parseDouble(args[0]);
		dt = Double.parseDouble(args[1]);
		filename = args[2];
		double r = readRadius(filename);
		Planet[] universe = readPlanets(filename);

		StdDraw.enableDoubleBuffering();
		StdAudio.play("audio/2001.mid");
		for (double time = 0; time < T; time+=dt) {
			double[] xForces = new double[universe.length];
			double[] yForces = new double[universe.length];
			for (int i = 0; i < universe.length; i++) {
				xForces[i] = universe[i].calcNetForceExertedByX(universe);
				yForces[i] = universe[i].calcNetForceExertedByY(universe);
			}
			for (int i = 0; i < universe.length; i++) {
				universe[i].update(dt,xForces[i],yForces[i]);
			}
			String backgroundImg = "images/starfield.jpg";
			StdDraw.setScale(-r, r);
			StdDraw.clear();
			StdDraw.picture(0, 0, backgroundImg);
			for (int i = 0; i < universe.length; i++) {
				universe[i].draw();
			}
			StdDraw.show();

			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", universe.length);
		StdOut.printf("%.2e\n", r);
		for (int i = 0; i < universe.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					universe[i].xxPos, universe[i].yyPos, universe[i].xxVel,
					universe[i].yyVel, universe[i].mass, universe[i].imgFileName);
		}
	}
}