public class Planet {
	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	private double mass;
	public String img;
	private double yNetForce;
	private double xNetForce;
	private double netForce;
	private double xAccel;
	private double yAccel;
	private Planet[] planets;
	private double radius;

	public Planet(double xPosition, double yPosition, double velocityX, double velocityY, double radius, double m, String image) {
		this.x = xPosition;
		this.y = yPosition;
		this.xVelocity = velocityX;
		this.yVelocity = velocityY;
		this.mass = m;
		this.img = image; 
		this.radius = radius;
	}

	public double radius() {
		return radius;
	}

	public double mass() {
		return mass;
	}

	public double calcDistance(Planet p) {
		double distance = Math.sqrt((p.x-this.x)*(p.x-this.x) + (p.y-this.y)*(p.y-this.y));
		return distance; 
	}

	public double calcPairwiseForce(Planet p) {
		double force = 6.67e-11*p.mass*this.mass/(this.calcDistance(p)*this.calcDistance(p));
		return force;
	}

	public double calcPairwiseForceX(Planet p) {
		double forceX = this.calcPairwiseForce(p)*(p.x-this.x)/this.calcDistance(p);
		return forceX;
	}

	public double calcPairwiseForceY(Planet p) {
		double forceY = this.calcPairwiseForce(p)*(p.y-this.y)/this.calcDistance(p);
		return forceY;
	}

	public double calcXNetForce(Planet[] planets) {
		int numberOfPlanets = planets.length;
		int i = 0;
		double netForceX = 0;
		while (i < numberOfPlanets-1) {
			if (this.equals(planets[i])) {
				i = i + 1;
			}
			else {
				netForceX = netForceX + this.calcPairwiseForceX(planets[i]);
				i = i + 1;
			}
		}
		this.xNetForce = netForceX;
		return netForceX;
	}

	public double calcYNetForce(Planet[] planets) {
		int numberOfPlanets = planets.length;
		int i = 0;
		double netForceY = 0;
		while (i < numberOfPlanets-1) {
			if (this.equals(planets[i])) {
				i = i + 1;
			}
			else {
				netForceY = netForceY + this.calcPairwiseForceY(planets[i]);
				i = i + 1;
			}
		}
		this.yNetForce = netForceY;
		return netForceY;
	}

	public void setNetForce(Planet[] planets) {
		double netForce = Math.sqrt(this.calcYNetForce(planets)*this.calcYNetForce(planets) + this.calcXNetForce(planets)*this.calcXNetForce(planets));
	}

	public void draw() {
		StdDraw.picture(x,y,"images/" + this.img);
	}

	public void update(double dt) {
		this.xAccel = this.xNetForce/this.mass;
		this.yAccel = this.yNetForce/this.mass;
		this.xVelocity = xVelocity + (dt * xAccel);
		this.yVelocity = yVelocity + (dt * yAccel);
		this.x = this.x + (dt * this.xVelocity);
		this.y = this.y + (dt * this.yVelocity);
	}
}






