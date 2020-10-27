package simulation;

public class TwoParticleCollision extends Collision {
    /**
     * Constructor for TwoParticleCollision.
     */
    public TwoParticleCollision(final Particle p1, final Particle p2, final double t) {
        super(t, new Particle[] { p1, p2 });
    }

    /**
     * Updates the state of the two particles involved in the collision and
     * lets the event handler kwnow that a TwoParticleCollision happened.
     */
    @Override
    public void happen(ParticleEventHandler h) {
        final Particle particles[] = getParticles();

        // Updates the state of the two particles:
        Particle.collide(particles[0], particles[1]);

        h.reactTo(this);

    }
    
}