public class Planet {
	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	public double mass;
	public String img;
	public double yNetForce;
	public double xNetForce;
	public double netForce;
	public double xAccel;
	public double yAccel;

	public Planet(double xPosition, double yPosition, double velocityX, double velocityY, double m, String image) {
		this.x = xPosition;
		this.y = yPosition;
		this.xVelocity = velocityX;
		this.yVelocity = velocityY;
		this.mass = m;
		this.img = image; 
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

	public void setNetForce(Planet[] planets) {
		int numberOfPlanets = planets.length;
		int i = 0;
		this.xNetForce = 0;
		this.yNetForce = 0;
		while (i < numberOfPlanets) {
			if (this.equals(planets[i])) {
				i = i + 1;
			}
			else {
				this.yNetForce = this.yNetForce + this.calcPairwiseForceY(planets[i]);
				this.xNetForce = this.xNetForce + this.calcPairwiseForceX(planets[i]);
				i = i + 1;
			}
		}
		this.netForce = Math.sqrt(this.yNetForce*this.yNetForce + this.xNetForce*this.xNetForce);
		System.out.println(this.netForce);
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






