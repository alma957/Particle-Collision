package simulation;

public class ParticleWallCollision extends Collision {
    private Wall wall; 
    
    /**
     * Constructor for ParticleWallCollision.
     */
    public ParticleWallCollision(final Particle p, final Wall w, final double t) {
        super(t, new Particle[] { p });
        this.wall = w;
    }

    /**
     * Updates the state of the particle involved in the collision and 
     * lets the event handler kwnow that a ParticleWallCollision happened.
     */
    @Override
    public void happen(final ParticleEventHandler h) {
        final Particle particles[] = getParticles();
        
        // Updates the state of the particle:
        Particle.collide(particles[0], this.wall);

        h.reactTo(this);
    }
}
