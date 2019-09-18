import java.lang.Math;

public class Planet 
{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private static final double g = 6.67e-11;

	/* 2 constructor */
	public Planet (double xP, double yP, double xV, double yV, double m, String img) 
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet (Planet p)
	{
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p)
	{
		double distance;
		distance = (this.xxPos - p.xxPos)*(this.xxPos - p.xxPos) + (this.yyPos - p.yyPos)*(this.yyPos - p.yyPos);
		return Math.sqrt(distance);
	}

	public double calcForceExertedBy(Planet p) 
	{
		//returns a double describing the force exerted on this planet by given planet
		
		double r = this.calcDistance(p) ;
		double f = (g * this.mass * p.mass)/(r*r) ;
		return f;
	}

	public double calcForceExertedByX(Planet p)
	{
		double fX = calcForceExertedBy(p) * (p.xxPos - this.xxPos)/ calcDistance(p);
		return fX;
	}
	
	public double calcForceExertedByY(Planet p)
	{
		double fY = calcForceExertedBy(p) * (p.yyPos - this.yyPos)/ calcDistance(p);
		return fY;
	}

	/* example:
	Planet[] allPlanets = {samh, rocinante, aegir};
	samh.calcNetForceExertedByX(allPlanets);
	samh.calcNetForceExertedByY(allPlanets);
	*/
	public double calcNetForceExertedByX(Planet[ ] allPlanets){
		int l = allPlanets.length;
		int i;
		double netFX = 0;
		for (i = 0; i < l; i++){
			if(this.equals(allPlanets[i])) continue;
			netFX += calcForceExertedByX(allPlanets[i]);
		}
		return netFX;
	
	}

	public double calcNetForceExertedByY(Planet[ ] allPlanets){
		int l = allPlanets.length;
		int i;
		double netFY = 0;;
		for (i = 0; i < l; i++){
			if(this.equals(allPlanets[i])) continue;
			netFY += calcForceExertedByY(allPlanets[i]);
		}
		return netFY;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX/this.mass;
		double aY = fY/this.mass;
		this.xxVel += dt * aX;
		this.yyVel += dt * aY;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}
	public void draw(){
		String s = "images/" + imgFileName;
		StdDraw.picture(xxPos, yyPos, s);
	}
	
}